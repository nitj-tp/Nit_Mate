package com.parveen.nitmate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Calendar extends Activity {

    public GregorianCalendar month, itemmonth;// calendar instances.
    Cursor c,cc;
    SQLiteDatabase db;

    public CalendarAdapter adapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot
    // marker.
    DateDb datedb;
    String data ;
    static String subject_name;
    float  percent =0;
    int status , present =0 , absent =0 , holiday =0 , total =0;
    TextView pr ,ab , ttl ,pcnt;

    public ArrayList items; // container to store calendar items which
    // needs showing the event marker
    String selectedGridDate;

    RadioGroup radioGroup;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        pr = (TextView)findViewById(R.id.present);
        ab = (TextView)findViewById(R.id.absent);
        ttl = (TextView)findViewById(R.id.total);
        pcnt = (TextView)findViewById(R.id.percent);
        datedb = new DateDb(getApplicationContext());

        Context cont=datedb.selectall();
        db = cont.openOrCreateDatabase("StudentSub",Context.MODE_PRIVATE,null);
        presentShow();
        //subject_name = getIntent().getExtras().getString("String_name");
        Locale.setDefault( Locale.US );
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();

        items = new ArrayList();
        adapter = new CalendarAdapter(this, month,subject_name);

        //Toast.makeText(this , "subject in calendar "+subject_name , Toast.LENGTH_LONG).show();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        handler = new Handler();
        handler.post(calendarUpdater);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        setNextMonth();
                        refreshCalendar();

                    }
                });


        gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v,
                                    int position, long id) {

                ((CalendarAdapter) parent.getAdapter()).setSelected(v);
                selectedGridDate = (String) CalendarAdapter.dayString.get(position);
                //***************************************
                //  Toast.makeText(getApplicationContext(), "date"+selectedGridDate , Toast.LENGTH_LONG).show();

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*", "");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);

                // navigate to next or previous month on clicking offdays.

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    // refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    // refreshCalendar();
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v);

                // showToast(selectedGridDate);
                showInputDialog();

            }
        });
    }
    static void getData(String sub)
    {
        subject_name=sub;


    }
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Calendar.this);

        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Calendar.this);
        alertDialogBuilder.setView(promptView);

        radioGroup = (RadioGroup) promptView.findViewById(R.id.radiogrp);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        if (checkedRadioButtonId == -1) {
                            // No item selected
                        }
                        else{
                            if (checkedRadioButtonId == R.id.radio1) {
                                //Toast.makeText(getApplication() , "Present" , Toast.LENGTH_LONG).show();
                                datedb.addsub();
                                datedb.add(selectedGridDate , 1,subject_name);

                                // Do something with the button
                            }
                            if (checkedRadioButtonId == R.id.radio2) {
                                // Toast.makeText(getApplication() , "Absent" , Toast.LENGTH_LONG).show();
                                datedb.addsub();
                                datedb.add(selectedGridDate , 2,subject_name);

                                // Do something with the button
                            }
                            if (checkedRadioButtonId == R.id.radio3) {
                                // Toast.makeText(getApplication() , "Holiday" , Toast.LENGTH_LONG).show();
                                datedb.addsub();
                                datedb.add(selectedGridDate , 3,subject_name);
                                // Do something with the button
                            }
                            presentShow();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }
    public void presentShow()
    {
        present =0;absent =0;percent =0;
        total=0;
        //  Toast.makeText(getApplicationContext(),"String in Calendar  mm"+subject_name,Toast.LENGTH_LONG).show();
        cc =  db.rawQuery("SELECT * FROM att WHERE  subject = '"+subject_name+"' " , null);
        if(cc.getCount()!=0) {
            cc.moveToFirst();
            while (!cc.isAfterLast()) {
                data = cc.getString(cc.getColumnIndex("date"));
                status = cc.getInt(cc.getColumnIndex("status"));
                cc.moveToNext();
                if (status == 1) {

                    present++;
                }
                if (status == 2) {
                    absent++;
                }
                if (status == 3) {
                    holiday++;
                }
            }

            total = present + absent;
            percent = 100 * present / (present + absent);
            pr.setText("Present = " + present);
            ab.setText("Absent = " + absent);
            ttl.setText("Total = " + total);
            pcnt.setText("Percent = " + percent);
        }
    }
    public void showMessage(String title,String message)
    {
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    protected void setNextMonth() {

        if (month.get(GregorianCalendar.MONTH) == month.getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {

        //  Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {
        TextView title = (TextView) findViewById(R.id.title);

        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("yyyy MMMM", month));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add("2012-09-12");
                items.add("2012-10-07");
                items.add("2012-10-15");
                items.add("2012-10-20");
                items.add("2012-11-30");
                items.add("2012-11-28");
            }

            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    };
}
