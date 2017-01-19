package com.parveen.nitmate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomListy extends ArrayAdapter<String> {
    private String[] headings;
    private Bitmap[] bitmaps;
    private Activity context;

    public CustomListy(Activity context, String[] headings, Bitmap[] bitmaps) {
        super(context, R.layout.image_list_viewy, headings);
        this.context = context;
        this.headings= headings;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_viewy, null, true);
        TextView textViewHEADING = (TextView) listViewItem.findViewById(R.id.textViewHEADING);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);

        textViewHEADING.setText(headings[position]);
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],150,150,false));
        return  listViewItem;
    }
}
