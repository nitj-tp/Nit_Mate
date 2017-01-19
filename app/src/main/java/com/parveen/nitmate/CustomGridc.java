package com.parveen.nitmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hp W8.1 ori on 04-01-2017.
 */

public class CustomGridc extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> web;
    private final int Imageid;
    LinearLayout ll;

    public CustomGridc(Context c, ArrayList<String> web ) {
        mContext = c;
        Imageid = R.drawable.issue;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.single_grid, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_textc);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_imagec);
            textView.setText(web.get(position));
            imageView.setImageResource(Imageid);
        } else {
            grid =  convertView;
        }
        //ll = (LinearLayout)grid.findViewById(R.id.ll);
        //ll.setAlpha(0.3f);
        return grid;
    }
}