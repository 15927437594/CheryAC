package cn.com.hwtc.cheryac.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cn.com.hwtc.cheryac.manager.StatusManager;

/**
 * user: Created by jid on 2019/9/3.
 * email: jid@hwtc.com.cn
 * description:
 */
public class CheryControlService extends Service {
    private static final String TAG = CheryControlService.class.getSimpleName();
    public static final String MCUHW_PROTOCOL_3E_CHERY_AC1_ACTION = "android.mcuhw.MCUHW_PROTOCOL_3E_CHERY_AC1_ACTION";
    public static final String MCUHW_PROTOCOL_3F_CHERY_AC2_ACTION = "android.mcuhw.MCUHW_PROTOCOL_3F_CHERY_AC2_ACTION";
    public static final String EXTRA_NAME_PROTOCOL = "protocoldata";
    private Context mContext = null;
    private StatusManager mStatusManager = null;
    private Handler mHandler = null;
    private CheryControlReceiver mCheryControlReceiver = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        mStatusManager = StatusManager.getInstance();
        if (mHandler == null) {
            mHandler = new Handler();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        registerCheryControlReceiver();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterCheryControlReceiver();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerCheryControlReceiver() {
        if (mCheryControlReceiver == null) {
            mCheryControlReceiver = new CheryControlReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(MCUHW_PROTOCOL_3E_CHERY_AC1_ACTION);
            registerReceiver(mCheryControlReceiver, filter);
        }
    }

    private void unregisterCheryControlReceiver() {
        if (mCheryControlReceiver != null) {
            unregisterReceiver(mCheryControlReceiver);
            mCheryControlReceiver = null;
        }
    }

    public class CheryControlReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MCUHW_PROTOCOL_3E_CHERY_AC1_ACTION.equals(action)) {

            }
        }
    }

}
