package com.example.project1.Gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.project1.R;

import java.io.File;

public class FullView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        ImageView imageView = findViewById(R.id.img_full);
        String img_id = getIntent().getExtras().getString("img_id");

        File imfile = new File(img_id);
        Glide
                .with(getBaseContext())
                .load(Uri.fromFile(imfile))
                .into(imageView);
        //imageView.setImageResource(img_id);
    }
}

