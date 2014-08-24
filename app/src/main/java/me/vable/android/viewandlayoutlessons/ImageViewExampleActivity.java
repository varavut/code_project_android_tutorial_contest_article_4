package me.vable.android.viewandlayoutlessons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class ImageViewExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_example);
        ImageView imageView = (ImageView)findViewById(R.id.imageview1);
        imageView.setImageResource(R.drawable.ic_error);
    }
}
