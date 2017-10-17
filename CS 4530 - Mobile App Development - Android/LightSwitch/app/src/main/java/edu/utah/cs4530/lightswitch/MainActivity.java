package edu.utah.cs4530.lightswitch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ImageView _imageViewer = null;
    private LinearLayout _baseLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        _baseLayout = new LinearLayout(this);
        _baseLayout.setOrientation(LinearLayout.VERTICAL);
        _baseLayout.setBackgroundColor(Color.BLACK);
        setContentView(_baseLayout);

        _imageViewer = new ImageView(this);
        _imageViewer.setBackgroundColor(Color.BLACK);
        _imageViewer.setImageResource(R.drawable.offbulb);
        _baseLayout.addView(_imageViewer, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));


        ToggleButton toggle = new ToggleButton(this);
        toggle.setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams lightSwitchLayoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1));
        lightSwitchLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        _baseLayout.addView(toggle, lightSwitchLayoutParams);

        toggle.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
        if(on){
            _imageViewer.setImageResource(R.drawable.onbulb);
            _baseLayout.setBackgroundColor(Color.YELLOW);

        }
        else{
            _imageViewer.setImageResource(R.drawable.offbulb);
            _baseLayout.setBackgroundColor(Color.BLACK);
        }
    }
}
