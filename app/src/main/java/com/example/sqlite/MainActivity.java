package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    DatabaseHelper databaseHelper;
    ArrayList<Map<String, String>> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id  = Integer.parseInt(arrayList.get(i).get("id"));
                databaseHelper.delete(id);
                arrayList.clear();
                loadData();
                return true;
            }
        });
    }

    public void openInput(View view) {
        Intent intent = new Intent(this, Input.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        arrayList = databaseHelper.getAllStudents();
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,
                android.R.layout.simple_list_item_2,new String[]{"name","address"},
                new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }

}