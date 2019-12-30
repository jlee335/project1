package com.example.project1.MLthings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Label_Image {

    // FirebaseVisionImageLabel List 을 정렬하기 위한 comparator 지정
    private static int cmpfloat(float f1,float f2){
        if(f1 - f2 > 0){return 1;}
        else if(f1 - f2 == 0){return 0;}
        else{return -1;}
    }

    public static class CustomComparator implements Comparator<FirebaseVisionImageLabel>{
        @Override
        public int compare(FirebaseVisionImageLabel o1, FirebaseVisionImageLabel o2){
            return cmpfloat(o1.getConfidence(),o2.getConfidence());
        }
    }
    //이미지를 로딩하는 function
    public static FirebaseVisionImage load_img(Context context, int img){
        FirebaseVisionImage image;
        Bitmap bitimg = BitmapFactory.decodeResource(context.getResources(),img);
        image = FirebaseVisionImage.fromBitmap(bitimg);
        /*
        try {
            image = FirebaseVisionImage.fromFilePath(context, uri); // img 에 대해 돌리자
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        */

        return image;
    }

    //FirebaseVisionImage 를 Label 하는 function
    public static String LabelImg(FirebaseVisionImage img){

        //이 부분은 Asynchronous Task 를 하나하나씩 처리함.
        //나중에는, 여러 이미지를 한꺼번에 처리할 수 있는 Thread들을 만들어 관리하자...

        final String[] text = new String[1];
        final FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
        final FirebaseVisionImage image = img;
        final String label;
        //무식하게 Thread 를 만들어야 했다...
        Thread labelThread = new Thread(new Runnable(){
            public void run(){

                Task<List<FirebaseVisionImageLabel>> labelTask;
                labelTask = labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        Collections.sort(labels,new CustomComparator());
                        text[0] = labels.get(0).getText();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        text[0] = "Unclassified";
                    }
                });
                try {
                    Tasks.await(labelTask);
                }catch(ExecutionException e){
                    text[0]="FAILED CLASSIFICATION";
                }catch(InterruptedException e){
                    text[0]= "INTERRUPTED WHILE CLASIFICATION";
                }
                List<FirebaseVisionImageLabel> results = labelTask.getResult();
                Collections.sort(results,new CustomComparator());
                text[0] = results.get(0).getText();
            }
        });
        labelThread.start();
        try {
            labelThread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    return text[0];

    }
}
