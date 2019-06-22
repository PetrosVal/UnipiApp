package com.example.unipiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class search_members extends AppCompatActivity {

    private ListView listView;
    private DBhelper2 database;

    private List<String> resultList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmembers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        listView =(ListView)findViewById(R.id.listView1);
        database =new  DBhelper2(this);

        Intent intent = getIntent();
        String[] myItems =  getIntent().getStringArrayExtra("items");
        showMembers(myItems);


    }
    private void showMembers(String[] items){

        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(aa);
        listView.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, members.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
