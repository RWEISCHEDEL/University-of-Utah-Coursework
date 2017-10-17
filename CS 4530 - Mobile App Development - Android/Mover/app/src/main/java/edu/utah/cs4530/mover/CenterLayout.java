package edu.utah.cs4530.mover;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Robert on 9/14/2016.
 */
public class CenterLayout extends ViewGroup{

    public CenterLayout(Context context){
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        for(int childIndex = 0; childIndex < getChildCount(); childIndex++){
            View child = getChildAt(childIndex);
            float childWidth = 100.0f;
            float childHeight = 100.0f;
            child.layout((int)(getWidth() * 0.5f - childWidth * 0.5f),
                    (int)(getHeight() * 0.5f - childWidth * 0.5f),
                    (int)(getWidth() * 0.5f + childWidth * 0.5f),
                    (int)(getHeight() * 0.5f + childWidth * 0.5f));
        }
    }
}
