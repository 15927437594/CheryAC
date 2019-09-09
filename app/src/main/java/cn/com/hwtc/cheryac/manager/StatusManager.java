package cn.com.hwtc.cheryac.manager;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;

import java.lang.reflect.Method;
import java.util.Arrays;

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
    private boolean informMaster = false;
    private boolean sendInsidePhoto = false;
    private boolean openInsideCamera = false;
    private boolean openVenSystem = false;

    private int manualPurificationSwitch = 0; //手动净化开关状态
    private int panel = 0; //画面选择
    private int autoPurificationSwitch = 0; //自动净化开关状态
    private int fragranceType = 0; //香氛类型
    private int fragranceMode = 0; //香氛模式
    private int fragranceConcentration = 0; //香氛浓度
    private int carbonDioxideAutoMonitorSwitch = 0; //CO2自动检测开关状态
    private int autoMistSwitch = 0; //自动除雾开关状态
    private int autoFragranceSwitch = 0; //智能香氛开关状态
    private boolean defogCompleted = false; //是否除雾完成
    private boolean autoControlPurification = false; //香氛自动控制标志

    private OnUpdateAirPurificationCallback mOnUpdateAirPurificationCallback;
    private OnUpdateVitalSignsCallback mOnUpdateVitalSignsCallback;
    private OnUpdateAutoDefogCallback mOnUpdateAutoDefogCallback;

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

    public boolean getInformMaster() {
        return informMaster;
    }

    public void setInformMaster(boolean informMaster) {
        this.informMaster = informMaster;
    }

    public boolean getSendInsidePhoto() {
        return sendInsidePhoto;
    }

    public void setSendInsidePhoto(boolean sendInsidePhoto) {
        this.sendInsidePhoto = sendInsidePhoto;
    }

    public boolean getOpenInsideCamera() {
        return openInsideCamera;
    }

    public void setOpenInsideCamera(boolean openInsideCamera) {
        this.openInsideCamera = openInsideCamera;
    }

    public boolean getOpenVenSystem() {
        return openVenSystem;
    }

    public void setOpenVenSystem(boolean openVenSystem) {
        this.openVenSystem = openVenSystem;
    }

    public int getManualPurificationSwitch() {
        return manualPurificationSwitch;
    }

    public void setManualPurificationSwitch(int manualPurificationSwitch) {
        this.manualPurificationSwitch = manualPurificationSwitch;
    }

    public int getPanel() {
        return panel;
    }

    public void setPanel(int panel) {
        this.panel = panel;
    }

    public int getAutoPurificationSwitch() {
        return autoPurificationSwitch;
    }

    public void setAutoPurificationSwitch(int autoPurificationSwitch) {
        this.autoPurificationSwitch = autoPurificationSwitch;
    }

    public int getFragranceType() {
        return fragranceType;
    }

    public void setFragranceType(int fragranceType) {
        this.fragranceType = fragranceType;
    }

    public int getFragranceMode() {
        return fragranceMode;
    }

    public void setFragranceMode(int fragranceMode) {
        this.fragranceMode = fragranceMode;
    }

    public int getFragranceConcentration() {
        return fragranceConcentration;
    }

    public void setFragranceConcentration(int fragranceConcentration) {
        this.fragranceConcentration = fragranceConcentration;
    }

    public int getCarbonDioxideAutoMonitorSwitch() {
        return carbonDioxideAutoMonitorSwitch;
    }

    public void setCarbonDioxideAutoMonitorSwitch(int carbonDioxideAutoMonitorSwitch) {
        this.carbonDioxideAutoMonitorSwitch = carbonDioxideAutoMonitorSwitch;
    }

    public int getAutoMistSwitch() {
        return autoMistSwitch;
    }

    public void setAutoMistSwitch(int autoMistSwitch) {
        this.autoMistSwitch = autoMistSwitch;
    }

    public int getAutoFragranceSwitch() {
        return autoFragranceSwitch;
    }

    public void setAutoFragranceSwitch(int autoFragranceSwitch) {
        this.autoFragranceSwitch = autoFragranceSwitch;
    }

    public boolean getDefogCompleted() {
        return defogCompleted;
    }

    public void setDefogCompleted(boolean defogCompleted) {
        this.defogCompleted = defogCompleted;
    }

    public boolean getAutoControlPurification() {
        return autoControlPurification;
    }


    public void setAutoControlPurification(boolean autoControlPurification) {
        this.autoControlPurification = autoControlPurification;
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
        Log.d(TAG, "sendMsg -> " + Arrays.toString(bytes));
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

    public void sendInfo(Context context) {
        int manualPurificationSwitch = getManualPurificationSwitch();
        int panel = getPanel();
        int autoPurificationSwitch = getAutoPurificationSwitch();
        int fragranceType = getFragranceType();
        int fragranceMode = getFragranceMode();
        int fragranceConcentration = getFragranceConcentration();
        int carbonDioxideAutoMonitorSwitch = getCarbonDioxideAutoMonitorSwitch();
        int autoMistSwitch = getAutoMistSwitch();
        int autoFragranceSwitch = getAutoFragranceSwitch();
        byte[] bytes = new byte[]{(byte) manualPurificationSwitch, (byte) panel, (byte) autoPurificationSwitch,
                (byte) fragranceType, (byte) fragranceMode, (byte) fragranceConcentration,
                (byte) carbonDioxideAutoMonitorSwitch, (byte) autoMistSwitch, (byte) autoFragranceSwitch};

        sendMsg(context, (byte) 0x3F, bytes);
    }

    public byte[] intsToBytes(int[] ints) {
        byte[] bytes = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) ints[i];
        }
        return bytes;
    }

    private int calculateFogProbability(int humidSensor) {
        int fogProbability;
        if (humidSensor < 45) {
            fogProbability = 0;
        } else if (humidSensor <= 75) {
            fogProbability = (humidSensor - 45) / (75 - 45) * 100;
        } else {
            fogProbability = 100;
        }
        return fogProbability;
    }

    public void updatePurification() {
        if (mOnUpdateAirPurificationCallback != null) {
            int percent = (outsidePm25 - insidePm25) / (outsidePm25 - pm25Ref) * 100;
            mOnUpdateAirPurificationCallback.updatePurificationPercent(percent);
        }
    }

    public void updateCarbonDioxideLevel() {
        if (mOnUpdateVitalSignsCallback != null) {
            mOnUpdateVitalSignsCallback.updateCarbonDioxide(carbonDioxideLevel);
        }
    }

    public void updateMonitorAction() {
        if (mOnUpdateVitalSignsCallback != null) {
            mOnUpdateVitalSignsCallback.updateMonitorAction(informMaster, sendInsidePhoto, openInsideCamera, openVenSystem);
        }
    }

    public void updateHumidity() {
        if (mOnUpdateAutoDefogCallback != null) {
            mOnUpdateAutoDefogCallback.updateCurrentHumidity(humidSensor);
            int fogProbability = calculateFogProbability(humidSensor);
            mOnUpdateAutoDefogCallback.updateFogProbability(fogProbability);
        }
    }

    public interface OnUpdateAirPurificationCallback {
        void updatePurificationPercent(int percent);
    }

    public interface OnUpdateVitalSignsCallback {
        void updateCarbonDioxide(int level);

        void updateMonitorAction(boolean informMaster, boolean sendInsidePhoto, boolean openInsideCamera, boolean openVenSystem);
    }

    public interface OnUpdateAutoDefogCallback {
        void updateCurrentHumidity(int humidity);

        void updateFogProbability(int fogProbability);
    }

    public void setOnUpdateAirPurificationCallback(OnUpdateAirPurificationCallback callback) {
        mOnUpdateAirPurificationCallback = callback;
    }

    public void setOnUpdateVitalSignsCallback(OnUpdateVitalSignsCallback callback) {
        mOnUpdateVitalSignsCallback = callback;
    }

    public void setOnUpdateAutoDefogCallback(OnUpdateAutoDefogCallback callback) {
        mOnUpdateAutoDefogCallback = callback;
    }

}
