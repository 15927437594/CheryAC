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

import java.util.Arrays;

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
                byte[] data = intent.getByteArrayExtra(EXTRA_NAME_PROTOCOL);
                Integer[] dataInt = new Integer[22];
                if (data == null) {
                    Log.w(TAG, "data == null");
                    return;
                }

                Log.d(TAG, "onReceive data -> " + Arrays.toString(data));

                if (data.length < 22) {
                    Log.w(TAG, "data.length < 22, this data is invalid");
                    return;
                }

                for (int i = 0; i < 22; i++) {
                    dataInt[i] = data[i] & 0xff;
                }

                Log.d(TAG, "onReceive dataInt -> " + Arrays.toString(dataInt));
                if (dataInt[0] > 0) {
                    mStatusManager.setPm25Ref(dataInt[0] * 10);
                }
                mStatusManager.setRiseState(dataInt[1]);
                mStatusManager.setPm25Warning(dataInt[2]);
                mStatusManager.setPm25AutoCleanFdk(dataInt[3]);
                mStatusManager.setOutsidePm25(dataInt[4] << 8 | dataInt[5]);
                mStatusManager.setOutsidePm25Valid(dataInt[6]);
                mStatusManager.setInsidePm25(dataInt[7] << 8 | dataInt[8]);
                mStatusManager.setInsidePm25Valid(dataInt[9]);
                mStatusManager.setHumidUpState(dataInt[10] > mStatusManager.getHumidSensor());
                mStatusManager.setHumidSensor(dataInt[10]);
                mStatusManager.setHumidTempError(dataInt[11]);
                mStatusManager.setCarbonDioxideWarning(dataInt[12]);
                mStatusManager.setCarbonDioxideValid(dataInt[13]);
                mStatusManager.setCarbonDioxideLevel(dataInt[14]);
                mStatusManager.setCarbonDioxideAutoMonitorFdk(dataInt[15]);
                mStatusManager.setAutoMistFdk(dataInt[16]);
                mStatusManager.setAutoFragranceFdk(dataInt[17]);
                mStatusManager.setInformMaster(dataInt[18] == 1);
                mStatusManager.setSendInsidePhoto(dataInt[19] == 1);
                mStatusManager.setOpenInsideCamera(dataInt[20] == 1);
                mStatusManager.setOpenVenSystem(dataInt[21] == 1);

                mStatusManager.updateCarbonDioxideLevel();
                mStatusManager.updatePurification();
                mStatusManager.updateMonitorAction();
                mStatusManager.updateHumidity();
            }
        }
    }

}
