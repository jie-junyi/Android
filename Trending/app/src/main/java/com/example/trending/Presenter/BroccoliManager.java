package com.example.trending.Presenter;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.google.android.material.drawable.DrawableUtils;

import java.util.HashMap;
import java.util.Map;

import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;

/**
 * 占位符管理类
 */
public class BroccoliManager {
    private static Broccoli mBroccoli;
    // 给Recycler的item用的。
    private static Map<View, Broccoli> mViewPlaceholderManager = new HashMap<>();

    /**
     * 静态的占位符
     *
     * @param context 上下文 activity
     * @param views   R.id.控件的id
     */
    public static void initStatic(Activity context, int... views) {
        if (mBroccoli == null) {
            mBroccoli = new Broccoli();
        }
        for (int view : views) {
            mBroccoli.addPlaceholders(context, view);
        }
    }

//    /**
//     * 动态占位符
//     *
//     * @param views 已绑定好的view
//     */
//    public static void initAction(View... views) {
//        mBroccoli = new Broccoli();
//        for (View view : views) {
//            mBroccoli.addPlaceholders(PlaceholderHelper.getParameter(view));
//        }
//
//    }

    /**
     * RecyclerView的动态占位符
     *
     * @param itemView recyclerView整项内容的View
     * @param views    里面各小项内容的view
     */
    public static void initRecyclerView(View itemView, View... views) {
        Broccoli broccoli = mViewPlaceholderManager.get(itemView);
        if (broccoli == null) {
            broccoli = new Broccoli();
            mViewPlaceholderManager.put(itemView, broccoli);
        }
        for (View view : views) {
            broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                    .setView(view)
                    .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                            Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                    .build());
        }
        broccoli.show();
    }

    /**
     * 根据大项itemView来清除占位符
     *
     * @param itemView
     */
    public static void byItemViewClear(View itemView) {
        if (itemView != null) {
            Broccoli broccoli = mViewPlaceholderManager.get(itemView);
            broccoli.removeAllPlaceholders();
        }
    }

    /**
     * 当用recyclerView时在onDestroy时将mViewPlaceholderManager清空
     * 防止使用BroccoliGradientDrawable时内存泄露
     */
    public static void recyclerViewOnDestroy() {
        //Prevent memory leaks when using BroccoliGradientDrawable
        //防止使用BroccoliGradientDrawable时内存泄露
        for (Broccoli broccoli : mViewPlaceholderManager.values()) {
            broccoli.removeAllPlaceholders();
        }

        mViewPlaceholderManager.clear();
    }

    /**
     * 清除静态占位符
     */
    public static void staticClear() {
        if (mBroccoli != null) {
            mBroccoli.removeAllPlaceholders();
        }
    }

    /**
     * 圆形占位符
     *
     * @param// views 可以多个圆形view 进行处理
     */
//    public static void staticSquare(View... views) {
//        int placeholderColor = Color.parseColor("#DDDDDD");
//        if (mBroccoli == null) {
//            mBroccoli = new Broccoli();
//        }
//        for (View view : views) {
//            mBroccoli.addPlaceholders(
//                    new PlaceholderParameter.Builder()
//                            .setView(view)
//                            .setDrawable(DrawableUtils.createRectangleDrawable(placeholderColor, 0))
//                            .build());
//        }
//
//    }

    public static void show() {
        mBroccoli.show();
    }

//    /**
//     * 圆形占位符
//     *
//     * @param views 可以多个圆形view 进行处理
//     */
//    public static void staticCircular(View... views) {
//        int placeholderColor = Color.parseColor("#DDDDDD");
//        if (mBroccoli == null) {
//            mBroccoli = new Broccoli();
//        }
//        for (View view : views) {
//            mBroccoli.addPlaceholders(
//                    new PlaceholderParameter.Builder()
//                            .setView(view)
//                            .setDrawable(DrawableUtils.createOvalDrawable(placeholderColor))
//                            .build());
//        }
//    }

}

