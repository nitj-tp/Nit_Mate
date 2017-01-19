package com.parveen.nitmate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDemo extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener {
ImageView back;
    ArrayList<String> listItems = new ArrayList<String>();
    String pos, subject_name;
    SQLiteDatabase db;
    DateDb datedb;
    String sub;
    Cursor lc;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter = 0;
    private ListView mListView;
    Button b;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_demo);
        db = openOrCreateDatabase("StudentSub", Context.MODE_PRIVATE, null);
        datedb = new DateDb(getApplicationContext());
        //*******
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(in);
            }
        });
        //********
        b = (Button) findViewById(R.id.b1);
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.list);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);

        b.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        lc = db.rawQuery("SELECT * FROM subjectlist", null);
        while (lc.moveToNext()) {
            listItems.add(lc.getString(0));
        }
        mListView.setAdapter(adapter);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                // TODO Auto-generated method stub


                sub = listItems.get(pos).toString();
                PopupMenu popup = new PopupMenu(ListDemo.this,arg1 );
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                Toast.makeText(getApplicationContext(), "Long press", Toast.LENGTH_LONG).show();
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Delete"))
                        {
                           showAlert(item , pos);
                        }
                        if(item.getTitle().equals("Delete all"))
                        {

                           showAlertAll(item);

                        }
                        return true;
                    }
                }
                );
                popup.show();
                return true;
            }
        });
    }
    protected void showAlertAll(final MenuItem item)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(ListDemo.this);
        View promptView = layoutInflater.inflate(R.layout.show_alert_all , null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListDemo.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK" , new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListDemo.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        datedb.deleteall();
                        listItems.clear();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel" , new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    protected void showAlert(final MenuItem item , final int pos)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(ListDemo.this);
        View promptView = layoutInflater.inflate(R.layout.show_alert , null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListDemo.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK" ,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListDemo.this, "You Clicked : " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        datedb.delete(sub);
                        adapter.remove(listItems.get(pos));
                        adapter.notifyDataSetChanged();
                    }
                } )
                .setNegativeButton("Cancel" , new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(ListDemo.this);
        View promptView = layoutInflater.inflate(R.layout.sub_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListDemo.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());
                        if (editText.getText().toString().trim().length() == 0) {
                            showMessage("Error", "Please enter Valid Subject Name");
                            return;
                        } else {
                            subject_name = editText.getText().toString();
                            int flag=0;

                            for(int i =0;i<listItems.size();i++)
                            {
                                if(subject_name.equals(listItems.get(i)))
                                {
                                    flag =1;
                                    showMessage("Error" , "Subject name already exists");
                                    break;
                                }
                            }
                            if(flag==0) {
                                listItems.add(subject_name);
                                datedb.addsubject(subject_name);
                            }
                            //Code to store data on database
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

    public void showMessage(String title, String message) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        listItems.add("Clicked : " + clickCounter++);
        adapter.notifyDataSetChanged();
    }

    protected ListView getListView() {
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.list);
        }
        return mListView;
    }

    @Override
    public void onClick(View v) {

        addItems(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = listItems.get(position);
        // Toast.makeText(getApplicationContext(),"sub in cal = "+pos, Toast.LENGTH_SHORT).show();

        Intent in = new Intent(getApplicationContext(), Calendar.class);
        // in.putExtra("Subject_name" ,pos );
        startActivity(in);
        Calendar.getData(pos);
    }
}


