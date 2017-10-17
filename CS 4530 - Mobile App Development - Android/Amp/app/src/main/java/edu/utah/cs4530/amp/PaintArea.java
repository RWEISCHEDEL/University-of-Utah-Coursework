package edu.utah.cs4530.amp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Robert on 9/17/2016.
 */
public class PaintArea extends View {

    Path _linePath = null;
    Paint _currentLineColor = null;
    Paint _linePaint = null;
    ArrayList<PaintLineInfo> _allLines = null;
    int _color;

    public int getLineColor() {
        return _color;
    }

    public void setLineColor(int lineColor) {
        _color = lineColor;
        _currentLineColor.setColor(_color);
    }

    public PaintArea(Context context) {
        super(context);

        _linePath = new Path();
        _currentLineColor = new Paint();
        _currentLineColor.setColor(Color.BLACK);
        _color = Color.BLACK;
        _currentLineColor.setStyle(Paint.Style.STROKE);
        _currentLineColor.setStrokeWidth(10.0f);



        _allLines = new ArrayList<PaintLineInfo>();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(PaintLineInfo l : _allLines){
            canvas.drawPath(l.getPath(), l._color);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                _linePath.lineTo(x, y);
                invalidate();
                _linePath = new Path();
                break;
            case MotionEvent.ACTION_DOWN:
                Paint newLinePaint = new Paint();
                newLinePaint.setColor(_color);
                newLinePaint.setStyle(Paint.Style.STROKE);
                newLinePaint.setStrokeWidth(10.0f);
                _allLines.add(new PaintLineInfo(_linePath, newLinePaint));
                _linePath.moveTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                _linePath.lineTo(x, y);
                invalidate();
                break;
            default:
                return false;
        }

        return true;

    }

    private class PaintLineInfo{
        private Path _path;
        private Paint _color;

        public PaintLineInfo(Path path, Paint color){
            _path = path;
            _color = color;
        }

        public Path getPath(){
            return _path;
        }

        public Paint getColor(){
            return _color;
        }
    }
}
