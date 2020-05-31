package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
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
