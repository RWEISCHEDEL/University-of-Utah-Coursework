package edu.utah.cs4530.painting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.icu.util.Measure;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Robert on 8/29/2016.
 */
public class KnobView extends View {

    public interface OnAngleChangedListener{
        void onAngleChanged(float theta, String name);
    }

    float _theta = 0.0f;
    RectF _knobRect = new RectF();
    OnAngleChangedListener _angleChangedListener = null;
    String _name = null;

    public KnobView(Context context, String name){
        super(context);
        _name = name;

    }

    public String getName(){
        return _name;
    }

    public float getTheta(){
        return _theta;
    }

    public void setTheta(float theta){
        _theta = theta;
        invalidate();
    }

    public void setOnAngleChangedListener(OnAngleChangedListener angleChangedListener){
        _angleChangedListener = angleChangedListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        PointF touchPoint = new PointF();
        touchPoint.x = event.getX();
        touchPoint.y = event.getY();

        // Set "theta" property.

        float theta = (float) Math.atan2(touchPoint.y -_knobRect.centerY() , touchPoint.x - _knobRect.centerX());
        setTheta(theta);

        _angleChangedListener.onAngleChanged(theta, _name);

        return true; //super.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        _knobRect.left = getPaddingLeft();
        _knobRect.top = getPaddingTop();
        _knobRect.right = getWidth() - getPaddingRight();
        _knobRect.bottom = _knobRect.right - getPaddingBottom();

        //Center knob
        float offset = (getHeight() - _knobRect.height()) * 0.5f;
        _knobRect.top += offset;
        _knobRect.bottom += offset;

        float knobRadius = _knobRect.width() * 0.35f;

        PointF nibCenter = new PointF();
        nibCenter.x = _knobRect.centerX() + (float)Math.cos((double)_theta) * knobRadius;
        nibCenter.y = _knobRect.centerY() + (float)Math.sin((double)_theta) * knobRadius;

        float nibRadius = knobRadius * 0.2f;

        RectF nibRect = new RectF();
        nibRect.left = nibCenter.x - nibRadius;
        nibRect.top = nibCenter.y - nibRadius;
        nibRect.right = nibCenter.x + nibRadius;
        nibRect.bottom = nibCenter.y + nibRadius;

        Paint knobPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        knobPaint.setColor(Color.GRAY);

        Paint nibPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        nibPaint.setColor(Color.WHITE);

        canvas.drawOval(_knobRect, knobPaint);
        canvas.drawOval(nibRect, nibPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

        int width = 400;
        int height = 400;
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSpec;
            height = width;
        }
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSpec;
            width = height;
        }

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }
}
