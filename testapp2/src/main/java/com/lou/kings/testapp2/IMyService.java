package com.lou.kings.testapp2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class IMyService extends Service {
    private int mCode;
    public IMyService() {
    }

    public final IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int sub(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public IBinder asBinder() {
            return iMyAidlInterface;
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iMyAidlInterface;
    }
}
