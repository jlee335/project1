package com.example.project1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class IOcustom {
    public void writeToFile(String data, Context context) {
        // Exception 이 안 뜨면, Contacts.json 에 data string 저장. contexts 는 이 앱으로 준다.
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("Contacts.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile(Context context) {
        String res = new String();
        try{
        FileInputStream fis = context.openFileInput("Contacts.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                res = stringBuilder.toString();
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
                Log.e("FILE ERROR","File read failed");
                res = null;
            }

        }catch(FileNotFoundException e){
            Log.e("FILE ERROR","Contacts.json does not exist!!");
            res = null;
        }
        return res;
    }


    public void writeToFileLabel(String data, Context context) {
        // Exception 이 안 뜨면, Contacts.json 에 data string 저장. contexts 는 이 앱으로 준다.
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("labelHist.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFileLabel(Context context) {
        String res = new String();
        try{
            FileInputStream fis = context.openFileInput("labelHist.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                res = stringBuilder.toString();
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
                Log.e("FILE ERROR","File read failed");
                res = null;
            }

        }catch(FileNotFoundException e){
            Log.e("FILE ERROR","labelHist.json does not exist!!");
            res = null;
        }
        return res;
    }
    public void writeToFileCache(String data, Context context) {
        // Exception 이 안 뜨면, Contacts.json 에 data string 저장. contexts 는 이 앱으로 준다.
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("cache.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFileCache(Context context) {
        String res = new String();
        try{
            FileInputStream fis = context.openFileInput("cache.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                res = stringBuilder.toString();
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
                Log.e("FILE ERROR","File read failed");
                res = null;
            }

        }catch(FileNotFoundException e){
            Log.e("FILE ERROR","labelHist.json does not exist!!");
            res = null;
        }
        return res;
    }



}
