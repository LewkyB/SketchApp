package com.example.jraw_test_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

// TODO: save path and store it as bitmap?

public class PaintView extends View {

    private Path path;
    private Paint mPaint;
    public Canvas canvas;
    public Bitmap mBitmap;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        path = new Path();
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(6f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(mBitmap, 0, 0, null);
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

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
