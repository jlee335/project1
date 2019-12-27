package com.example.project1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Contact_Fragment extends Fragment {
    Contact tmpcontact = new Contact("Jay Lee","01054375220");

    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Contact> contacts = new ArrayList<>();

    public Contact_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
        contacts.add(tmpcontact);
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
