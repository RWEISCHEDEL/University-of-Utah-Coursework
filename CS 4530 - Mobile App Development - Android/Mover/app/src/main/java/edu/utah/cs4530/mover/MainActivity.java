package edu.utah.cs4530.mover;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ValueAnimator.AnimatorUpdateListener{

    View _greenSquareView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        CenterLayout rootLayout = new CenterLayout(this);
        setContentView(rootLayout);

        _greenSquareView = new View(this);
        _greenSquareView.setBackgroundColor(Color.GREEN);
        rootLayout.addView(_greenSquareView);

        View redSquareView = new View(this);
        redSquareView.setBackgroundColor(Color.RED);
        rootLayout.addView(redSquareView);

        ValueAnimator animation = new ValueAnimator();
        animation.setDuration(5000);
        animation.setFloatValues(0.0f, 2.0f * (float)Math.PI);
        animation.addUpdateListener(this);
        animation.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation){
        //_animationValue = (Float)animation.getAnimatedValue();
        //Log.i("Animation", "" + _animationValue);
        float animationValue = (Float)animation.getAnimatedValue();

        _greenSquareView.getLeft();
        _greenSquareView.getX();
        _greenSquareView.setY(_greenSquareView.getTop() + 400.0f * (float)Math.sin(animationValue));
        _greenSquareView.setX(_greenSquareView.getLeft() + 400.0f * (float)Math.cos(animationValue));
    }
}
