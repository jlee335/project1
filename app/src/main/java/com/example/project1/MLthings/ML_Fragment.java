package com.example.project1.MLthings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.project1.MyApplication;
import com.example.project1.R;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

import static com.example.project1.MyApplication.getAppContext;

public class ML_Fragment extends Fragment {

    MyApplication app;
    List<ML_Image_Object> img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MyApplication) getAppContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ml_gallery,container,false);

        img = app.getImg();

        //여기 이 List 에서, Label 이 되어있지 않은 친구들을 Label 시켜버리자!!!!
        for(ML_Image_Object iter : img){
            // ML 모델로 인해 Label 이 정해지지 않은 경우!
            if(!iter.isLabelled()){
                int imdir = iter.getImResource();
                FirebaseVisionImage fbimage = Label_Image.load_img(app.getApplicationContext(),imdir);
                String nlabel = Label_Image.LabelImg(fbimage);
                iter.setLabel(nlabel);
                iter.setLabelled(true);
                // 이제, ML 을 돌려 Label 이 만들어졌고, setLabelled 도 True 로 지정했다.
            }
        }


        //img 리스트들이 있을 것이다.

        MLAdapter adapter = new MLAdapter(
                getAppContext(),
                R.layout.mlrow,
                img);

        GridView gv = (GridView)view.findViewById(R.id.gridViewML);
        gv.setAdapter(adapter);

        return view;
    }
}

