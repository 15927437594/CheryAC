package cn.com.hwtc.cheryac.manager;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;

import java.lang.reflect.Method;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:状态管理模块
 */
public class StatusManager {
    private static final String TAG = StatusManager.class.getSimpleName();
    private static volatile StatusManager sInstance = null;
    private int pm25Ref = 40;
    private int pm25Warning = 0;
    private int pm25AutoCleanFdk = 0;
    private int outsidePm25 = 0;
    private int outsidePm25Valid = 0;
    private int insidePm25 = 0;
    private int insidePm25Valid = 0;
    private int humidSensor = 0;
    private int humidTempError = 0;
    private int carbonDioxideWarning = 0;
    private int carbonDioxideValid = 0;
    private int carbonDioxideLevel = 0;
    private int carbonDioxideAutoMonitorFdk = 0;
    private int autoMistFdk = 0;
    private int autoFragranceFdk = 0;
    private int informMaster = 0;
    private int sendInsidePhoto = 0;
    private int openInsideCamera = 0;
    private int openVenSystem = 0;
    private DriveMode mDriveMode = DriveMode.MODE_WELCOME;

    private StatusManager() {
    }

    public static StatusManager getInstance() {
        if (sInstance == null) {
            synchronized (StatusManager.class) {
                if (sInstance == null) {
                    sInstance = new StatusManager();
                }
            }
        }
        return sInstance;
    }

    public void onBack() {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    Log.d(TAG, "KEYCODE_BACK");
                } catch (Exception e) {
                    Log.e(TAG, "Exception when onBack -> " + e.toString());
                }
            }
        }.start();
    }

    public void onHome(Context context) {
        //方式一模拟home键
//        new Thread() {
//            public void run() {
//                try {
//                    Instrumentation inst = new Instrumentation();
//                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_HOME);
//                    Log.d(TAG, "KEYCODE_HOME");
//                } catch (Exception e) {
//                    Log.e(TAG, "Exception when onHome -> " + e.toString());
//                }
//            }
//        }.start();

        //方式二模拟home键
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(i);
    }

    public void startActivity(Context context, String packageName, String className) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName(packageName, className);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPm25Ref() {
        return pm25Ref;
    }

    public void setPm25Ref(int pm25Ref) {
        this.pm25Ref = pm25Ref;
    }

    public int getPm25Warning() {
        return pm25Warning;
    }

    public void setPm25Warning(int pm25Warning) {
        this.pm25Warning = pm25Warning;
    }

    public int getPm25AutoCleanFdk() {
        return pm25AutoCleanFdk;
    }

    public void setPm25AutoCleanFdk(int pm25AutoCleanFdk) {
        this.pm25AutoCleanFdk = pm25AutoCleanFdk;
    }

    public int getOutsidePm25() {
        return outsidePm25;
    }

    public void setOutsidePm25(int outsidePm25) {
        this.outsidePm25 = outsidePm25;
    }

    public int getOutsidePm25Valid() {
        return outsidePm25Valid;
    }

    public void setOutsidePm25Valid(int outsidePm25Valid) {
        this.outsidePm25Valid = outsidePm25Valid;
    }

    public int getInsidePm25() {
        return insidePm25;
    }

    public void setInsidePm25(int insidePm25) {
        this.insidePm25 = insidePm25;
    }

    public int getInsidePm25Valid() {
        return insidePm25Valid;
    }

    public void setInsidePm25Valid(int insidePm25Valid) {
        this.insidePm25Valid = insidePm25Valid;
    }

    public int getHumidSensor() {
        return humidSensor;
    }

    public void setHumidSensor(int humidSensor) {
        this.humidSensor = humidSensor;
    }

    public int getHumidTempError() {
        return humidTempError;
    }

    public void setHumidTempError(int humidTempError) {
        this.humidTempError = humidTempError;
    }

    public int getCarbonDioxideWarning() {
        return carbonDioxideWarning;
    }

    public void setCarbonDioxideWarning(int carbonDioxideWarning) {
        this.carbonDioxideWarning = carbonDioxideWarning;
    }

    public int getCarbonDioxideValid() {
        return carbonDioxideValid;
    }

    public void setCarbonDioxideValid(int carbonDioxideValid) {
        this.carbonDioxideValid = carbonDioxideValid;
    }

    public int getCarbonDioxideLevel() {
        return carbonDioxideLevel;
    }

    public void setCarbonDioxideLevel(int carbonDioxideLevel) {
        this.carbonDioxideLevel = carbonDioxideLevel;
    }

    public int getCarbonDioxideAutoMonitorFdk() {
        return carbonDioxideAutoMonitorFdk;
    }

    public void setCarbonDioxideAutoMonitorFdk(int carbonDioxideAutoMonitorFdk) {
        this.carbonDioxideAutoMonitorFdk = carbonDioxideAutoMonitorFdk;
    }

    public int getAutoMistFdk() {
        return autoMistFdk;
    }

    public void setAutoMistFdk(int autoMistFdk) {
        this.autoMistFdk = autoMistFdk;
    }

    public int getAutoFragranceFdk() {
        return autoFragranceFdk;
    }

    public void setAutoFragranceFdk(int autoFragranceFdk) {
        this.autoFragranceFdk = autoFragranceFdk;
    }

    public int getInformMaster() {
        return informMaster;
    }

    public void setInformMaster(int informMaster) {
        this.informMaster = informMaster;
    }

    public int getSendInsidePhoto() {
        return sendInsidePhoto;
    }

    public void setSendInsidePhoto(int sendInsidePhoto) {
        this.sendInsidePhoto = sendInsidePhoto;
    }

    public int getOpenInsideCamera() {
        return openInsideCamera;
    }

    public void setOpenInsideCamera(int openInsideCamera) {
        this.openInsideCamera = openInsideCamera;
    }

    public int getOpenVenSystem() {
        return openVenSystem;
    }

    public void setOpenVenSystem(int openVenSystem) {
        this.openVenSystem = openVenSystem;
    }

    private enum DriveMode {
        MODE_WELCOME, MODE_LONG_DISTANCE, MODE_RELAX
    }

    public DriveMode getDriveMode() {
        return mDriveMode;
    }

    public void setDriveMode(DriveMode mode) {
        mDriveMode = mode;
    }

    /**
     * 如果此时下拉框处于显示状态,则隐藏下拉框
     *
     * @param context 上下文
     */
    public void collapsingNotification(Context context) {
        @SuppressLint("WrongConstant") Object service = context.getSystemService("statusbar");
        if (service == null) {
            Log.w(TAG, "collapsingNotification -> Cannot find service object");
            return;
        }

        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = Build.VERSION.SDK_INT;
            Method collapse;
            if (sdkVersion <= 16) {
                collapse = clazz.getMethod("collapse");
            } else {
                collapse = clazz.getMethod("collapsePanels");
            }
            collapse.setAccessible(true);
            collapse.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向MCU发送消息
     *
     * @param context 上下文
     * @param type    消息类型
     * @param bytes   消息数据
     */
    @SuppressLint("PrivateApi")
    public void sendMsg(Context context, byte type, byte[] bytes) {
        @SuppressLint("WrongConstant") Object mcu_object = context.getSystemService("mcuhw");
        if (mcu_object == null) {
            Log.w(TAG, "sendMsg -> Cannot find mcu object");
            return;
        }

        try {
            Class<?> clazz = Class.forName("android.mcuhw.MCUHWManager");
            Method sendMsg = clazz.getDeclaredMethod("sendmsg", byte.class, byte[].class);
            sendMsg.invoke(mcu_object, type, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] intsToBytes(int[] ints) {
        byte[] bytes = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) ints[i];
        }
        return bytes;
    }

}
