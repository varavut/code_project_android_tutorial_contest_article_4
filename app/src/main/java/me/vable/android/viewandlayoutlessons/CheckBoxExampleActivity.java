package me.vable.android.viewandlayoutlessons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;


public class CheckBoxExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_example);

        CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.checkbox_remember_me);
        CheckBox iHaveAnEmailCheckBox = (CheckBox) findViewById(R.id.checkbox_i_have_an_email);

        rememberMeCheckBox.isChecked();
        rememberMeCheckBox.setOnCheckedChangeListener(onRememberMeCheckBoxCheckedChange);

        iHaveAnEmailCheckBox.setOnCheckedChangeListener(iHaveAnEmailCheckBoxCheckedChange);

    }

    CompoundButton.OnCheckedChangeListener onRememberMeCheckBoxCheckedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            if(checked)
            {
                Toast.makeText(CheckBoxExampleActivity.this, "rememberMeCheckBox was checked", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(CheckBoxExampleActivity.this, "rememberMeCheckBox was unchecked", Toast.LENGTH_SHORT).show();
            }
        }
    };

    CompoundButton.OnCheckedChangeListener iHaveAnEmailCheckBoxCheckedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            if(checked)
            {
                findViewById(R.id.edittext_email).setEnabled(true);
            }
            else
            {
                findViewById(R.id.edittext_email).setEnabled(false);
            }
        }
    };
}
