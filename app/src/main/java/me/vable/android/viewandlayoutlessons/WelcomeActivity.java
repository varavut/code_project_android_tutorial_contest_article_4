package me.vable.android.viewandlayoutlessons;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import me.vable.android.viewandlayoutlessons.data.User;
import me.vable.android.viewandlayoutlessons.data.service.UserService;


public class WelcomeActivity extends ActionBarActivity {

    private ImageView profileImageView;
    private TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        profileImageView = (ImageView) findViewById(R.id.imageview_profile);
        greetingTextView = (TextView) findViewById(R.id.textview_greeting);

        User user = UserService.getInstance(this).getCurrentUser();

        if(user==null) {
            finish();
            return;
        }

        greetingTextView.setText(String.format("Hello, %s", user.getUsername()));
        profileImageView.setImageBitmap(UserService.getInstance(this).getProfileImage(user));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_user_list) {
            goToUserListPage();
            return true;
        }else if (id == R.id.menu_profile) {
            goToMenuProfilePage();
            return true;
        }else if (id == R.id.menu_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToUserListPage()
    {
        Intent intent = new Intent(this,UserListActivity.class);
        startActivity(intent);
    }

    private void goToMenuProfilePage()
    {
        Intent intent = new Intent(this,ProfileActivity.class);
        intent.putExtra("user",UserService.getInstance(this).getCurrentUser());
        startActivity(intent);
    }

    private void logout()
    {
        UserService.getInstance(this).logout();
        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remembered",false);
        editor.remove("username");
        editor.remove("password");
        editor.commit();
        finish();
    }
}
