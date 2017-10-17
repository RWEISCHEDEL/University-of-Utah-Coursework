package edu.utah.cs4530.painting;

import java.io.Serializable;

/**
 * Created by Robert on 9/26/2016.
 */

public class Point implements Serializable{
    public float x;
    public float y;

    public Point(float px, float py) {
        x = px;
        y = py;
    }
}
