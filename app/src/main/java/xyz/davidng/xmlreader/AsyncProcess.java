package xyz.davidng.xmlreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AsyncProcess extends AsyncTask<String, Integer, String> {
    Context context;
    ListView lv;
    ArrayList<VNExpress> arrayList;
    CustomLayout adapter;
    int nodeListCounter = 0;
    ProgressDialog progressDialog;

    public AsyncProcess(Context context, ListView lv) {
        this.context = context;
        this.lv = lv;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(nodeListCounter);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        arrayList = new ArrayList<VNExpress>();
        adapter = new CustomLayout(context, R.layout.custom_layout, arrayList);
    }
    @Override
    protected String doInBackground(String... params) {
        if (params[0] != null){
            String xml =  readFromURL(params[0]);
            XMLParser parser = new XMLParser();
            Document doc = parser.getDocument(xml);
            NodeList nodeList = doc.getElementsByTagName("item");
            NodeList nodeListDescription = doc.getElementsByTagName("description");

            String title;
            String description="";
            nodeListCounter = nodeList.getLength();

            for(int i=0; i<nodeListCounter; i++) {
                Element e = (Element) nodeList.item(i);
                title = parser.getValue(e, "title");

                description = nodeListDescription.item(i + 1).getTextContent() + "<br/>";
                VNExpress vne = new VNExpress(title, getImageLinkFromCDATA(description));

                arrayList.add(vne);
                publishProgress((i*100) / nodeListCounter);
                SystemClock.sleep(100);
            }
            return null;
        }
        else{
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String xml) {
        progressDialog.dismiss();
        lv.setAdapter(adapter);
    }

    private String getImageLinkFromCDATA(String link){
        int begin = link.indexOf("http://img");
        int end = link.indexOf(".jpg");
        return link.substring(begin, end+4);
    }

    private static String readFromURL(String theUrl){
        StringBuilder content = new StringBuilder();
        try{
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream())
            );

            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return content.toString();
    }

}
