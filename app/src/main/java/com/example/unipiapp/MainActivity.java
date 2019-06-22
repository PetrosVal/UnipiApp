package com.example.unipiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView contactCard,loginCard;
    private Menu menu;
    private String itemTitle1,itemTitle2,itemTitle3,itemTitle4,itemTitle5;
    private boolean showPop = false,logged;
    private TextView helloText,loginCardText;
    private ImageView icon;
    @Override
    public boolean releaseInstance() {
        return super.releaseInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon = (ImageView) findViewById(R.id.loginCardIcon);
        helloText =(TextView)findViewById(R.id.logged);
        loginCardText =(TextView)findViewById(R.id.loginCardText);
        contactCard =(CardView)findViewById(R.id.contactCard);
        loginCard =(CardView)findViewById(R.id.loginCard);
        contactCard.setOnClickListener(this);
        loginCard.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("LOGGED", MODE_PRIVATE);
        String name = prefs.getString("NAME", "null");
         logged = prefs.getBoolean("LOGGED", false);
        if(logged){
            helloText.setVisibility(View.VISIBLE);
            helloText.setText("HELLO "+ name.toUpperCase()+" HOW ARE YOU TODAY?");
            icon.setImageDrawable(getDrawable(R.drawable.ic_highlight_off_black_24dp));
            loginCardText.setText("Αποσύνδεση");
        }else{
            loginCardText.setText("Σύνδεση");
            helloText.setVisibility(View.GONE);
            icon.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_24dp));
        }
    }

    @Override
    public void onClick(final View v) {
        itemTitle1 =null;
        itemTitle2 = null;
        itemTitle3 = null;
        itemTitle4 = null;
        itemTitle5 = null;
        switch(v.getId()){

            case R.id.contactCard:

                itemTitle4 = "Επαφές";
                break;

            case R.id.loginCard:
                if(logged){
                    SharedPreferences.Editor editor = getSharedPreferences("LOGGED", MODE_PRIVATE).edit();
                    editor.putBoolean("LOGGED",false);
                    editor.apply();
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(this,LoginActivity.class);
                    intent.putExtra("TO","LOGIN");
                    startActivity(intent);
                }
                break;

        }
        final PopupMenu popup = new PopupMenu(MainActivity.this,v);
        popup.getMenuInflater().inflate(R.menu.popup,popup.getMenu());
        // popup.getMenu().findItem(R.id.first).setTitle("test");
        updatePopTitles(popup.getMenu(),itemTitle1,itemTitle2,itemTitle3,itemTitle4,itemTitle5);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getTitle().toString()){

                    case "Επαφές":
                        startActivity(new Intent(getApplicationContext(),members.class));
                        break;

                }
               // startActivity(intent);


                showPop = false;
                return false;
            }
        });

        popup.show();

    }


    public void updatePopTitles(Menu menu, String title1, String title2, String title3, String title4, String title5){
        menu.clear();
        if(title1!=null) {
            MenuItem first = menu.add(Menu.NONE, 1, 0, title1);
        }
        if(title2!=null) {
            MenuItem second = menu.add(Menu.NONE, 2, 0, title2);
        }
        if(title3!=null) {
            MenuItem third = menu.add(Menu.NONE, 3, 0, title3);
        }
        if(title4!=null) {
            MenuItem fourth = menu.add(Menu.NONE, 4, 0, title4);
        }
        if(title5!=null) {
            MenuItem fifth = menu.add(Menu.NONE, 5, 0, title5);
        }
    }


}
