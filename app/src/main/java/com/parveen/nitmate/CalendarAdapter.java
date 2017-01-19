package com.parveen.nitmate;

/**
 * Created by Hp W8.1 ori on 23-12-2016.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.util.GregorianCalendar.*;


public class CalendarAdapter extends BaseAdapter {
    public Context mContext;

    private java.util.Calendar month;
    public GregorianCalendar pmonth; // calendar instance for previous month
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    SQLiteDatabase db1;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;
    DateDb colordate;
    Cursor datec;
    private ArrayList items;
    String subject_name;
    public static List dayString;

    public View previousView;

    public CalendarAdapter(Context c, GregorianCalendar monthCalendar , String sub) {


        CalendarAdapter.dayString = new ArrayList();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        System.out.println(":::::::::::::::::::::MONTH CALENDAR:::::::::::::::"
                + monthCalendar);
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;

        subject_name = sub;
        //Toast.makeText(mContext , "in adapter = "+subject_name , Toast.LENGTH_LONG).show();
        db1 = mContext.openOrCreateDatabase("StudentSub",Context.MODE_PRIVATE,null);
        month.set(DAY_OF_MONTH, 1);

        this.items = new ArrayList();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        System.out
                .println(":::::::::::::::::CURRENT DATE STRING::::::::::::::;"
                        + curentDateString);
        refreshDays();
    }

    public void setItems(ArrayList items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).equals("1")) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {
        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);

        }
        dayView = (TextView) v.findViewById(R.id.date);
        // separates daystring into parts.
        String[] separatedTime = dayString.get(position).toString().split("-");
        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");

        //Toast.makeText(mContext ,"grid alue  "+ gridvalue , Toast.LENGTH_LONG).show();

        System.out
                .println(":::::::::::::::::::::::ADPTER SPLITTED MONTH:::::::::::::::::::::"
                        + separatedTime[1]);
        // checking whether the day is in current month or not.
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            // setting offdays to white color.
            dayView.setTextColor(Color.parseColor("#CECECE"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.parseColor("#CECECE"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            if (dayString.get(position).equals(curentDateString)) {
                dayView.setTextColor(Color.parseColor("#000000"));
            } else {
                dayView.setTextColor(Color.parseColor("#257247"));
                colordate= new DateDb(mContext);
                // datec = colordate.selectall();
                String s = dayString.get(position).toString();

                //   Toast.makeText(mContext , s , Toast.LENGTH_LONG).show();


                // db1.execSQL("CREATE TABLE IF NOT EXISTS att(date varchar, status number);");

                Cursor datec=db1.rawQuery("SELECT * FROM att WHERE ( date ='"+s+"' AND subject = '"+subject_name+"' )",null);
                //  Toast.makeText(mContext , "sub = "+subject_name ,   Toast.LENGTH_LONG).show();
                int data=-1;
                if(datec.getCount()!=0) {
                    if (datec.moveToFirst()) {

                        data = datec.getInt(datec.getColumnIndex("status"));
                        // Toast.makeText(mContext, "not empty  "+data, Toast.LENGTH_LONG).show();
                    }
                }
//               String data = datec.getString(datec.getColumnIndex("date"));
//                int st = datec.getInt(datec.getColumnIndex("status"));

                //   Toast.makeText(mContext , "a = "+st,Toast.LENGTH_LONG).show();
                if(data==1)
                {
                    dayView.setTextColor(Color.parseColor("#0000FF"));
                }
                if(data==2)
                {
                    dayView.setTextColor(Color.parseColor("#FF0000"));
                }
                if(data==3)
                {
                    dayView.setTextColor(Color.parseColor("#CDDC39"));
                }
            }

        }

        if (dayString.get(position).equals(curentDateString)) {
            setSelected(v);
            previousView = v;
        } else {
            v.setBackgroundResource(R.color.colorPrimaryDark);
        }
        dayView.setText(gridvalue);

        // create date string for comparison
        String date = dayString.get(position).toString();

        if (date.length() == 1) {
            date = "0" + date;
        }

        String monthStr = "" + (month.get(MONTH));
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        // show icon if date is not empty and it exists in the items array
        ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.INVISIBLE);
        }
        return v;
    }

    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.color.colorPrimaryDark);


            // Toast.makeText(mContext , "control back_press" , Toast.LENGTH_LONG).show();
        }

        previousView = view;
        view.setBackgroundResource(R.color.colorPrimary);
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(DAY_OF_MONTH, calMaxP);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(DATE, 1);
            dayString.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.getActualMinimum(MONTH) == month.get(MONTH)) {
            pmonth.set((month.get(YEAR) - 1),
                    month.getActualMaximum(MONTH), 1);
        } else {
            pmonth.set(MONTH,
                    month.get(MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(DAY_OF_MONTH);

        return maxP;
    }

}
