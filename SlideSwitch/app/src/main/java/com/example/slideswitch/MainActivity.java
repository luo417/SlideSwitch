package com.example.slideswitch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.slideswitch.view.SlideSwitch;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlideSwitch slideSwitch = (SlideSwitch) findViewById(R.id.slide_switch);

        slideSwitch.setSwitchBackgroundResource(R.drawable.switch_bg);
        slideSwitch.setSwitchSlidingResource(R.drawable.switch_slide);
        slideSwitch.setSwitchState(SlideSwitch.SwitchState.Open);

        slideSwitch.setOnSwitchStateChangeListener(new SlideSwitch.OnSWitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(SlideSwitch.SwitchState switchState) {
                Toast.makeText(MainActivity.this, switchState+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
