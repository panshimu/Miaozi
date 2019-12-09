package com.miaozi.baselibrary.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * created by panshimu
 * on 2019/10/24
 */
public class ActivityUtils {

    public static ActivityUtils mActivityUtils;
    private Stack<Activity> mActivityList;
    private ActivityUtils(){
        mActivityList = new Stack<>();
    }
    public static ActivityUtils getInstance(){
        if(mActivityUtils == null){
            synchronized (ActivityUtils.class){
                if(mActivityUtils == null) {
                    mActivityUtils = new ActivityUtils();
                }
            }
        }
        return mActivityUtils;
    }

    /**
     * 添加
     * @param activity
     */
    public void addActivity(Activity activity){
        mActivityList.add(activity);
    }

    /**
     * 移除
     * @param removeActivity
     */
    public void removeActivity(Activity removeActivity){
        int size = mActivityList.size();
        for ( int i = 0; i < size ; i ++ ){
            Activity activity = mActivityList.get(i);
            if(activity == removeActivity){
                mActivityList.remove(i);
                i--;
                size--;
            }
        }
    }

    /**
     * 传activity
     * @param finishActivity
     */
    public void finishActivity(Activity finishActivity){
        int size = mActivityList.size();
        for ( int i = 0; i < size ; i ++ ){
            Activity activity = mActivityList.get(i);
            if(activity == finishActivity){
                mActivityList.remove(i);
                activity.finish();
                i--;
                size--;
            }
        }
    }

    /**
     * 传class
     * @param activityClass
     */
    public void finishActivity(Class<? extends Activity> activityClass ){
        int size = mActivityList.size();
        for ( int i = 0; i < size ; i ++ ){
            Activity activity = mActivityList.get(i);
            if(activity.getClass().getCanonicalName().equals(activityClass.getCanonicalName())){
                mActivityList.remove(i);
                activity.finish();
                i--;
                size--;
            }
        }
    }

    /**
     * 获取当前的Activity
     * @return
     */
    public Activity getCurrentActivity(){
        return mActivityList.lastElement();
    }
}
