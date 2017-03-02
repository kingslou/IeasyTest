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

import com.ieasy360.launcher.IDataManger;
import com.ieasy360.launcher.TransEntity;
import com.ieasy360.launcher.TransListener;
import com.lou.kings.testapp2.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private Button btn1,btn2,btn3;
    private ITrans iTrans;
    private ServiceConnection serviceConnection  = new ServiceConnection() {
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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iDataManger = IDataManger.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "service connected OK",
                    Toast.LENGTH_SHORT).show();
            try{
                iDataManger.registerTransListener(transListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "service connected FALIE",
                    Toast.LENGTH_SHORT).show();
        }
    };
    private IMyAidlInterface iMyAidlInterface;
    private IDataManger iDataManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
//        bindService();
        bindService2();
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

//                if(iMyAidlInterface!=null){
//                    iTrans = new AidlItransImp(iMyAidlInterface);
//                    int result  = iTrans.add(2,3);
//                    Toast.makeText(MainActivity.this,"result"+result,Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(MainActivity.this,"null",Toast.LENGTH_LONG).show();
//                }
                if(connection!=null){
                    try {
//                        iDataManger.callHost("woshiceshi");
                        TransEntity transEntity = new TransEntity();
                        transEntity.setTransType("收款");
                        transEntity.getData().putString("test","ceshishuju");
                        transEntity.getData().putFloat("payMoney",100);
                        transEntity.getData().putString("payType","收款");
                        iDataManger.request(transEntity);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private TransListener transListener = new TransListener.Stub(){
        @Override
        public int transCallBack(final TransEntity transEntity) throws RemoteException {
            if(transEntity!=null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"已经执行完毕"+transEntity.getTransType(),Toast.LENGTH_LONG).show();
                    }
                });

            }else{

            }
            return 0;
        }
    };

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

    void bindService2(){
        Intent intent = new Intent();
        intent.setAction("com.ieasy360.launcher.calculate");
        try {
            bindService(intent, connection, Service.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Log.e("绑定失败",e.toString());
            Toast.makeText(MainActivity.this, "绑定服务失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connection!=null){
            unbindService(connection);
            try{
                iDataManger.unregisterTransListener(transListener);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
