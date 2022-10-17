package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/4/4.
 */
public class InvalidTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public InvalidTextView(Context context) {
        super(context);
        initDraw();
    }

    public InvalidTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDraw();
    }

    public InvalidTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth((float) 10.0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawLine(0, height / 2, width, height / 2, mPaint);
    }
}
