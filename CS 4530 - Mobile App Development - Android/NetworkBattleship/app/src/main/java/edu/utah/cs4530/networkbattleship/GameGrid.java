package edu.utah.cs4530.networkbattleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Robert on 10/30/2016.
 */

public class GameGrid extends View {

    private Paint tilePaint;
    private float dim;
    private int width;
    private int height;
    private boolean wasHit;


    public GameGrid(Context context) {
        super(context);

        wasHit = false;

        width = this.getWidth();
        height = this.getHeight();

        tilePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tilePaint.setStyle(Paint.Style.FILL);
        tilePaint.setColor(Color.BLACK);

        if(this.getWidth() <= this.getHeight()){
            dim = this.getWidth();
        }
        else{
            dim = this.getHeight();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = this.getWidth();
        height = this.getHeight();

        if(this.getWidth() <= this.getHeight()){
            dim = this.getWidth();
        }
        else{
            dim = this.getHeight();
        }

        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (wasHit) {
            canvas.drawCircle((float)dim / 2, (float)dim / 2, (float)(dim / 3.0), tilePaint);
        }
    }

    public void hit(int hitCode) {
        wasHit = true;
        if (hitCode == 2){
            tilePaint.setColor(Color.RED);
        }
        else{
            tilePaint.setColor(Color.BLACK);
        }
        this.invalidate();
    }

    public void notHit() {
        wasHit = false;
    }
}
