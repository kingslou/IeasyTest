package com.lou.kings.testapp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by YiMuTian on 2017/2/13.
 */

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().contains("com.lou.kings.testapp2.showToast")){
            String sql = intent.getExtras().getString("method");
            int a = intent.getExtras().getInt("add1");
            int b = intent.getExtras().getInt("add2");
            MainActivity.showToast(sql);
//            MainActivity.sum(a,b);
        }else{
            Toast.makeText(context,"没有找到action",Toast.LENGTH_LONG).show();
        }
    }
}
