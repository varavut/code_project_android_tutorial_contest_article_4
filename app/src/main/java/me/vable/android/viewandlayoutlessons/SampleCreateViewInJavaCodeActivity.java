package me.vable.android.viewandlayoutlessons;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class SampleCreateViewInJavaCodeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);//Create a button
        button.setText(R.string.title_activity_sample_create_view_in_java_code);//Set the text on the button
        setContentView(button);//Set the button as screen content
    }
}
