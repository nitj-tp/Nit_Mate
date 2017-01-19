package com.parveen.nitmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Hp W8.1 ori on 24-12-2016.
 */

public class DateDb  {
    Context context;
    public String subject;
    Cursor c;
    public SQLiteDatabase db;
    public DateDb(Context c) {
        context =c;

        db = context.openOrCreateDatabase("StudentSub",Context.MODE_PRIVATE,null);
        //db.execSQL("DROP TABLE subjectlist");

        //Toast.makeText(context , subject+" yess" , Toast.LENGTH_LONG).show();
        db.execSQL("CREATE TABLE IF NOT EXISTS subjectlist (subject VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS att(date varchar, status integer,subject varchar);");
        // db.execSQL("DROP TABLE att");

    }
    public void display()
    {       StringBuilder sb = new StringBuilder();
        c = db.rawQuery("SELECT * FROM att" , null);
        if(c.getCount()!=0) {
            while (c.moveToNext())
            {
                sb.append(c.getString(0)+c.getString(1)+c.getString(2)+"  ");
            }
        }
        // Toast.makeText(context , sb.toString() , Toast.LENGTH_LONG).show();
    }
    public void addsubject(String subb)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS subjectlist (subject VARCHAR);");
        c = db.rawQuery("SELECT * FROM subjectlist WHERE subject = '"+subb+"'",null);
        if(c.getCount()!=0)
        {
            Toast.makeText(context , "ERROR Subject already exits" , Toast.LENGTH_LONG).show();
        }
        else {
            db.execSQL("INSERT INTO subjectlist VALUES( '" + subb + "')");
        }
        //Toast.makeText(context,"Subject is "+subb,Toast.LENGTH_LONG).show();

    }

    public void addsub( )
    {
        // subject = subb;
        db.execSQL("CREATE TABLE IF NOT EXISTS att(date varchar, status integer,subject varchar);");

    }
    public void add(String date ,int a ,String subject)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS att(date varchar, status integer,subject varchar);");
        Cursor cc=db.rawQuery("SELECT * FROM att WHERE ( date ='"+date+"' AND subject ='"+subject+"')",null);
        if(cc.getCount()!=0) {

            db.execSQL("UPDATE att SET status='" + a + "' WHERE ( date ='" + date + "' AND subject ='"+subject+"')");

        }
        else
        {
            db.execSQL("INSERT INTO att VALUES('"+date+"','"+a+"','"+subject+"');");
            // Toast.makeText(context ,"added" , Toast.LENGTH_LONG).show();
        }


    }
    public Context selectall()
    {
        //Cursor c = db.rawQuery("SELECT * FROM att" , null);
        return context;

    }
    public void delete(String sub)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS att(date varchar, status integer,subject varchar);");
        db.execSQL("DELETE FROM att WHERE (subject ='"+sub+"')");
        db.execSQL("DELETE FROM subjectlist WHERE subject = '"+sub+"'");
    }
    public void deleteall()
    {
        int a = ifexistatt();
        if(a==1) {
            db.execSQL("DROP TABLE att");
        }
        int b = ifexistsub();
        if(b==1) {
            db.execSQL("DROP TABLE subjectlist");
        }
    }
    public int ifexistatt()
    {
        c = db.rawQuery("SELECT * FROM att",null);
        if(c==null)
        {
            return 0;
        }
        return 1;
    }
    public int ifexistsub()
    {
        c = db.rawQuery("SELECT * FROM subjectlist",null);
        if(c==null)
        {
            return 0;
        }
        return 1;
    }




}
