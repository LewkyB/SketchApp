package com.example.jraw_test_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;


public class PaintView extends View {

    public static final String TAG = "PaintView";

    private Path path;
    private Paint mPaint;
    public Canvas canvas;
    public Bitmap mBitmap;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        path = new Path();
        mPaint = new Paint();
        paintViewConfig(); // setup Paint

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "PaintView onSizeChanged()");

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                return true;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(pointX, pointY);
                    break;
            default:
                return false;
        }
        postInvalidate();
        return false;
    }

    private void paintViewConfig() {
        Log.d(TAG, "PaintView paintViewConfig()");

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(6f);
    }
}
