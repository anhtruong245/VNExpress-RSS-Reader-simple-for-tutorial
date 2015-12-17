package xyz.davidng.xmlreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 12/16/2015.
 */
public class CustomLayout extends ArrayAdapter<VNExpress> {

    public CustomLayout(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomLayout(Context context, int resource, List<VNExpress> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_layout, null);
        }

        VNExpress p = getItem(position);

        if (p != null) {
            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            Picasso.with(getContext()).load(p.getImageLink()).into(img);

            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(p.getTitle());
        }
        return v;
    }
}
