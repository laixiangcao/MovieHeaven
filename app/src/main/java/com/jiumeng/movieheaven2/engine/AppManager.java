//package com.jiumeng.movieheaven2.engine;
//
//import android.app.Activity;
//
//
//import java.util.Stack;
//
///**
// * activity堆栈式管理
// *
// * @author FireAnt（http://my.oschina.net/LittleDY）
// * @created 2014年10月30日 下午6:22:05
// */
//public class AppManager {
//
//    //用来存放activity的集合
//    private static Stack<Activity> activityStack;
//    private static AppManager instance;
//
//    private AppManager() {
//    }
//
//    /**
//     * 单一实例
//     */
//    public static AppManager getAppManager() {
//        if (instance == null) {
//            instance = new AppManager();
//        }
//
//
//        if (activityStack == null) {
//            activityStack = new Stack<>();
//        }
//
//        return instance;
//    }
//
//    /**
//     * 获取指定的Activity
//     */
//    public static Activity getActivity(Class<?> cls) {
//        if (activityStack != null)
//            for (Activity activity : activityStack) {
//                if (activity.getClass().equals(cls)) {
//                    return activity;
//                }
//            }
//        return null;
//    }
//
//
//    /**
//     * 添加Activity到堆栈
//     */
//    public void addActivity(Activity activity) {
//        activityStack.add(activity);
//    }
//
//    /**
//     * 获取当前Activity（堆栈中最后一个压入的）
//     */
//    public Activity currentActivity() {
//        return activityStack.lastElement();
//    }
//
//    /**
//     * 结束当前Activity（堆栈中最后一个压入的）
//     */
//    public void finishActivity() {
//        finishActivity(activityStack.lastElement());
//    }
//
//    /**
//     * 结束指定的Activity
//     */
//    public void finishActivity(Activity activity) {
////        if (activity != null && activityStack.contains(activity)) {
//            activityStack.remove(activity);
//            activity.finish();
////        }
//    }
//
//    /**
//     * 结束指定的Activity
//     */
//    public void removeActivity(Activity activity) {
//        if (activity != null && activityStack.contains(activity)) {
//            activityStack.remove(activity);
//        }
//    }
//
//    /**
//     * 结束指定类名的Activity
//     */
//    public void finishActivity(Class<?> cls) {
//        for (Activity activity : activityStack) {
//            if (activity.getClass().equals(cls)) {
//                finishActivity(activity);
//                break;
//            }
//        }
//    }
//
//    /**
//     * 结束所有Activity
//     */
//    private void finishAllActivity() {
//        for(int i=0;i<activityStack.size();i++){
//            finishActivity();
//        }
//        activityStack.clear();
//        System.out.println("aaaActivity"+activityStack.size());
//    }
//
//    /**
//     * 退出应用程序
//     */
//    public void AppExit() {
//        try {
//            finishAllActivity();
//            // System.exit(0);
//        } catch (Exception e) {
//        }
//    }
//}
