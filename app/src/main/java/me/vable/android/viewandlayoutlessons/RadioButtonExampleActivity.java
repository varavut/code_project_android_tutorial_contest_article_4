package me.vable.android.viewandlayoutlessons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;


public class RadioButtonExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button_example);
        RadioGroup feelingRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_feeling);
        feelingRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener= new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(i == R.id.radiobutton_felling_happy)
            {
                Toast.makeText(RadioButtonExampleActivity.this,"You're happy!!",Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.radiobutton_felling_sad)
            {
                Toast.makeText(RadioButtonExampleActivity.this,"You're sad",Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.radiobutton_felling_bored)
            {
                Toast.makeText(RadioButtonExampleActivity.this,"You're bored",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
