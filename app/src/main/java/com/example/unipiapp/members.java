package com.example.unipiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class members extends AppCompatActivity {
    private Spinner drop1,drop2;
    private EditText nameText,emailText,phoneText,emailText2,memberEmail3,memberPhone2;
    private Button addButton,searchButton,deleteButton,updatePhoneButton;
    private DBhelper2 database;
    private ListView listView;
    private RadioGroup radioGroup1;
    private SearchView searchView;
    private static  final String[] grade={"Συγγενής","Συμφοιτητής","Φίλος"};
    private List<String> resultList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
        searchView =(SearchView)findViewById(R.id.searchView1);
        drop1 = (Spinner)findViewById(R.id.spinner1);
        nameText = (EditText)findViewById(R.id.memberName);
        emailText=(EditText)findViewById(R.id.memberEmail);
        emailText2=(EditText)findViewById(R.id.memberEmail2);
        memberEmail3=(EditText)findViewById(R.id.memberEmail3);
        memberPhone2 = (EditText)findViewById(R.id.memberPhone2);
        phoneText= (EditText)findViewById(R.id.memberPhone);
        addButton = (Button)findViewById(R.id.addMemberButton);
        deleteButton = (Button)findViewById(R.id.deleteMemberButton);
        updatePhoneButton = (Button)findViewById(R.id.UpdatePhoneButton);
        listView =(ListView)findViewById(R.id.listView1);
        database =new  DBhelper2(this);
        searchButton = (Button)findViewById(R.id.searchMemberButton);
        //Adapters

        ArrayAdapter<String> adapterforDrop1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, grade);
       // listView.setOnItemClickListener(this);

       drop1.setAdapter(adapterforDrop1);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMember();
            }
        });
        updatePhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePhone();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNewMember();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchView.getQuery().toString().equals(""))
                    searchMembers();

            }
        });

    }

    private void updatePhone(){
        String email=null;
        if( !memberEmail3.getText().toString().equals("") ) {
            SharedPreferences prefs = getSharedPreferences("LOGGED", MODE_PRIVATE);
            String name = prefs.getString("NAME", "null");
            if(prefs.getBoolean("LOGGED", false)){

                Cursor data = database.searchMemberEmail(memberEmail3.getText().toString());
                while(data.moveToNext()){
                    email =  data.getString(2);
                }
                if(email!=null) {
                    database.updatePhonebyEmail(memberEmail3.getText().toString(),memberPhone2.getText().toString() );
                    Toast.makeText(getApplicationContext(), "Phone Updated", Toast.LENGTH_LONG).show();
                    memberEmail3.setText("");
                    memberPhone2.setText("");
                }else
                    Toast.makeText(getApplicationContext(),"Email is not registered", Toast.LENGTH_LONG).show();

            }else
                Toast.makeText(getApplicationContext(),"Your are not Logged in", Toast.LENGTH_LONG).show();


        }
        else{

            if(memberEmail3.getText().toString().equals(""))
                memberEmail3.setError("Provide your Email");

            if(memberPhone2.getText().toString().equals(""))
                memberPhone2.setError("Provide your Phone");

        }
    }

    private void deleteNewMember(){
        String email=null;
        if( !emailText2.getText().toString().equals("") ) {
            SharedPreferences prefs = getSharedPreferences("LOGGED", MODE_PRIVATE);
            String name = prefs.getString("NAME", "null");
            if(prefs.getBoolean("LOGGED", false)){

                Cursor data = database.searchMemberEmail(emailText2.getText().toString());
                while(data.moveToNext()){
                    email =  data.getString(2);
                }
                if(email!=null) {
                    database.deleteRowbyName( emailText2.getText().toString());
                    Toast.makeText(getApplicationContext(), "Member Deleted", Toast.LENGTH_LONG).show();
                    emailText2.setText("");
                }else
                    Toast.makeText(getApplicationContext(),"Email is not registered", Toast.LENGTH_LONG).show();

            }else
                Toast.makeText(getApplicationContext(),"Your are not Logged in", Toast.LENGTH_LONG).show();


        }
        else{

            if(emailText2.getText().toString().equals(""))
                emailText.setError("Provide your Email");

        }
    }

    private void addNewMember(){
        String email=null;
        if(!nameText.getText().toString().equals("") && !emailText.getText().toString().equals("") && !phoneText.getText().toString().equals("")) {
            SharedPreferences prefs = getSharedPreferences("LOGGED", MODE_PRIVATE);
            String name = prefs.getString("NAME", "null");
            if(prefs.getBoolean("LOGGED", false)){

                    Cursor data = database.searchMemberEmail(emailText.getText().toString());
                    while(data.moveToNext()){
                       email =  data.getString(2);
                    }
                    if(email==null) {
                        database.addMember(nameText.getText().toString(), phoneText.getText().toString(), emailText.getText().toString(), drop1.getSelectedItem().toString());
                        Toast.makeText(getApplicationContext(), "Member Added", Toast.LENGTH_LONG).show();
                        nameText.setText("");
                        emailText.setText("");
                        phoneText.setText("");
                    }else
                        Toast.makeText(getApplicationContext(),"Email is already registered", Toast.LENGTH_LONG).show();

            }else
                Toast.makeText(getApplicationContext(),"Your are not Logged in", Toast.LENGTH_LONG).show();


        }
        else{
            if(nameText.getText().toString().equals(""))
                nameText.setError("Provide your Name");
            if(emailText.getText().toString().equals(""))
                emailText.setError("Provide your Email");
            if(phoneText.getText().toString().equals(""))
                phoneText.setError("Provide a Password");
        }
    }

    private void searchMembers(){
        Cursor data = null;
        resultList.clear();
        switch (radioGroup1.getCheckedRadioButtonId()){

            case R.id.nameRadioButton:
                data =database.searchMember("NAME",searchView.getQuery().toString());
                break;
            case  R.id.emailRadioButton:
                data =database.searchMember("EMAIL",searchView.getQuery().toString());
                break;
            case R.id.phoneRadioButton:
               data= database.searchMember("PHONE",searchView.getQuery().toString());
                break;
        }
        while(data.moveToNext()){
            resultList.add("Όνομα: "+data.getString(1)+" Phone: "+data.getString(2) +" Email: "+data.getString(3) +" Ιδιότητα: "+data.getString(4));
        }
        String[] items = resultList.toArray(new String[resultList.size()]); //Converting list to array for the adapter bellow
        data.close();
        Intent intent = new Intent(this, search_members.class);
        intent.putExtra("items", items);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
