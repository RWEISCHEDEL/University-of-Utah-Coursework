package edu.utah.cs4530.painting;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Robert on 10/1/2016.
 */

public class ColorControlActivity extends AppCompatActivity implements KnobView.OnAngleChangedListener, Button.OnClickListener, PaintSplotch.OnSplotchChangedListener{

    int _redKnobValue = 0;
    int _greenKnobValue = 0;
    int _blueKnobValue = 0;

    int _currentSplotchColor = 0;

    int _activeSplotchCount = 4;
    PaintSplotch _splotch0 = null;
    PaintSplotch _splotch1 = null;
    PaintSplotch _splotch2 = null;
    PaintSplotch _splotch3 = null;
    PaintSplotch _splotch4 = null;

    ArrayList<PaintSplotch> _splotchList = null;

    Button _plusButton = null;
    Button _minusButton = null;

    KnobView _redKnob = null;
    KnobView _greenKnob = null;
    KnobView _blueKnob = null;

    Palette _palette = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int _currentSplotchColor = Color.BLACK;

        _splotchList = new ArrayList<PaintSplotch>();

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        _palette = new Palette(this);
        _palette.setBackgroundColor(Color.BLACK);

        _splotch0 = new PaintSplotch(this, Color.RED, 0);
        _splotch1 = new PaintSplotch(this, Color.GREEN, 1);
        _splotch2 = new PaintSplotch(this, Color.BLUE, 2);
        _splotch3 = new PaintSplotch(this, Color.YELLOW, 3);
        _splotch4 = new PaintSplotch(this, Color.rgb(148, 0, 211), 4);

        _splotchList.add(_splotch0);
        _splotchList.add(_splotch1);
        _splotchList.add(_splotch2);
        _splotchList.add(_splotch3);
        _splotchList.add(_splotch4);

        _splotch0.setOnSplotchChangedListener(this);
        _splotch1.setOnSplotchChangedListener(this);
        _splotch2.setOnSplotchChangedListener(this);
        _splotch3.setOnSplotchChangedListener(this);
        _splotch4.setOnSplotchChangedListener(this);

        _palette.addView(_splotch0);
        _palette.addView(_splotch1);
        _palette.addView(_splotch2);
        _palette.addView(_splotch3);
        _palette.addView(_splotch4);
        rootLayout.addView(_palette, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4));

        LinearLayout rgbKnobs = new LinearLayout(this);

        _redKnob = new KnobView(this, "red");
        _redKnob.setBackgroundColor(Color.RED);
        _redKnob.setOnAngleChangedListener(this);
        rgbKnobs.addView(_redKnob, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        _greenKnob = new KnobView(this, "green");
        _greenKnob.setBackgroundColor(Color.GREEN);
        _greenKnob.setOnAngleChangedListener(this);
        rgbKnobs.addView(_greenKnob, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        _blueKnob = new KnobView(this, "blue");
        _blueKnob.setBackgroundColor(Color.BLUE);
        _blueKnob.setOnAngleChangedListener(this);
        rgbKnobs.addView(_blueKnob, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        _plusButton = new Button(this);
        _minusButton = new Button(this);
        buttonLayout.addView(_plusButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        buttonLayout.addView(_minusButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        _plusButton.setTag("Plus");
        _minusButton.setTag("Minus");
        _plusButton.setText("+");
        _minusButton.setText("-");
        _plusButton.setOnClickListener(this);
        _minusButton.setOnClickListener(this);

        rgbKnobs.addView(buttonLayout);


        rootLayout.addView(rgbKnobs);

        //Palette palette = new Palette(this);
        //rootLayout.addView(palette, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        // PaintArea paintView = new PaintArea(this);
        //rootLayout.addView(paintView);
        setContentView(rootLayout);
    }

    @Override
    public void onSplotchChanged(int name) {

        for(PaintSplotch splotch : _splotchList){
            if(splotch._name != name){
                splotch.set_highlighted(false);
            }
            else{
                splotch.set_highlighted(true);
                Gallery.getInstance().setCurrentSelectedColor(splotch.get_splotchColor());
                //finish();
            }
        }

    }

    @Override
    public void onAngleChanged(float theta, String name) {
        float volume = theta;
        Log.i("TAG", "Volume changed to: " + Math.abs(volume * 360));

        if(name.equals("red")){
            int value = (int)Math.abs(volume * 360);
            _redKnobValue+=5;

            if(_redKnobValue == 255){
                _redKnobValue = 0;
            }

            _redKnob.setBackgroundColor(Color.rgb(_redKnobValue, 0, 0));

            _currentSplotchColor = Color.rgb(_redKnobValue, _greenKnobValue, _blueKnobValue);

        }
        else if(name.equals("green")){
            Log.i("Green", "GREEN");

            _greenKnobValue+=5;

            if(_greenKnobValue == 255){
                _greenKnobValue = 0;
            }

            _greenKnob.setBackgroundColor(Color.rgb(0, _greenKnobValue, 0));

            _currentSplotchColor = Color.rgb(_redKnobValue, _greenKnobValue, _blueKnobValue);
        }
        else{
            Log.i("BLUE", "BLUE");
            _blueKnobValue+=5;

            if(_blueKnobValue == 255){
                _blueKnobValue = 0;
            }

            _blueKnob.setBackgroundColor(Color.rgb(0, 0, _blueKnobValue));

            _currentSplotchColor = Color.rgb(_redKnobValue, _greenKnobValue, _blueKnobValue);
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getTag().equals("Plus")){

            _activeSplotchCount++;

            PaintSplotch newSplotch = new PaintSplotch(this, _currentSplotchColor, _activeSplotchCount);
            newSplotch.setOnSplotchChangedListener(this);
            _palette.addView(newSplotch);
            _splotchList.add(newSplotch);

            if(_activeSplotchCount >= 2){
                _minusButton.setEnabled(true);
            }

        }
        else{

            PaintSplotch removeSplotch = _splotchList.get(_splotchList.size() - 1);

            if(removeSplotch._highlighted){
                removeSplotch.set_highlighted(false);
            }

            _palette.removeView(removeSplotch);

            _splotchList.remove(_splotchList.size() - 1);

            _activeSplotchCount--;

            if(_activeSplotchCount == 0){
                _minusButton.setEnabled(false);
            }

        }

    }
}
