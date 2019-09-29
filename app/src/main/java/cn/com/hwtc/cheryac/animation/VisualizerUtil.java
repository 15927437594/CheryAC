package cn.com.hwtc.cheryac.animation;

import java.util.Arrays;
import java.util.List;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.manager.StatusManager;


/**
 *    
 * Description:    Java class description   
 * Created time:   2019/8/23 13:24  
 * Author:         zed  
 * Version:        1.0  
 */
public class VisualizerUtil {

    private List<Integer> vitalSignsBitmaps = Arrays.asList(
            R.drawable.vital_signs_00000, R.drawable.vital_signs_00001, R.drawable.vital_signs_00002, R.drawable.vital_signs_00003, R.drawable.vital_signs_00004,
            R.drawable.vital_signs_00005, R.drawable.vital_signs_00006, R.drawable.vital_signs_00007, R.drawable.vital_signs_00008, R.drawable.vital_signs_00009,
            R.drawable.vital_signs_00010, R.drawable.vital_signs_00011, R.drawable.vital_signs_00012, R.drawable.vital_signs_00013, R.drawable.vital_signs_00014,
            R.drawable.vital_signs_00015, R.drawable.vital_signs_00016, R.drawable.vital_signs_00017, R.drawable.vital_signs_00018, R.drawable.vital_signs_00019,
            R.drawable.vital_signs_00020, R.drawable.vital_signs_00021, R.drawable.vital_signs_00022, R.drawable.vital_signs_00023, R.drawable.vital_signs_00024,
            R.drawable.vital_signs_00025, R.drawable.vital_signs_00026, R.drawable.vital_signs_00027, R.drawable.vital_signs_00028, R.drawable.vital_signs_00029,
            R.drawable.vital_signs_00030, R.drawable.vital_signs_00031, R.drawable.vital_signs_00032, R.drawable.vital_signs_00033, R.drawable.vital_signs_00034,
            R.drawable.vital_signs_00035, R.drawable.vital_signs_00036, R.drawable.vital_signs_00037);

    private List<Integer> autoDefogBitmaps = Arrays.asList(
            R.drawable.auto_defog_00000, R.drawable.auto_defog_00001, R.drawable.auto_defog_00002, R.drawable.auto_defog_00003, R.drawable.auto_defog_00004,
            R.drawable.auto_defog_00005, R.drawable.auto_defog_00006, R.drawable.auto_defog_00007, R.drawable.auto_defog_00008, R.drawable.auto_defog_00009,
            R.drawable.auto_defog_00010, R.drawable.auto_defog_00011, R.drawable.auto_defog_00012, R.drawable.auto_defog_00013, R.drawable.auto_defog_00014,
            R.drawable.auto_defog_00015, R.drawable.auto_defog_00016, R.drawable.auto_defog_00017, R.drawable.auto_defog_00018, R.drawable.auto_defog_00019,
            R.drawable.auto_defog_00020, R.drawable.auto_defog_00021, R.drawable.auto_defog_00022, R.drawable.auto_defog_00023, R.drawable.auto_defog_00024,
            R.drawable.auto_defog_00025, R.drawable.auto_defog_00026, R.drawable.auto_defog_00027, R.drawable.auto_defog_00028, R.drawable.auto_defog_00029,
            R.drawable.auto_defog_00030, R.drawable.auto_defog_00031, R.drawable.auto_defog_00032, R.drawable.auto_defog_00033, R.drawable.auto_defog_00034,
            R.drawable.auto_defog_00035, R.drawable.auto_defog_00036, R.drawable.auto_defog_00037, R.drawable.auto_defog_00038, R.drawable.auto_defog_00039,
            R.drawable.auto_defog_00040, R.drawable.auto_defog_00041, R.drawable.auto_defog_00042, R.drawable.auto_defog_00043, R.drawable.auto_defog_00044,
            R.drawable.auto_defog_00045, R.drawable.auto_defog_00046, R.drawable.auto_defog_00047, R.drawable.auto_defog_00048, R.drawable.auto_defog_00049);

    private List<Integer> airPurificationBitmaps = Arrays.asList(
            R.drawable.air_purification_00000, R.drawable.air_purification_00001, R.drawable.air_purification_00003,
            R.drawable.air_purification_00004, R.drawable.air_purification_00006, R.drawable.air_purification_00007,
            R.drawable.air_purification_00008, R.drawable.air_purification_00009, R.drawable.air_purification_00010, R.drawable.air_purification_00011,
            R.drawable.air_purification_00012, R.drawable.air_purification_00013, R.drawable.air_purification_00014, R.drawable.air_purification_00015,
            R.drawable.air_purification_00016, R.drawable.air_purification_00017, R.drawable.air_purification_00018, R.drawable.air_purification_00019,
            R.drawable.air_purification_00020, R.drawable.air_purification_00021, R.drawable.air_purification_00022, R.drawable.air_purification_00023,
            R.drawable.air_purification_00024, R.drawable.air_purification_00025, R.drawable.air_purification_00026, R.drawable.air_purification_00027,
            R.drawable.air_purification_00028, R.drawable.air_purification_00029, R.drawable.air_purification_00030, R.drawable.air_purification_00031,
            R.drawable.air_purification_00032, R.drawable.air_purification_00033, R.drawable.air_purification_00035,
            R.drawable.air_purification_00036, R.drawable.air_purification_00037, R.drawable.air_purification_00038,
            R.drawable.air_purification_00040, R.drawable.air_purification_00041, R.drawable.air_purification_00043,
            R.drawable.air_purification_00044, R.drawable.air_purification_00045, R.drawable.air_purification_00046, R.drawable.air_purification_00047,
            R.drawable.air_purification_00048, R.drawable.air_purification_00049);

    private static VisualizerUtil instance;

    private VisualizerUtil() {
    }

    public static VisualizerUtil getInstance() {
        if (instance == null) {
            synchronized (VisualizerUtil.class) {
                if (instance == null) {
                    instance = new VisualizerUtil();
                }
            }
        }
        return instance;
    }

    public List<Integer> getBitmapIds() {
        List<Integer> bitmapIds = null;
        if (StatusManager.getInstance().getPanel() == 1) {
            bitmapIds = airPurificationBitmaps;
        } else if (StatusManager.getInstance().getPanel() == 2) {
            bitmapIds = vitalSignsBitmaps;
        } else if (StatusManager.getInstance().getPanel() == 3) {
            bitmapIds = autoDefogBitmaps;
        }

        return bitmapIds;
    }
}
