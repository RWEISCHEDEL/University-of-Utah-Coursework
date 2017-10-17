package edu.utah.cs4530.painting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Robert on 9/19/2016.
 */
public class PaintView extends View{

    public interface OnPolyLineStartedListener{
        void onPolyLineStarted();
    }

    public interface OnPolyLineEndedListener{
        void onPolyLineEnded(PointF[] polyLine);
    }

    List<ArrayList<PointF>> _polyLines = new ArrayList<ArrayList<PointF>>();

    OnPolyLineStartedListener _polyLineStartedListener = null;
    OnPolyLineEndedListener _polyLineEndedListener = null;

    int _currentDrawingView;

    public int get_currentDrawingView() {
        return _currentDrawingView;
    }

    public void set_currentDrawingView(int currentDrawingView) {
        _currentDrawingView = currentDrawingView;
    }

    public OnPolyLineStartedListener get_polyLineStartedListener() {
        return _polyLineStartedListener;
    }

    public void set_polyLineStartedListener(OnPolyLineStartedListener polyLineStartedListener) {
        _polyLineStartedListener = polyLineStartedListener;
    }

    public OnPolyLineEndedListener get_polyLineEndedListener() {
        return _polyLineEndedListener;
    }

    public void set_polyLineEndedListener(OnPolyLineEndedListener polyLineEndedListener) {
        _polyLineEndedListener = polyLineEndedListener;
    }

    public PaintView(Context context) {
        super(context);
    }

    public void emptyView(){
        _polyLines.clear();
        invalidate();
    }

    public void addPolyLine(PointF[] polyLine){
        Log.i("Add Polyline", "Add polyline");
        ArrayList<PointF> polyLineArray = new ArrayList<PointF>();
        for(int pointIndex = 0; pointIndex < polyLine.length; pointIndex++){
            polyLineArray.add(polyLine[pointIndex]);
        }
        _polyLines.add(polyLineArray);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("ONTOUCH", "A Touch happened");

        PointF touchPoint = new PointF((float)event.getX(), (float)event.getY());

        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            _polyLines.add(new ArrayList<PointF>());

            if(_polyLineStartedListener != null){
                _polyLineStartedListener.onPolyLineStarted();
            }
        }
        if(event.getActionMasked() == MotionEvent.ACTION_UP){
            if(_polyLineEndedListener != null){
                List<PointF> finishedPolyLine = _polyLines.get(_polyLines.size() - 1);
                PointF[] points = new PointF[finishedPolyLine.size()];
                for(int pointIndex = 0; pointIndex < finishedPolyLine.size(); pointIndex++){
                    points[pointIndex] = finishedPolyLine.get(pointIndex);
                }
                Log.i("OnTouch", "Go into polyline ended listener");
                _polyLineEndedListener.onPolyLineEnded(points);
            }
        }

        List<PointF> points = _polyLines.get(_polyLines.size() - 1);
        points.add(touchPoint);
        invalidate();

        return true;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Log.i("onDraw", "Stoke Count: " + Gallery.getInstance().getDrawing(_currentDrawingView).getStrokeCount());
        //Log.i("onDraw", "PolyLine Count: " + _polyLines.size());

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5.0f);

        for(int polyLineIndex = 0; polyLineIndex < _polyLines.size(); polyLineIndex++) {

            //Log.i("onDRAW", "polyLine index: " + polyLineIndex);
            //Log.i("onDRAW", "polyLine size: " + _polyLines.size());
            if(polyLineIndex >= _polyLines.size() - 1){
                paint.setColor(Gallery.getInstance().getCurrentSelectedColor());
            }
            else{
                paint.setColor(Gallery.getInstance().getDrawing(_currentDrawingView).getStroke(polyLineIndex ).get_color());
            }

            List<PointF> points = _polyLines.get(polyLineIndex);
            for (int pointIndex = 1; pointIndex < points.size(); pointIndex++) {
                PointF prevPoint = points.get(pointIndex - 1);
                PointF point = points.get(pointIndex);
                canvas.drawLine(prevPoint.x, prevPoint.y, point.x, point.y, paint);
            }
        }

    }
}
