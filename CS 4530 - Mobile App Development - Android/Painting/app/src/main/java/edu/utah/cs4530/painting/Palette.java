package edu.utah.cs4530.painting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Robert on 9/17/2016.
 */
public class Palette extends ViewGroup {

    RectF _palette = new RectF();
    int _splotchCount;
    int _width;
    int _height;

    public Palette(Context context){
        super(context);
        setWillNotDraw(false);
        _splotchCount = 5;
    }

    public int get_splotchCount() {
        return _splotchCount;
    }

    public void set_splotchCount(int splotchCount) {
        _splotchCount = splotchCount;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        for(int childIndex = 0; childIndex < getChildCount(); childIndex++){
            float theta = (float)(2.0 * Math.PI) / (float)getChildCount() * (float)childIndex;

            // TODO call measured child to get the size the child would like to be!
            float density = getResources().getDisplayMetrics().density;
            float childWidth = 0.3f * 160.0f * density; // 300/1000 of an inch
            float childHeight = 0.25f * 160.0f * density; // 250/1000 of an inch

            PointF childCenter = new PointF();
            childCenter.x = getWidth() * 0.5f + (getWidth() * 0.5f - childWidth * 0.5f) * (float)Math.cos(theta);
            childCenter.y = getHeight() * 0.5f + (getHeight() * 0.5f - childHeight * 0.5f) * (float)Math.sin(theta);



            Rect childRect = new Rect();
            childRect.left = (int)(childCenter.x - childWidth * 0.5f);
            childRect.right = (int)(childCenter.x + childWidth * 0.5f);
            childRect.top = (int)(childCenter.y - childHeight * 0.5f);
            childRect.bottom = (int)(childCenter.y + childHeight * 0.5f);

            View childView = getChildAt(childIndex);
            childView.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("TAG", "Made it in here. 2");

        _palette.left = getPaddingLeft();
        _palette.right = getWidth() - getPaddingRight();
        _palette.top = getPaddingTop();
        _palette.bottom = getHeight() - getPaddingBottom();

        Paint paletteColor = new Paint();
        paletteColor.setColor(Color.GRAY);

        canvas.drawOval(_palette, paletteColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        _width = w;
        _height = h;

        super.onSizeChanged(w, h, oldw, oldh);
    }
}
