package cn.com.hwtc.cheryac.manager;

import android.annotation.SuppressLint;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * user: Created by jid on 2019/9/2
 * email: jid@hwtc.com.cn
 * description:系统属性工具
 */
public class SystemPropertiesUtil {

    @SuppressLint("PrivateApi")
    public static String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, "unknown"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    @SuppressLint("PrivateApi")
    public static void setProperty(String key, String value) {
        Log.d("setProperty", "value -> " + value);
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
