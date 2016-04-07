package com.pili.pldroid.streaming.camera.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.view.WindowManager;
import android.widget.Toast;

import com.pili.pldroid.streaming.camera.demo.global.BaseApplication;


public class ScreenUtils {

    /******************************************dip转px, px转dp*****************************/
    // dp = px / 设备密度
    public static int dip2px(float dip) {
        float density = BaseApplication.getInstance().getResources().getDisplayMetrics().density;// 设备密度
        int px = (int) (dip * density + 0.5f);// 3.1->3, 3.9+0.5->4.4->4
        return px;
    }

    public static float px2dip(int px) {
        float density = BaseApplication.getInstance().getResources().getDisplayMetrics().density;// 设备密度
        return px / density;
    }

    /**
     * 在屏幕中央显示一个Toast
     * @param text
     */
    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /** 获取屏幕宽 */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    /** 获取屏幕高 */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        return screenHeight;
    }

    public static int getStatusBarHeidht(Activity context){
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // 状态栏高度
        return rect.top;
    }

    /**
     * 打印Cursor里面所有的记录
     * @param cursor
     */
    public static void printCursor(Cursor cursor) {
        if (cursor == null) {
            return;
        }

        L.i( "共有" + cursor.getCount() + "条记录");
        while (cursor.moveToNext()) {
            L.i( "---------------");
            // 遍历所有的列
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                String value = cursor.getString(i);
                L.i( columnName + " = " + value);
            }
        }
    }

}









