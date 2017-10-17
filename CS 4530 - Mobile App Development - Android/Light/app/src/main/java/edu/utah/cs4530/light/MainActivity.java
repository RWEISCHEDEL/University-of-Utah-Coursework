package edu.utah.cs4530.light;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private ImageView _lightImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.BLUE);
        setContentView(rootLayout);

        _lightImageView = new ImageView(this);
        _lightImageView.setBackgroundColor(Color.RED);
        _lightImageView.setImageResource(R.drawable.off);
        rootLayout.addView(_lightImageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));

        Switch lightSwitch = new Switch(this);
        lightSwitch.setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams lightSwitchLayoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1));
        lightSwitchLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        rootLayout.addView(lightSwitch, lightSwitchLayoutParams);

        lightSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.i("TAG", "Switch moved!");
        if(b){
            _lightImageView.setImageResource(R.drawable.on);
        }
        else{
            _lightImageView.setImageResource(R.drawable.off);
        }

    }
}
