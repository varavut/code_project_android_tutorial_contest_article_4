package me.vable.android.viewandlayoutlessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.vable.android.viewandlayoutlessons.data.User;
import me.vable.android.viewandlayoutlessons.data.service.UserService;


public class LoginActivity extends ActionBarActivity {

    private EditText usernameEditText;
    private EditText passwordEduitText;
    private CheckBox rememberMeCheckBox;
    private TextView forgotPasswordTextView;
    private Button loginButton;
    private Button signUpButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(UserService.getInstance(this).getCurrentUser()!=null)
        {
            goToWelcomeActivity();
            return;
        }

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);

        usernameEditText = (EditText) findViewById(R.id.edittext_username);
        passwordEduitText = (EditText) findViewById(R.id.edittext_password);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.checkbox_remember_me);
        forgotPasswordTextView =  (TextView) findViewById(R.id.textview_forgot_password);
        loginButton =  (Button) findViewById(R.id.button_login);
        signUpButton =  (Button) findViewById(R.id.button_sign_up);

        loginButton.setOnClickListener(onClickLoginButtonListener);
        signUpButton.setOnClickListener(onClickSignUpButtonListener);
        forgotPasswordTextView.setOnClickListener(onClickForgotPasswordTextViewListener);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        boolean remembered = sharedPreferences.getBoolean("remembered",false);
        if(remembered)
        {
            rememberMeCheckBox.setChecked(true);
            String username = sharedPreferences.getString("username", null);
            String password = sharedPreferences.getString("password", null);
            login(username,password);
        }
    }

    private void login(String username,String password)
    {
        progressDialog.show();
        UserService.getInstance(LoginActivity.this).login(username, password, loginListener);
    }

    private void goToSignupActivity()
    {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    private void goToWelcomeActivity()
    {
        Intent intent = new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    View.OnClickListener onClickLoginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String username = usernameEditText.getText().toString();
            String password = passwordEduitText.getText().toString();

            login(username, password);
        }
    };

    View.OnClickListener onClickSignUpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToSignupActivity();
        }
    };

    View.OnClickListener onClickForgotPasswordTextViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Waraing");
            builder.setIcon(R.drawable.ic_error);
            builder.setMessage("Not implement");
            builder.setPositiveButton("OK",null);
            builder.show();
        }
    };

    UserService.LoginListener loginListener = new UserService.LoginListener() {
        @Override
        public void onResponce(boolean loggedin, String message, User user) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
            if(loggedin)
            {
                SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(rememberMeCheckBox.isChecked())
                {
                    editor.putBoolean("remembered", true);
                    editor.putString("username", user.getUsername());
                    editor.putString("password", user.getPassword());
                }
                else
                {
                    editor.putBoolean("remembered",false);
                    editor.remove("username");
                    editor.remove("password");
                }
                editor.commit();
                goToWelcomeActivity();
            }
        }
    };
}
