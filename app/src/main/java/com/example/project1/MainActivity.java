package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.project1.ui.main.AddContact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.project1.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {


    public void buttonDo(){
        Intent intent = new Intent(this, AddContact.class);
        startActivity(intent); // intent 를 통해 새 activity 에 접속?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //첫 화면은 activity_main.xml 로


        //Viewpager 및 Adapter 를 만들어주자
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        //TabLayout object (UI에 있는 것) 에 적용시키자
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //FAB
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonDo();
            }
        });
    }
}
