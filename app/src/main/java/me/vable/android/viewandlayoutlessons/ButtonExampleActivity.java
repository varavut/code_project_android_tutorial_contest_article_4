package me.vable.android.viewandlayoutlessons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ButtonExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_example);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Toast.makeText(ButtonExampleActivity.this,"Button was clicked",Toast.LENGTH_SHORT).show();
        }
    };

    public void buttonClick(View view)
    {
        Toast.makeText(ButtonExampleActivity.this,"Invoke buttonClick() method",Toast.LENGTH_SHORT).show();
    }
}
