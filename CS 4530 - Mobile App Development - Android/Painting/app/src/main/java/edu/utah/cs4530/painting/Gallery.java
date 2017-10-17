package edu.utah.cs4530.painting;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 9/19/2016.
 */
public class Gallery {

    private static Gallery _Instance = null;

    public static int _currentSelectedColor = Color.BLACK;

    public static Gallery getInstance(){
        // TODO: Thread Safe!!!
        if(_Instance == null){
            _Instance = new Gallery();
        }
        return _Instance;
    }

    List<Drawing> _drawings = new ArrayList<Drawing>();
    int _currentDrawing;
    boolean _movie = false;

    private Gallery(){
        // TODO: construct gallery
    }

    int getDrawingCount(){
        return _drawings.size();
    }

    Drawing getDrawing(int drawingIndex){
        return _drawings.get(drawingIndex);
    }

    void addNewDrawing(){
        _drawings.add(new Drawing());
    }

    void addStrokeToDrawing(int drawingIndex, Stroke stroke){
        _drawings.get(drawingIndex).addStroke(stroke);
    }

    int getCurrentSelectedColor(){
        return _currentSelectedColor;
    }

    void setCurrentSelectedColor(int color){
        _currentSelectedColor = color;
    }

    public List<Drawing> get_drawings() {
        return _drawings;
    }

    public void set_drawings(List<Drawing> drawings) {
        _drawings = drawings;
    }

    public int get_currentDrawing() {
        return _currentDrawing;
    }

    public void set_currentDrawing(int currentDrawing) {
        _currentDrawing = currentDrawing;
    }

    public boolean is_movie() {
        return _movie;
    }

    public void set_movie(boolean movie) {
        _movie = movie;
    }
}
