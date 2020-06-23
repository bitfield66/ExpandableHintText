package com.asknone.blur;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.asknone.R;

public class PaintView extends View implements View.OnTouchListener {

    private static final String TAG = "PaintView";
    Bitmap Bitmap1, Bitmap2;
    Bitmap Transparent;
    int X = -100;
    int Y = -100;
    Canvas canvas;
    private boolean isTouched = false;
xoxp-1200956950371-1186005978471-1206960119300-54592ff66c8e6b34535c219d8cae32c5
    xoxb-1200956950371-1224616312928-1m1U77ftl9TFkuJNYca0LNiC
    
    xoxp-1200956950371-1186005978471-1199489742565-cc2e40063e4d15f90830dd71f4c57fae
    Paint paint = new Paint();

    Path drawPath = new Path();

    public PaintView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Transparent = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap1 = new GuassBlur(context).fastBlur(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        Bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        canvas = new Canvas();
        canvas.setBitmap(Transparent);
        canvas.drawBitmap(Bitmap2, 0, 0, paint);

        paint.setAlpha(0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAntiAlias(true);
    }

    private static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        point.x = display.getWidth();
        point.y = display.getHeight();
        return point;
    }

    public Bitmap getImageFromSource() {
        return Transparent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("onDraw");

        if (isTouched) {
            canvas.drawBitmap(Bitmap1, 0, 0, null);

        }
        canvas.drawBitmap(Transparent, 0, 0, null);
    }

    public Bitmap getBitmap() {
        setDrawingCacheEnabled(true);
        final Bitmap bitmap = Bitmap.createBitmap(getDrawingCache());
        setDrawingCacheEnabled(false);
        destroyDrawingCache();
        return bitmap;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        isTouched = true;
        X = (int) event.getX();
        Y = (int) event.getY();

        paint.setStrokeWidth(60);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(X, Y);
                canvas.drawPath(drawPath, paint);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(X, Y);
                canvas.drawPath(drawPath, paint);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(X, Y);
                canvas.drawPath(drawPath, paint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}
