package com.lou.kings.ieasytest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lou.kings.testapp2.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private Button btn1,btn2;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "service connected OK",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "service connected FALIE",
                    Toast.LENGTH_SHORT).show();
        }
    };
    private IMyAidlInterface iMyAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button)findViewById(R.id.button2);
        bindService();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent("com.lou.kings.testapp2.showToast");
                it.putExtra("method","sql");
                it.putExtra("add1",2);
                it.putExtra("add2",3);
                sendBroadcast(it);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iMyAidlInterface!=null){
                    try{
                        int result = iMyAidlInterface.sub(2,3);
                        Toast.makeText(MainActivity.this,"result"+result,Toast.LENGTH_LONG).show();
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"null",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void bindService(){
        Intent intent = new Intent();
        intent.setAction("com.lou.kings.testapp2.CALCULATOR");
        try {
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Log.e("绑定失败",e.toString());
            Toast.makeText(MainActivity.this, "绑定服务失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
