package xyz.lego.trainee;

import android.os.RemoteException;
import android.util.Log;

public class MyAidlInterfaceImpl extends xyz.lego.trainee.IMyAidlInterface.Stub {
    private static final String TAG = "MyAidlInterfaceImpl";
    @Override
    public void whoami() throws RemoteException {
        Log.i(TAG, "Hey, I'm Remote Service");
    }
}
