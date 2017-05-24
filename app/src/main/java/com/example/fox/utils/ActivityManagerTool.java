package com.example.fox.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

public class ActivityManagerTool {

    public static boolean isUseActivityManager = true;

    private final List<Activity> activityList = new LinkedList<Activity>();

    private static ActivityManagerTool manager;

    /*
     * activity exist
     */
    private boolean isExist = false;

    public static Class<?> indexActivity;

    public static List<Class<?>> bottomActivityList = new LinkedList<Class<?>>();

    /**
     * @return
     */
    public static ActivityManagerTool getActivityManager() {
        if (null == manager) {
            manager = new ActivityManagerTool();
        }
        return manager;
    }

    /**
     * @param activity
     * @return
     */
    public boolean add(final Activity activity) {

        int position = 0;

        if (isUseActivityManager) {

            if (isBottomActivity(activity)) {
                for (int i = 0; i < activityList.size() - 1; i++) {

                    if (!isBottomActivity(activityList.get(i))) {
                        popActivity(activityList.get(i));
                        i--;
                    }
                    if (i > 0) {

                        if (activityList.get(i).getClass()
                                .equals(activity.getClass())) {
                            isExist = true;
                            position = i;
                        }
                    }
                }

            }
        }

        if (!activityList.add(activity)) {
            return false;
        }

        if (isExist) {
            isExist = false;
            activityList.remove(position);
        }

        return true;
    }

    /**
     * @param activity
     */
    public void finish(final Activity activity) {
        for (Activity iterable : activityList) {
            if (activity != iterable) {
                iterable.finish();
            }
        }
    }

    /**
     */
    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.out.println("程序退出");
        System.exit(0);
    }

    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 清除所有activity
     */
    public void clearActivityList() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * @param activity
     */
    private void popActivity(final Activity activity) {

        if (activity != null) {
            activity.finish();
            activityList.remove(activity);
        }

    }

    /**
     * @param targetclazz
     * @param sourceActivity
     */
    public void removeTemporaryActivityList(final Class<Activity> targetclazz,
                                          final Activity sourceActivity) {
        if (targetclazz == null || sourceActivity == null) {
            return;
        }

        int begin = -1;
        int end = -1;
        Activity activity;

        for (int i = activityList.size() - 1; i >= 0; i--) {
            activity = activityList.get(i);
            if (activity.getClass() == targetclazz && end == -1) {
                end = i;
            }
            if (sourceActivity == activity && begin == -1) {
                begin = i;
            }
            if (begin != -1 && end != -1) {
                break;
            }
        }

        if (end != -1 && begin > end) {
            for (int i = begin; i > end; i--) {
                activity = activityList.get(i);
                popActivity(activity);
            }
        }
    }

    /**
     * @return
     */
    @SuppressWarnings("unused")
    private Activity currentActivity() {
        return activityList.get(activityList.size() - 1);
    }

    /**
     * @return
     */
    public boolean isBottomActivity(final Activity activity) {

        for (int i = 0; i < bottomActivityList.size(); i++) {
            if (activity.getClass() == bottomActivityList.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     */
    public void backIndex(final Context context) {

        if (activityList.size() <= 0) {
            return;
        }

        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity activity = activityList.get(i);
            if (isBottomActivity(activity)) {
                Intent intent = new Intent();
                intent.setClass(context, indexActivity);
                context.startActivity(intent);
            } else {
                popActivity(activity);
            }
        }
    }

    /**
     * @param clazz
     */
    public <E extends Activity> boolean backToActivity(final Class<E> clazz) {
        boolean flag = false;
        if (activityList.size() <= 0) {
            return flag;
        }

        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity activity = activityList.get(i);
            if (activity.getClass() == clazz) {
                flag = true;
                break;
            }
        }
        if (flag) {
            for (int i = activityList.size() - 1; i >= 0; i--) {
                Activity activity = activityList.get(i);
                if (activity.getClass() != clazz) {
                    popActivity(activity);
                } else {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param activity
     */
    public void removeActivity(final Activity activity) {

        if (activity != null) {
            activityList.remove(activity);
        }
    }

    /**
     * @param activityClass
     */
    public void setBottomActivityList(final Class<?> activityClass) {
        if (activityClass != null) {
            bottomActivityList.add(activityClass);
        }
    }

    public List<Activity> getActivityList() {
        return activityList;
    }
}