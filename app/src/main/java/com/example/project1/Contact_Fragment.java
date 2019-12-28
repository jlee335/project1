package com.example.project1;

import android.content.Context;

import java.io.BufferedReader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;

public class Contact_Fragment extends Fragment {


    private Contact tmpcontact = new Contact("Jay Lee","01054375220");

    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Contact> contacts = new ArrayList<>();





    public Contact_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        IOcustom iocustom = new IOcustom();

        super.onCreate(savedInstanceState);

        //List 를 JSON 으로 저장
        //Context context = getContext();
        //String json = new Gson().toJson(contacts);
        //writeToFile(json,getContext());


        //JSON --> List<Contact> 변환
        String json = iocustom.readFromFile(getContext());
        if(json == null){
            contacts.add(tmpcontact);
            contacts.add(tmpcontact);
        }else{
            Gson gson = new Gson();
            Contact[] array = gson.fromJson(json, Contact[].class);
            contacts = Arrays.asList(array);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.contactRV);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactAdapter(contacts);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
