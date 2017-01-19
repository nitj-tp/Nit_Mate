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
public class CustomList extends ArrayAdapter<String> {
    private String[] names;
    private String[] jobs;
    private Bitmap[] bitmaps;
    private Activity context;

    public CustomList(Activity context, String[] names, Bitmap[] bitmaps ,  String[] jobs) {
        super(context, R.layout.image_list_view, names);
        this.context = context;
        this.names= names;
        this.jobs = jobs;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);
        TextView textViewHEADING = (TextView) listViewItem.findViewById(R.id.textViewHEADING);
        TextView textViewJOB = (TextView) listViewItem.findViewById(R.id.textViewJOB);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);

        textViewHEADING.setText(names[position]);
        textViewJOB.setText(jobs[position]);
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],150,150 , false));
        return  listViewItem;
    }
}