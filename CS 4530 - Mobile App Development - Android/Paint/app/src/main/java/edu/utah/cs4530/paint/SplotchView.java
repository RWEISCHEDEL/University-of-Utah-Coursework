package edu.utah.cs4530.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Robert on 9/12/2016.
 */
public class SplotchView extends View {
    int _splotchColor = Color.WHITE;
    boolean _highlighted = false;

    public SplotchView(Context context){
        super(context);
    }

    public int get_splotchColor() {
        return _splotchColor;
    }

    public void set_splotchColor(int splotchColor) {
        _splotchColor = splotchColor;
        invalidate();
    }

    public boolean is_highlighted() {
        return _highlighted;
    }

    public void set_highlighted(boolean highlighted) {
        _highlighted = highlighted;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){

        super.onDraw(canvas);

        RectF splotchRect = new RectF();
        splotchRect.left = getPaddingLeft();
        splotchRect.right = getWidth() - getPaddingRight();
        splotchRect.top = getPaddingTop();
        splotchRect.bottom = getHeight() - getPaddingBottom();

        Paint splotchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        splotchPaint.setColor(_splotchColor);

        canvas.drawOval(splotchRect, splotchPaint);

        if(_highlighted){
            Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // TODO What if ello splotch?
            highlightPaint.setColor(Color.YELLOW);
            highlightPaint.setStyle(Paint.Style.STROKE);
            highlightPaint.setStrokeWidth(splotchRect.height() * 0.1f); // Device Independent Pixels
            canvas.drawOval(splotchRect, highlightPaint);
        }
    }
}
