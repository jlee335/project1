package com.example.project1.Gallery;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.MainActivity;
import com.example.project1.MyApplication;
import com.example.project1.R;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.project1.MyApplication.getAppContext;

public class Gallery_Fragment extends Fragment {

    MyApplication app;

    ArrayList<Integer> mImageIds = new ArrayList<>(Arrays.asList(
            R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,
            R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist,R.drawable.kaist
    ));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MyApplication) getAppContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_,container,false);

        GridView gridView = (GridView)view.findViewById(R.id.myGrid);
        gridView.setAdapter(new MyAdapter(mImageIds,app));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item_pos = mImageIds.get(position);

                ShowDialogBox(item_pos);

            }
        });

        return view;

    }

    private void ShowDialogBox(final int item_pos)
    {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.custom_dialog);

        //Getting custom dialog views
        TextView Image_name = dialog.findViewById(R.id.txt_Image_name);
        ImageView Image = dialog.findViewById(R.id.img);
        Button btn_Full = dialog.findViewById(R.id.btn_full);
        Button btn_Close = dialog.findViewById(R.id.btn_close);

        String title = getResources().getResourceName(item_pos);

        //extracting name

        int index = title.indexOf("/");
        String name = title.substring(index+1,title.length());
        Image_name.setText(name);

        Image.setImageResource(item_pos);

        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_Full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), FullView.class);
                i.putExtra("img_id",item_pos);
                startActivity(i);
            }
        });

        dialog.show();

    }
}

