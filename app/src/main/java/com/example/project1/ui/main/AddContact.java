package com.example.project1.ui.main;
import android.content.Context;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.project1.Contact;
import com.example.project1.MainActivity;

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

public class AddContact extends AppCompatActivity {

    private void writeToFile(String data, Context context) {
        // Exception 이 안 뜨면, Contacts.json 에 data string 저장. contexts 는 이 앱으로 준다.
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("Contacts.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("Contacts.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString); //line by line 으로 파일 읽기. string 으로 돌려받기.
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


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

    public void press_save(View view){
        Intent intent = new Intent(this, MainActivity.class);

        EditText name = (EditText) findViewById(R.id.typename);
        String n_name = name.getText().toString();

        EditText addr = (EditText) findViewById(R.id.typeaddr);
        String n_addr = addr.getText().toString();

        Contact ncont = new Contact(n_name,n_addr);

        //새로운 contact 를 만들었으니, 이제 저장하자

        String json = readFromFile(getApplicationContext()); //파일 열기
        Gson gson = new Gson(); //gson object 지정

        Contact[] array = gson.fromJson(json, Contact[].class); //json 에서 얻어가기
        List<Contact> contacts = new ArrayList<Contact>();
        Collections.addAll(contacts,array);

        contacts.add(ncont); //List 에서 추가

        json = new Gson().toJson(contacts); //리스트를 json 으로 만들기
        writeToFile(json,getApplicationContext()); // 파일에 덮어쓰기

        //LOAD JSON FILE
        startActivity(intent); //main 으로 복귀
    }

}
