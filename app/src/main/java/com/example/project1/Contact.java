package com.example.project1;

import android.widget.ImageView;

public class Contact {
    private String name;
    private String number;
    private ImageView image;


    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
    public ImageView getImage(){
        return image;
    }


    public Contact(String nName, String nNumber){
        name = nName;
        number = nNumber;
    }
}
