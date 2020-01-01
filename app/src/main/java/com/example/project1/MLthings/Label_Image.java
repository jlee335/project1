package com.example.project1.MLthings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.project1.MyApplication;
import com.example.project1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.project1.MyApplication.getAppContext;

public class Label_Image {
    // FirebaseVisionImageLabel List 을 정렬하기 위한 comparator 지정
    private static int cmpfloat(float f1, float f2) {
        if (f1 - f2 > 0) {
            return 1;
        } else if (f1 - f2 == 0) {
            return 0;
        } else {
            return -1;
        }
    }
    public static class CustomComparator implements Comparator<FirebaseVisionImageLabel> {
        @Override
        public int compare(FirebaseVisionImageLabel o1, FirebaseVisionImageLabel o2) {
            return cmpfloat(o1.getConfidence(), o2.getConfidence());
        }
    }
    //이미지를 로딩하는 function
    public static FirebaseVisionImage load_img(Context context, String path) {
        FirebaseVisionImage image;
        File imgfile = new File(path);
        //Bitmap bitimg = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
        try {
            image = FirebaseVisionImage.fromFilePath(context, Uri.fromFile(imgfile));
        }catch (IOException e){
            e.printStackTrace();
            image = null;//FirebaseVisionImage.fromBitmap();
        }
        return image;
    }

    public static String LabelImg(FirebaseVisionImage img) {

        final String[] text = new String[1];
        final FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
        final FirebaseVisionImage image = img;
        final String label;
        //이것은 Thread 하나를 만들어, AsyncTask 로 처리한다!

        Task<List<FirebaseVisionImageLabel>> labelTask;
        labelTask = labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> labels) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                text[0] = "Unclassified";
            }
        });


        try {
            Tasks.await(labelTask);
        } catch (ExecutionException e) {
            text[0] = "FAILED CLASSIFICATION";
        } catch (InterruptedException e) {
            text[0] = "INTERRUPTED WHILE CLASSIFICATION";
        }
        List<FirebaseVisionImageLabel> results = labelTask.getResult();

        Collections.sort(results, new CustomComparator());
        if(results.size() == 0){
            return "Unclassified";
        }else{
            text[0] = results.get(0).getText();
            return text[0];
        }

    }

    public interface AsyncDelegate{
        public void asyncComplete(boolean success);
    }

    public static class LabelAll extends AsyncTask<ML_Image_Object, Integer, Void> {
        private AsyncDelegate delegate;
        MyApplication app = (MyApplication)getAppContext();
        Map<String,String> cache = app.getCache();
        Map<String,String> ncache = new HashMap<>();

        // Cache --> HIT --> Ncache workflow
        // This can destroy unused items of Cache :: by Deletion of image or label-change


        public LabelAll(AsyncDelegate delegate){
            this.delegate = delegate;
        }
        @Override
        protected Void doInBackground(ML_Image_Object ... img) {// 여러개가 들어올 것이다...
            for (ML_Image_Object iter : img) {
                // ML 모델로 인해 Label 이 정해지지 않은 경우!
                if (!iter.isLabelled()) {
                    //1차. Cache 로부터 값 찾아가자.
                    String data = cache.get(iter.getImID());
                    if(data == null){
                        String imdir = iter.getPath();
                        FirebaseVisionImage fbimage = Label_Image.load_img(getAppContext(), imdir);
                        String nlabel = Label_Image.LabelImg(fbimage);
                        iter.setLabel(nlabel);
                        iter.setLabelled(true);
                        data = nlabel;
                        ncache.put(iter.getImID(),data); // Add specific key to cache!
                    }else{
                        // Cache Key: id, Value: Label
                        ncache.put(iter.getImID(),data);
                        iter.setLabel(data);
                        iter.setLabelled(true);
                    }
                    // 이제, ML 을 돌려 Label 이 만들어졌고, setLabelled 도 True 로 지정했다.
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer ... progress){
            //return null;
        }
        @Override
        protected void onPostExecute(Void v){
            delegate.asyncComplete(true);
            app.setCache(ncache); // update existing cache after operation!
            //return null;
        }
    }
}
