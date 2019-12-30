package com.example.project1.Gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.project1.MyApplication;
import com.example.project1.R;

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

