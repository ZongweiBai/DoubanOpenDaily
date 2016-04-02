package com.monosky.daily.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;

import java.lang.reflect.Field;

/**
 * Toast工具类
 * Created by Baymin on 2015/10/19.
 */
public class ToastUtils extends Toast {

    public ToastUtils(Context context) {
        super(context);
    }

    // 配置是否显示Toast
    public static boolean isShow = true;
    private static String oldMsg;
    protected static Toast toast = null;
    protected static View toastView;
    protected static TextView textView;
    private static long oneTime = 0;
    private static long twoTime = 0;

    private static void showToast(Context context, String message, int duration) {
        if (toast == null) {
            toast = new Toast(context);
            // 由layout文件创建一个View对象
            toastView = View.inflate(context, R.layout.toast_common, null);
            textView = (TextView) toastView.findViewById(R.id.toastTextView);
            textView.setText(message);

            toast.setView(toastView);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, (int) (0.35 * ScreenUtils.getScreenHeight(context)));
            toast.setDuration(duration);
            try {
                Object mTN = getField(toast, "mTN");
                if (mTN != null) {
                    Object mParams = getField(mTN, "mParams");
                    if (mParams != null
                            && mParams instanceof WindowManager.LayoutParams) {
                        WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                        params.windowAnimations = R.style.toast_anim_style;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > duration) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                textView.setText(message);
                toast.setDuration(duration);
                toast.setView(toastView);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, String message) {
        if (isShow) {
            showToast(BaseApplication.getContext(), message, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow) {
            showToast(BaseApplication.getContext(), BaseApplication.getContext().getResources().getString(message), Toast.LENGTH_SHORT);
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, String message) {
        if (isShow) {
            showToast(BaseApplication.getContext(), message, Toast.LENGTH_LONG);
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow) {
            showToast(BaseApplication.getContext(), BaseApplication.getContext().getResources().getString(message), Toast.LENGTH_LONG);
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, String message, int duration) {
        if (isShow) {
            showToast(BaseApplication.getContext(), message, duration);
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow) {
            showToast(BaseApplication.getContext(), BaseApplication.getContext().getResources().getString(message), duration);
        }
    }
}
