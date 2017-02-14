package com.lou.kings.testapp2;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;
    private ActionReceiver actionReceiver;
    private IMyService iMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        actionReceiver = new ActionReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.lou.kings.testapp2.showToast");
        registerReceiver(actionReceiver,intentFilter);
    }

    public static void showToast(String str){
        Toast.makeText(mContext, "testapp2"+str, Toast.LENGTH_SHORT).show();
    }

    public static void sum(int a,int b){
        Toast.makeText(mContext, "testapp2"+a+b, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(actionReceiver);
    }
}
