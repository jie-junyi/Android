package com.example.viewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CustomView mCustomView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomView= (CustomView) this.findViewById(R.id.customview);
        /**
         * 使用属性动画使view滑动
         * view实际位置也跟着动
         */
//        ObjectAnimator.ofFloat(mCustomView,"translationX",0,300).setDuration(1000).start();
        /**
         * 使用View动画使view滑动
         * 只会动视图
         */
//        mCustomView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
        /**
         * 使用Scroll来进行平滑移动
         */
        mCustomView.smoothScrollTo(-400,0);
    }
}