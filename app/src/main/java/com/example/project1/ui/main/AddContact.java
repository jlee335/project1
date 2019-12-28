package com.example.project1.ui.main;
import android.content.Context;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.project1.Contact;
import com.example.project1.MainActivity;
import com.example.project1.IOcustom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.project1.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

import static com.example.project1.MyApplication.getAppContext;

public class AddContact extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher_background);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        setSupportActionBar(toolbar);
    }

    public void press_cancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //Main menu 로 돌아감...ㅜㅜ
    }

    public void press_save(View view){

        Contact tmpcontact = new Contact("Jay Lee","01054375220");


        IOcustom iocustom = new IOcustom();
        Intent intent = new Intent(this, MainActivity.class);

        EditText name = (EditText) findViewById(R.id.typename);
        String n_name = name.getText().toString();

        EditText addr = (EditText) findViewById(R.id.typeaddr);
        String n_addr = addr.getText().toString();

        Contact ncont = new Contact(n_name,n_addr);



        //새로운 contact 를 만들었으니, 이제 저장하자
        Gson gson = new Gson(); //gson object 지정
        List<Contact> contacts = new ArrayList<>();
        String json = iocustom.readFromFile(getAppContext()); //파일 열기
        if(json == null){
            Log.e("login activity","Non-existing DATABASE");
            contacts.add(ncont);
        }else{
            Contact[] array = gson.fromJson(json, Contact[].class); //json 에서 얻어가기
            Collections.addAll(contacts,array);
            contacts.add(ncont); //List 에서 추가
        }



        json = gson.toJson(contacts); //리스트를 json 으로 만들기

        iocustom.writeToFile(json,getAppContext()); // 파일에 덮어쓰기

        //LOAD JSON FILE
        startActivity(intent); //main 으로 복귀
    }

}
