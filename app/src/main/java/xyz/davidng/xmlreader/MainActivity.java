package xyz.davidng.xmlreader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) this.findViewById(R.id.lv);
        new AsyncProcess(MainActivity.this, lv)
                .execute("http://vnexpress.net/rss/the-thao.rss");
    }
}
