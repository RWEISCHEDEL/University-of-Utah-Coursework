package edu.utah.cs4530.paint;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        CircleLayout paletteLayout = new CircleLayout(this);
        setContentView(paletteLayout);

        SplotchView redPaintSplotchView = new SplotchView(this);
        redPaintSplotchView.set_splotchColor(Color.RED);
        redPaintSplotchView._highlighted = true;
        paletteLayout.addView(redPaintSplotchView);

        SplotchView greenPaintSplotchView = new SplotchView(this);
        greenPaintSplotchView.set_splotchColor(Color.GREEN);
        paletteLayout.addView(greenPaintSplotchView);

        SplotchView bluePaintSplotchView = new SplotchView(this);
        bluePaintSplotchView.set_splotchColor(Color.BLUE);
        paletteLayout.addView(bluePaintSplotchView);

        SplotchView yellowPaintSplotchView = new SplotchView(this);
        yellowPaintSplotchView.set_splotchColor(Color.YELLOW);
        paletteLayout.addView(yellowPaintSplotchView);

        SplotchView magentaPaintSplotchView = new SplotchView(this);
        magentaPaintSplotchView.set_splotchColor(Color.MAGENTA);
        paletteLayout.addView(magentaPaintSplotchView);

        SplotchView cyanPaintSplotchView = new SplotchView(this);
        cyanPaintSplotchView.set_splotchColor(Color.CYAN);
        paletteLayout.addView(cyanPaintSplotchView);
    }
}
