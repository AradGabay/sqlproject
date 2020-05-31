package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class InsertData extends AppCompatActivity {
    HelperDB hlp;
    SQLiteDatabase db;
    EditText name,address,cellnum,homenum,fathername,fathernum,momname,momnum,subject,quarter,grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        name = findViewById(R.id.studname);
        address = findViewById(R.id.studaddress);
        cellnum = findViewById(R.id.cellnum);
        homenum = findViewById(R.id.homenum);
        fathername = findViewById(R.id.fathername);
        fathernum = findViewById(R.id.fathernum);
        momname = findViewById(R.id.momname);
        momnum = findViewById(R.id.momnum);
        subject = findViewById(R.id.subject);
        quarter = findViewById(R.id.quarter);
        grade = findViewById(R.id.grade);



    }

    public void applygradedata(View view) {
        if(!(Integer.parseInt(grade.getText().toString())<0)||!(Integer.parseInt(grade.getText().toString())>100)) {
            ContentValues cv = new ContentValues();

            cv.put(Grades.GRADE, Integer.parseInt(grade.getText().toString()));
            cv.put(Grades.QUARTER, Integer.parseInt(quarter.getText().toString()));
            cv.put(Grades.SUBJECT, subject.getText().toString());

            db = hlp.getWritableDatabase();
            db.insert(Grades.TABLE_GRADES, null, cv);
            db.close();
        }

    }

    public void applystudentdata(View view) {
        ContentValues cv = new ContentValues();

        cv.put(Students.NAME,name.getText().toString());
        cv.put(Students.ADDRESS,address.getText().toString());
        cv.put(Students.CEL_NUM,Integer.parseInt(cellnum.getText().toString()));
        cv.put(Students.HOME_NUM,Integer.parseInt(homenum.getText().toString()));
        cv.put(Students.FATHER_NAME,fathername.getText().toString());
        cv.put(Students.FATHER_NUM,Integer.parseInt(fathernum.getText().toString()));
        cv.put(Students.MOTHER_NAME,momname.getText().toString());
        cv.put(Students.MOTHER_NUM,Integer.parseInt(momnum.getText().toString()));

        db = hlp.getWritableDatabase();
        db.insert(Students.TABLE_STUDENTS,null,cv);
        db.close();
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
                Intent intent = new Intent(this,   InsertData.class);
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

