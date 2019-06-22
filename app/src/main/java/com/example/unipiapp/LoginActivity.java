package com.example.unipiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout textInputLayout;
    Button regButton,singButton;
    DBhelper database;
    EditText name,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        database = new DBhelper(this);
        textInputLayout = (TextInputLayout)findViewById(R.id.textLayout1);
        name =(EditText)findViewById(R.id.nametextbox);
        email=(EditText)findViewById(R.id.email);
        password= (EditText)findViewById(R.id.password);
        singButton =(Button) findViewById(R.id.singinButton);
        regButton = (Button)findViewById(R.id.registerButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regButton.getText().equals("REGISTER")){
                    Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                    intent1.putExtra("TO","REGISTER");
                    startActivity(intent1);
                }else{
                    if(!name.getText().toString().equals("") && !email.getText().toString().equals("") && !password.getText().toString().equals("")){
                        database.addUser(name.getText().toString(),email.getText().toString(),password.getText().toString());
                        database.close();
                        Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                        intent1.putExtra("TO","LOGIN");
                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(),"User added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                       if(name.getText().toString().equals(""))
                           name.setError("Provide your Name");
                        if(email.getText().toString().equals(""))
                            email.setError("Provide your Email");
                        if(password.getText().toString().equals(""))
                            password.setError("Provide a Password");
                    }
                }

            }
        });
        if(intent.getStringExtra("TO").equals("REGISTER")){
            textInputLayout.setVisibility(View.VISIBLE);
            singButton.setVisibility(View.GONE);
            regButton.setText("SUBMIT");

        }
        else{
            singButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!email.getText().toString().equals("") && !password.getText().toString().equals("")){
                        Cursor data = database.login(email.getText().toString());
                        while(data.moveToNext()) {
                            if (data.getString(3).toString().equals(password.getText().toString())) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                SharedPreferences.Editor editor = getSharedPreferences("LOGGED", MODE_PRIVATE).edit();
                                editor.putBoolean("LOGGED", true);
                                editor.putString("NAME", data.getString(1));
                                editor.apply();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Password or Username is incorrenct", Toast.LENGTH_LONG).show();
                                password.setError("Wrong Password");
                            }
                        }

                    }
                    else{

                    }
                }
            });
        }

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
