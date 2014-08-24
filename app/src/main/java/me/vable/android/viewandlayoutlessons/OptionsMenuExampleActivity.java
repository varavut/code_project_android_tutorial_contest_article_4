package me.vable.android.viewandlayoutlessons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class OptionsMenuExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu_example);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //do something
            Toast.makeText(this,"Settings menu was clicked",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.menu_about) {
            //do something
            Toast.makeText(this,"About menu was clicked",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_refresh) {
            //do something
            Toast.makeText(this,"Refresh menu was clicked",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
