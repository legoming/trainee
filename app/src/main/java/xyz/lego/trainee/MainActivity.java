package xyz.lego.trainee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button btn_startlocalservice;
    private Button btn_startremoteservice;
    private Button btn_stop_local_service;
    private Button btn_stop_remote_service;
    private Button btn_bind_local_srv;
    private Button btn_call_binded_local;
    private Button btn_unbind_local_srv;
    private Button btn_bind_remote_srv;
    private Button btn_call_bound_remote;
    private Button btn_unbind_remote_srv;

    private LocalService mLocalService = null;

    private ServiceConnection mLocalConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mLocalService = ((LocalService.LocalBinder) iBinder).getService();
            Log.i(TAG, "mLocalConnection.onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mLocalService = null;
            Log.i(TAG, "mLocalConnection.onServiceDisconnected " + componentName);
        }
    };

    private xyz.lego.trainee.IMyAidlInterface mRemoteService = null;

    private ServiceConnection mRemoteConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mRemoteService = xyz.lego.trainee.IMyAidlInterface.Stub.asInterface(iBinder);
            Log.i(TAG, "mRemoteConn.onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "mRemoteConn.onServiceDisconnected " + componentName);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_startlocalservice = findViewById(R.id.btn_startlocalservice);
        btn_startremoteservice = findViewById(R.id.btn_startremoteservice);
        btn_stop_local_service = findViewById(R.id.btn_stoplocalservice);
        btn_stop_remote_service = findViewById(R.id.btn_stopremoteervice);
        btn_bind_local_srv = findViewById(R.id.btn_bindlocalservice);
        btn_call_binded_local = findViewById(R.id.btn_callbindedlocal);
        btn_unbind_local_srv = findViewById(R.id.btn_unbindlocalservice);
        btn_bind_remote_srv = findViewById(R.id.btn_bindremoteservice);
        btn_call_bound_remote = findViewById(R.id.btn_callbindedremote);
        btn_unbind_remote_srv = findViewById(R.id.btn_unbindremoteservice);

        btn_startlocalservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button Start Local Service clicked");
                Intent i = new Intent("trainee.intent.action.LocalService");
                i.setPackage(getPackageName());
                startService(i);
            }
        });
        btn_stop_local_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button Stop Local Service clicked");
                Intent i = new Intent("trainee.intent.action.LocalService");
                i.setPackage(getPackageName());
                stopService(i);
            }
        });

        btn_startremoteservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button Start Remote Service clicked");
                Intent i = new Intent("trainee.intent.action.RemoteService");
                i.setPackage(getPackageName());
                startService(i);
            }
        });
        btn_stop_remote_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button Stop Remote Service clicked");
                Intent i = new Intent("trainee.intent.action.RemoteService");
                i.setPackage(getPackageName());
                stopService(i);
            }
        });

        btn_bind_local_srv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button bind Local Service clicked");
                Intent i = new Intent("trainee.intent.action.LocalService");
                i.setPackage(getPackageName());
                bindService(i, mLocalConnection, Context.BIND_AUTO_CREATE);
            }
        });
        btn_call_binded_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button call bound Local Service clicked");
                if (null != mLocalService) {
                    mLocalService.whoami();
                } else {
                    Log.i(TAG, "mLocalService is null");
                }
            }
        });
        btn_unbind_local_srv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button unbind Local Service clicked");
                if (null != mLocalConnection) {
                    try {
                        unbindService(mLocalConnection);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, "mLocalConnection is null");
                }
            }
        });

        btn_bind_remote_srv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button bind Remote Service clicked");
                Intent i = new Intent("trainee.intent.action.RemoteService");
                i.setPackage(getPackageName());
                bindService(i, mRemoteConn, Context.BIND_AUTO_CREATE);
            }
        });
        btn_call_bound_remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mRemoteService) {
                    try {
                        mRemoteService.whoami();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, "mRemoteService is null");
                }
            }
        });
        btn_unbind_remote_srv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button unbind Remote Service clicked");
                if (null != mRemoteConn) {
                    try {
                        unbindService(mRemoteConn);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, "mRemoteConn is null");
                }
            }
        });
    }
}
