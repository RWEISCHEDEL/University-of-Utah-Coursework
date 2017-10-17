package edu.utah.cs4530.networkbattleship;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Robert on 10/30/2016.
 */

public class GameLayout extends ViewGroup implements View.OnClickListener {

    public interface Clicked {
        public void tileClicked(int row, int column);
    }

    Clicked clicked = null;

    public void setClicked(Clicked clicked) {
        this.clicked = clicked;
    }

    private GameGrid[][] gameGrid;
    private boolean playerTurn;
    private int height;
    private int width;

    public GameLayout(Context context, boolean turn) {
        super(context);

        playerTurn = turn;

        height = getHeight();
        width = getWidth();

        gameGrid = new GameGrid[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameGrid[i][j] = new GameGrid(context);
                gameGrid[i][j].setClickable(true);
                gameGrid[i][j].setOnClickListener(this);
                gameGrid[i][j].setBackgroundColor(Color.BLUE);
                addView(gameGrid[i][j]);
            }
        }
    }

    public void setupTiles(int[][] ships) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (ships[i][j] == 0) {
                    gameGrid[i][j].setBackgroundColor(Color.BLUE);
                    gameGrid[i][j].notHit();
                }
                else if (ships[i][j] == 1) {
                    gameGrid[i][j].setBackgroundColor(Color.BLUE);
                    gameGrid[i][j].hit(0);
                }
                else if (ships[i][j] > 1 && ships[i][j] < 6) {
                    if (playerTurn){
                        gameGrid[i][j].setBackgroundColor(Color.BLACK);
                    }
                    else{
                        gameGrid[i][j].setBackgroundColor(Color.BLUE);
                    }
                    gameGrid[i][j].notHit();
                }
                else {
                    if (playerTurn){
                        gameGrid[i][j].setBackgroundColor(Color.BLACK);
                    }
                    else{
                        gameGrid[i][j].setBackgroundColor(Color.BLUE);
                    }
                    gameGrid[i][j].hit(2);
                }
                gameGrid[i][j].invalidate();
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int hOffset;
        int wOffset;

        if(height > width){
            hOffset = (height - width) / 2;
        }
        else{
            hOffset = 0;
        }

        if(width > height){
            wOffset = (width - height) / 2;
        }
        else{
            wOffset = 0;
        }

        int pad = 3;
        int size = (Math.min(height, width) / 10) - (pad);

        for (int childIndex = 0; childIndex < getChildCount(); childIndex++) {
            int row = childIndex / 10;
            int col = childIndex % 10;

            int childTop = hOffset + (row * size) + (pad * (row + 1));
            int childBottom = childTop + size;
            int childLeft = wOffset + ((col * size) + (pad * (col + 1)));
            int childRight = childLeft + size;

            getChildAt(childIndex).layout(childLeft, childTop, childRight, childBottom);
        }
    }

    @Override
    public void onClick(View view) {
        if (clicked != null) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (gameGrid[i][j] == view){
                        clicked.tileClicked(i, j);
                    }
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        height = h;
        width = w;

        this.requestLayout();
    }
}
