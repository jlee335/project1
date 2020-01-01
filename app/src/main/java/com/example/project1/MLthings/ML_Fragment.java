package com.example.project1.MLthings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.project1.MyApplication;
import com.example.project1.MLthings.Label_Image.LabelAll;

import com.example.project1.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

import static com.example.project1.MyApplication.getAppContext;

public class ML_Fragment extends Fragment {

    private MyApplication app;
    private List<ML_Image_Object> img;
    private MLAdapter adapter;
    private GridView gv;

    private Set<String> selectionSet;

    public MLAdapter getAdapter(){
        return adapter;
    }

    ChipGroup chipGroup;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MyApplication) getAppContext();
        selectionSet = new HashSet<>();

    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ml_gallery,container,false);
        chipGroup = (ChipGroup)view.findViewById(R.id.labelChips);

        img = app.getImg();

        //여기 이 List 에서, Label 이 되어있지 않은 친구들을 Label 시켜버리자!!!!
        //여기서 그냥 thread 를 만들어 관리할까.....

        adapter = new MLAdapter(
                getAppContext(),
                R.layout.mlrow,
                img);

        gv = (GridView)view.findViewById(R.id.gridViewML);
        gv.setAdapter(adapter);
        //gv.setStretchMode(GridView.NO_STRETCH);

        class getdelegate implements Label_Image.AsyncDelegate{
            @Override
            public void asyncComplete(boolean success) {
                adapter.notifyDataSetChanged();
                gv.invalidateViews();
                loadLabelChips(inflater);
            }
        }
        getdelegate as = new getdelegate();
        LabelAll labelAll = new LabelAll(as);
        labelAll.execute(img.toArray(new ML_Image_Object[0]));

        //Filter Action Start
        loadLabelChips(inflater);
        applyfilter(img,selectionSet,app.getCache());


        return view;
    }

    private void applyfilter(List<ML_Image_Object> imgs, Set<String> selectionSet,Map<String,String> cache){
        // All image paths in cache
        List<ML_Image_Object> filtImg = new ArrayList<>();
        for(ML_Image_Object img : imgs){
            String path = img.getImID();
            String label = cache.get(path);
            if(selectionSet.contains(label)){
                filtImg.add(img);
            }
            //
        }
        //List if filtered images passed through. now we need to apply these filters.
        if(selectionSet.size() != 0){
            MLAdapter adapter2 = new MLAdapter(
                    getAppContext(),
                    R.layout.mlrow,
                    filtImg);
            gv.setAdapter(adapter2);
            gv.invalidateViews();
        }else{
            gv.setAdapter(adapter);
            gv.invalidateViews();
        }
    }


    private void loadLabelChips(LayoutInflater inflater){
        Set<String> labelSet = new HashSet<>();
        Map<String,String> cache = app.getCache();
        for(String key : cache.values()){
            labelSet.add(key);
        }

        chipGroup.removeAllViews();
        for(final String labels : labelSet){
            Chip chip = (Chip)inflater.inflate(R.layout.filterchip,chipGroup,false);
            chip.setText(labels);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        selectionSet.add(labels);
                        Log.e("SELECTION"," :: "+selectionSet);
                        applyfilter(img,selectionSet,app.getCache());
                    }else{
                        selectionSet.remove(labels);
                        Log.e("SELECTION"," :: "+selectionSet);
                        applyfilter(img,selectionSet,app.getCache());
                    }
                }
            });
            chipGroup.addView(chip);
        }
    }
}

