package edu.utah.cs4530.painting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 9/21/2016.
 */
public class Drawing implements Serializable{
    List<Stroke> _strokes;

    int getStrokeCount() {
        return _strokes.size();
    }

    Stroke getStroke(int strokeIndex){
        return _strokes.get(strokeIndex);
    }

    void addStroke(Stroke stroke){
        _strokes.add(stroke);
    }

    public Drawing(){
        _strokes = new ArrayList<Stroke>();
    }
}
