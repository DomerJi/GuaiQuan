package com.fd.gq.util;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import java.util.List;
import java.util.Stack;

/**
 * Created by admin on 2016/11/26.
 */
public class StackUtil {

    public static UsageStatsManager sUsageStatsManager;
    /**
     * 5.0版本之后google废弃了getRunningTasks（）方法，意味着我们在5.0以后不能通过该方法获取正在运行的应用程序，
     * google在5.0又提供类新的api，那就是getRunningAppProcesses()，通过ActivityManager的getRunningAppProcesses()方法也可以获取正在运行的应用程序。
     * 但是，在5.1的版本发布后getRunningAppProcesses()已经获取不到正在运行的服务...
     * 原来，Android在5.0版本google提供了一个UsageStatsManager类，通过这个类可以获取到应用程序的运行情况
     * 权限申请<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />
     */
    public static String getLauncherTopApp(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> appTasks = activityManager.getRunningTasks(1);
            if (null != appTasks && !appTasks.isEmpty()) {
                return appTasks.get(0).topActivity.getPackageName();
            }
        } else {
            long endTime = System.currentTimeMillis();
            long beginTime = endTime - 10000;

            if (sUsageStatsManager == null) {
                sUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            }
            String result = "";
            UsageEvents.Event event = new UsageEvents.Event();
            UsageEvents usageEvents = sUsageStatsManager.queryEvents(beginTime, endTime);
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    result = event.getPackageName();
                }
            }
            if (!android.text.TextUtils.isEmpty(result)) {
                return result;
            }
        }
        return "";
    }

    /**
     * 一定要申请用户授权，如果用户不给你授权，那么你还是拿不到的哦~~~
     * @param context
     */
    public static void lauchsUsageStatsManager(Context context){
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 栈底最小
     * @param s
     * @return
     */
    public static Stack<Integer> sortStk(Stack<Integer> s) {
        Stack<Integer> r = new Stack<Integer>();
        while( !s.isEmpty() ) {
            int temp = s.pop();
            // >栈底最小   <栈底最高
            while ( !r.isEmpty() && r.peek() > temp) {
                s.push(r.pop());
            }
            r.push(temp);
        }
        return r;
    }

    /**
     * 不使用peek() 对栈排序
     * @param stk
     * @return
     */
    public static Stack Qsort(Stack<Integer>  stk)
    {
        Stack result = new Stack();

        if (stk.isEmpty()) {
            return result;
        }

        int spliter = stk.pop();

        stk.pop();

        Stack<Integer>  s1 = new Stack(), s2 = new Stack<Integer> ();

        while (!stk.isEmpty()) {
            int e = stk.pop();
            stk.pop();
            if (e < spliter) {
                s1.push(e);
            } else {
                s2.push(e);
            }
        }

        s1 = Qsort(s1);
        s2 = Qsort(s2);

        Stack<Integer> s1Rev = new Stack<Integer>(), s2Rev = new Stack<Integer> ();

        while (!s1.isEmpty()) {
            int e = s1.pop();
            s1.pop();
            s1Rev.push(e);
        }

        while (!s2.isEmpty()) {
            int e = s2.pop();
            s2.pop();
            s2Rev.push(e);
        }

        while (!s1Rev.isEmpty()) {
            int e = s1Rev.pop();
            s1Rev.pop();
            result.push(e);
        }

        result.push(spliter);

        while (!s2Rev.isEmpty()) {
            int e = s2Rev.pop();
            s2Rev.pop();
            result.push(e);
        }

        return result;
    }



}
