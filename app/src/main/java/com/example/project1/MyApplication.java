package com.example.project1;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    //내부 저장소를 사용할 일이 많을 것 같아, context 를 global variable로 저장하려 한다!

    private static Context context;
    public void onCreate(){
        //앱 lifecycle (전체 사용 기간) 시작할 때 자동으로 실행되고,
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }
    // context 를 불러와야 할 때 이것을 불러주면 된다!
    public static Context getAppContext(){
        return MyApplication.context;
    }
}
