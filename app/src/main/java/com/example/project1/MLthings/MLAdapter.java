package com.example.project1.MLthings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;

import java.util.List;

public class MLAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<ML_Image_Object> img;
    LayoutInflater inf;

    public MLAdapter(Context context, int layout, List<ML_Image_Object> img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        this.inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public Object getItem(int position) {
        return img.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) convertView = inf.inflate(layout, parent,false);

        ImageView iv =  (ImageView)convertView.findViewById(R.id.ML_ImageView);
        iv.setImageResource(img.get(position).getImResource());


        TextView label = (TextView)convertView.findViewById(R.id.ML_Label_ID);
        label.setText(img.get(position).getLabel());
        //여따 labelling 결과값 출력!!!!
        return convertView;
    }
}
