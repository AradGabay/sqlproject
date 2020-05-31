package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.myapplication.Grades.TABLE_GRADES;
import static com.example.myapplication.Students.KEY_ID;
import static com.example.myapplication.Students.TABLE_STUDENTS;

public class WatchTables extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ListView lvrecords, lvtables;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter adp;
    AlertDialog.Builder adb;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lvtables = findViewById(R.id.lvtables);
        lvrecords = findViewById(R.id.lvrecords);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        lvtables.setOnItemClickListener(this);
        lvtables.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvrecords.setOnItemClickListener(this);
        lvrecords.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


        String[] tables = {"Students Table", "Grades Table"};
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, tables);
        lvtables.setAdapter(adp);

        choice = 0;

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if(parent == lvtables){
            choice = position;
            tbl = new ArrayList<>();
            db=hlp.getReadableDatabase();
            if(position==0){
                crsr = db.query(TABLE_STUDENTS,null,null,null,null,null,null);
                int col1 = crsr.getColumnIndex(KEY_ID);
                int col2 = crsr.getColumnIndex(Students.NAME);
                int col3 = crsr.getColumnIndex(Students.ADDRESS);
                int col4 = crsr.getColumnIndex(Students.CEL_NUM);
                int col5 = crsr.getColumnIndex(Students.HOME_NUM);
                int col6 = crsr.getColumnIndex(Students.FATHER_NAME);
                int col7 = crsr.getColumnIndex(Students.FATHER_NUM);
                int col8 = crsr.getColumnIndex(Students.MOTHER_NAME);
                int col9 = crsr.getColumnIndex(Students.MOTHER_NUM);

                crsr.moveToFirst();
                while(!crsr.isAfterLast()) {
                    int key = crsr.getInt(col1);
                    String name = crsr.getString(col2);
                    String address = crsr.getString(col3);
                    String celnum = crsr.getString(col4);
                    String homenum = crsr.getString(col5);
                    String fathername = crsr.getString(col6);
                    String fathernum = crsr.getString(col7);
                    String mothername = crsr.getString(col8);
                    String mothernum = crsr.getString(col9);
                    String studrecord = "" + key + ", " + name + ", " + address + ", " + celnum + ", " + homenum + ", " + fathername + ", " + fathernum + ", " + mothername + ", " + mothernum;
                    tbl.add(studrecord);
                    crsr.moveToNext();

                }
            }
            else {
                crsr = db.query(TABLE_GRADES, null, null, null, null, null, null);
                int col1 = crsr.getColumnIndex(Students.KEY_ID);
                int col2 = crsr.getColumnIndex(Grades.QUARTER);
                int col3 = crsr.getColumnIndex(Grades.SUBJECT);
                int col4 = crsr.getColumnIndex(Grades.GRADE);

                crsr.moveToFirst();
                while(!crsr.isAfterLast()){
                    int key = crsr.getInt(col1);
                    int qua = crsr.getInt(col2);
                    String sub = crsr.getString(col3);
                    int grade = crsr.getInt(col4);
                    String graderecord = ""+key+", "+qua+", "+sub+", "+grade;
                    tbl.add(graderecord);
                    crsr.moveToNext();
                }

            }
            crsr.close();
            db.close();
            adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tbl);
            lvrecords.setAdapter(adp);

        }else{
            String recordToDel = tbl.get(position);
            adb = new AlertDialog.Builder(this);
            adb.setTitle("DELETE RECORD");
            adb.setMessage("Are you sure you want to delete "+recordToDel+"?");
            adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db = hlp.getWritableDatabase();
                            if(choice == 0){
                                db.delete(TABLE_STUDENTS,KEY_ID+"=?",new String[]{Integer.toString(position+1)});
                            } else{
                                db.delete(TABLE_GRADES,KEY_ID+"=?",new String[]{Integer.toString(position+1)});
                            }
                            db.close();
                            tbl.remove(position);
                            adp.notifyDataSetChanged();
                        }
            });
            adb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
               AlertDialog ad = adb.create();
               ad.show();
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case(R.id.insertData):{
                Intent intent = new Intent(this,EnterData.class);
                startActivity(intent);
            }
            case (R.id.watchTables): {
                Intent intent = new Intent(this, WatchTables.class);
                startActivity(intent);
                break;
            }
            case (R.id.sortTables): {
                Intent intent = new Intent(this, SortTables.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

}


















