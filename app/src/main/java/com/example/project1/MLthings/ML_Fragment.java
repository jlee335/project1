package com.example.project1.MLthings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.project1.MyApplication;
import com.example.project1.MLthings.Label_Image.LabelAll;

import com.example.project1.R;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

import static com.example.project1.MyApplication.getAppContext;

public class ML_Fragment extends Fragment {

    private MyApplication app;
    private List<ML_Image_Object> img;
    private MLAdapter adapter;
    private GridView gv;
    public MLAdapter getAdapter(){
        return adapter;
    }


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
        //여기서 그냥 thread 를 만들어 관리할까.....

        adapter = new MLAdapter(
                getAppContext(),
                R.layout.mlrow,
                img);


        gv = (GridView)view.findViewById(R.id.gridViewML);
        gv.setAdapter(adapter);

        class getdelegate implements Label_Image.AsyncDelegate{
            @Override
            public void asyncComplete(boolean success) {
                adapter.notifyDataSetChanged();
                gv.invalidateViews();
            }
        }
        getdelegate as = new getdelegate();
        LabelAll labelAll = new LabelAll(as);
        labelAll.execute(img.toArray(new ML_Image_Object[0]));

        //img 리스트들이 있을 것이다.


        return view;
    }
}

