package edu.utah.cs4530.paint;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.SizeF;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Robert on 9/12/2016.
 */
public class CircleLayout extends ViewGroup {
    public CircleLayout(Context context){
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
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
}
