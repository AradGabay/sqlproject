package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Grades.TABLE_GRADES;
import static com.example.myapplication.Students.TABLE_STUDENTS;

public class SortTables extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ListView lvrecords,lvtables,lvsort;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter adp;

    String[] tables,fields;
    int table;

    String[] columns = null;
    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;
    String limit = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        lvtables =(ListView)findViewById(R.id.Tabletosort);
        lvrecords =(ListView)findViewById(R.id.aftersort);
        lvsort =(ListView)findViewById(R.id.byfield);

        hlp = new HelperDB(this);

        lvtables.setOnItemClickListener(this);
        lvtables.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvsort.setOnItemClickListener(this);
        lvsort.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        tables = new String[]{TABLE_STUDENTS,TABLE_GRADES};
        adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tables);
        lvtables.setAdapter(adp);




    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent == lvtables){
            table = position;
            if(position == 0){
                fields = new String[]{Students.KEY_ID,Students.NAME,Students.ADDRESS,Students.CEL_NUM,Students.HOME_NUM,Students.FATHER_NAME,Students.FATHER_NUM,Students.MOTHER_NAME,Students.MOTHER_NUM};
            }else{
                fields = new String[]{Students.KEY_ID,Grades.QUARTER,Grades.SUBJECT,Grades.GRADE};
            }
            adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,fields);
            lvsort.setAdapter(adp);
        }else if(parent == lvsort){
            tbl = new ArrayList<>();
            db = hlp.getReadableDatabase();
            orderBy = fields[position];
            if(table == 0){
                crsr = db.query(TABLE_STUDENTS, columns,selection,selectionArgs,groupBy,having,orderBy,limit);
                int col1 = crsr.getColumnIndex(Students.KEY_ID);
                int col2 = crsr.getColumnIndex(Students.NAME);
                int col3 = crsr.getColumnIndex(Students.ADDRESS);
                int col4 = crsr.getColumnIndex(Students.CEL_NUM);
                int col5 = crsr.getColumnIndex(Students.HOME_NUM);
                int col6 = crsr.getColumnIndex(Students.FATHER_NAME);
                int col7 = crsr.getColumnIndex(Students.FATHER_NUM);
                int col8 = crsr.getColumnIndex(Students.MOTHER_NAME);
                int col9 = crsr.getColumnIndex(Students.MOTHER_NUM);

                crsr.moveToFirst();
                while (!crsr.isAfterLast()){
                    int key = crsr.getInt(col1);
                    String name = crsr.getString(col2);
                    String address = crsr.getString(col3);
                    int celnum = crsr.getInt(col4);
                    int homenum = crsr.getInt(col5);
                    String fathername = crsr.getString(col6);
                    int fathernum = crsr.getInt(col7);
                    String momname = crsr.getString(col8);
                    int momnum = crsr.getInt(col9);
                    String tmp = "" + key + ", " + name + ", " + address + ", " + celnum + ", " + homenum + ", " + fathername + ", " + fathernum + ", " + momname + ", " + momnum;
                    tbl.add(tmp);
                    crsr.moveToNext();
                }
            }else{
                crsr = db.query(TABLE_GRADES, columns,selection,selectionArgs,groupBy,having,orderBy,limit);
                int col1 = crsr.getColumnIndex(Students.KEY_ID);
                int col2 = crsr.getColumnIndex(Grades.QUARTER);
                int col3 = crsr.getColumnIndex(Grades.SUBJECT);
                int col4 =crsr.getColumnIndex(Grades.GRADE);

                crsr.moveToFirst();
                while(!crsr.isAfterLast()){
                    int key = crsr.getInt(col1);
                    int qua = crsr.getInt(col2);
                    String sub = crsr.getString(col3);
                    int grade = crsr.getInt(col4);
                    String tmp = ""+key+", "+qua+", "+sub+", "+grade;
                    tbl.add(tmp);
                    crsr.moveToNext();
                }

            }
            crsr.close();
            db.close();
            adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tbl);
            lvrecords.setAdapter(adp);
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
                Intent intent = new Intent(this, InsertData.class);
                startActivity(intent);
                break;
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
            case(R.id.credits):{
                Intent intent = new Intent(this,Credits.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }


}
