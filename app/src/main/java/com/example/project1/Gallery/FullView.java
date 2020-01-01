package com.example.project1.Gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project1.R;

import java.io.File;

public class FullView extends AppCompatActivity {

    private View decorView;
    private int uiOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_view);


        ImageView imageView = findViewById(R.id.img_full);
        String img_id = getIntent().getExtras().getString("img_id");
        String filename = getIntent().getExtras().getString("filename");
        TextView tv = findViewById(R.id.imName);
        tv.setText(filename);
        File imfile = new File(img_id);
        Glide
                .with(getBaseContext())
                .load(Uri.fromFile(imfile))
                .into(imageView);
        //imageView.setImageResource(img_id);
    }
}

