package com.example.project1.Contacts;


public class Contact {
    private String name;
    private String number;

    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }


    public Contact(String nName, String nNumber){
        name = nName;
        number = nNumber;
    }
}
