package com.jiumeng.movieheaven2.global;

import com.jiumeng.movieheaven2.utils.PrefUtils;

/**
 * Created by jiumeng on 2016/10/2.
 */

public class GlobalSetting {
    /**
     * 是否开启无图模式
     * @return
     */
    public static Boolean isNoPicModel(){
        return PrefUtils.getBoolean("isNoPicModel");
    }

    /**
     * 是否双击退出程序
     * @return
     */
    public static Boolean isDoubleExit(){

        return PrefUtils.getBoolean("isDoubleExit");
    }

    /**
     * 是否在启动程序时，检测更新应用
     * @return
     */
    public static Boolean isInspectUpdate(){
        return PrefUtils.getBoolean("isInspectUpdate");
    }
}
