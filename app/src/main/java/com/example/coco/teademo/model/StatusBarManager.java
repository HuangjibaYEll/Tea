package com.example.coco.teademo.model;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.coco.teademo.R;
import com.example.coco.teademo.utils.MeasureUtils;

/**
 * Created by coco on 2017/8/29.
 * 与actionbar状态有关
 */

public class StatusBarManager {
    private static final String TAG = "StatusBarManager";

    private static void setStatusBarUpperAPI19(Activity act) {
        Window window = act.getWindow();
        //设置悬浮透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //拿到控制布局的总布局 （比自己设的布局还大，最外层的布局）
        ViewGroup mContentView = (ViewGroup) act.findViewById(Window.ID_ANDROID_CONTENT);
        //拿到状态栏高度
        int statusBarHeight = MeasureUtils.getStatusBarHeight(act);
        //拿到一个颜色（指定的颜色，一般来说跟你actionbar颜色一致）
        int statusColor = act.getResources().getColor(R.color.green);
        //开始添加view(跟状态栏高度一样的view)
        View mTopView = mContentView.getChildAt(0);
        if (mTopView != null && mTopView.getLayoutParams() != null &&
                mTopView.getLayoutParams().height == statusBarHeight) {
            mTopView.setBackgroundColor(statusColor);
            return;
        }
        //制造一个和状态栏等尺寸的 View
        mTopView = new View(act);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        mTopView.setBackgroundColor(statusColor);
        //将view添加到第一个位置
        mContentView.addView(mTopView, 0, lp);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarUpper21(Activity act) {
        act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        act.getWindow().setStatusBarColor(Color.parseColor("#00000000"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setTranStatusBar(Activity act) {
        //拿到当前系统的版本号
        int systemVersion = getSystemVersion();
        if (systemVersion >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpper21(act);
        } else if (systemVersion >= Build.VERSION_CODES.KITKAT && systemVersion < Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpperAPI19(act);
        }


        Log.e(TAG, "setTranStatusBar: " + systemVersion);

    }

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }
}
