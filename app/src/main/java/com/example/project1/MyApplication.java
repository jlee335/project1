package com.example.project1;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.project1.Contacts.Contact;
import com.example.project1.MLthings.ML_Image_Object;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyApplication extends Application {

    //내부 저장소를 사용할 일이 많을 것 같아, context 를 global variable로 저장하려 한다!
    private static Context context;
    private List<Contact> contacts = new ArrayList<>();
    Gson gson;
    IOcustom iocustom;
    private List<ML_Image_Object> img = new ArrayList<>();
    private static ML_Image_Object tmp = new ML_Image_Object(R.drawable.a,null,false);

    public void onCreate(){
        super.onCreate();

        // 이미지의 List 로 구현을 하겠습니다.

        /*IMAGE DATABASE 로딩!!!!*/

        // 이것은 임시방편
        img.add(tmp);
        img.add(tmp);
        img.add(tmp);
        img.add(tmp);
        img.add(tmp);
        img.add(tmp);
        img.add(tmp);
        img.add(tmp);
        img.add(tmp);

        /*IMAGE DATABASE 로딩 끝*/



        //앱 lifecycle (전체 사용 기간) 시작할 때 자동으로 실행되고,
        MyApplication.context = getApplicationContext();
        iocustom = new IOcustom();
        gson = new Gson();

        //contacts 의 값들을 load 해줘야 한다.
        String json = iocustom.readFromFile(context); //파일 열기
        if(json == null){
            Log.e("login activity","Non-existing DATABASE");
        }else{
            Contact[] array = gson.fromJson(json, Contact[].class); //json 에서 얻어가기
            Collections.addAll(contacts,array);
        }
        //로딩 완료
    }
    public List<Contact> getContacts(){
        return contacts;
    }
    public List<ML_Image_Object> getImg(){return img;}

    public void setImg (List<ML_Image_Object> newImage){
        img = newImage;
    }
    // set일 때 수정, get 일 때 수정안함. Mainactivity 는 앱이랑 꺼지지 않는다.

    public void setContacts(List<Contact> contacts){
        // List Manipulation 은 그냥 activity/fragment 에서 하자.
        // 여기는 I/O 및 덮어쓰기만 하자
        this.contacts = contacts;                       //여기 local variable 도 덮어쓰기
        String json;
        json = gson.toJson(contacts);                   //리스트를 json 으로 만들기
        iocustom.writeToFile(json,getAppContext());     // 그거를 그대로 덮어쓰기
    }
    // context 를 불러와야 할 때 이것을 불러주면 된다!
    public static Context getAppContext(){
        return MyApplication.context;
    }
}
