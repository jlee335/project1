package com.example.project1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import static com.example.project1.MyApplication.getAppContext;

public class Gallery_Fragment extends Fragment {

    MyApplication app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MyApplication) getAppContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_,container,false);

        int img[]= {
                R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,
                R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,R.drawable.a,
        };

        MyAdapter adapter = new MyAdapter (
                app = (MyApplication) getAppContext(),
                R.layout.row,
                img);

        GridView gv = (GridView)view.findViewById(R.id.gridView1);
        gv.setAdapter(adapter);

        return view;
    }
}

