package com.lou.kings.ieasytest;

import android.os.RemoteException;

import com.lou.kings.testapp2.IMyAidlInterface;

/**
 * Created by YiMuTian on 2017/2/17.
 */

public class AidlItransImp extends BaseBoardCast {
    private IMyAidlInterface iMyAidlInterface;
    public AidlItransImp(IMyAidlInterface iMyAidlInterface){
        this.iMyAidlInterface = iMyAidlInterface;
    }
    @Override
    public int add(int a, int b) {
        try{
           return iMyAidlInterface.sub(a,b);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return 0;
    }
}
