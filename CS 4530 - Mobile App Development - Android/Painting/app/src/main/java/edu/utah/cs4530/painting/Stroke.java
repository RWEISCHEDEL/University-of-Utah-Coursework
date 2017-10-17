package edu.utah.cs4530.painting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 9/21/2016.
 */
public class Stroke implements Serializable{

    List<Point> _points;
    int _color;

    public int get_color() {
        return _color;
    }

    public void set_color(int color) {
        _color = color;
    }

    int getPointCount(){
        return _points.size();
    }

    Point getPoint(int pointIndex){
        return _points.get(pointIndex);
    }

    void addPoint(Point p){
        _points.add(p);
    }

    public Stroke(){
        _points = new ArrayList<Point>();
        _color = 0xFF000000;
    }
}
