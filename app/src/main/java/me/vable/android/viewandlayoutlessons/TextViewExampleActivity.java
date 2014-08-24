package me.vable.android.viewandlayoutlessons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class TextViewExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_example);
        TextView helloWorldTextView = (TextView)findViewById(R.id.textview_hello_world);
        helloWorldTextView.setText("Hello John");
    }
}
