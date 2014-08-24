package me.vable.android.viewandlayoutlessons;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import me.vable.android.viewandlayoutlessons.data.User;
import me.vable.android.viewandlayoutlessons.data.service.UserService;


public class SignUpActivity extends ActionBarActivity {

    private ImageView profileImageView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private CompoundButton newsletterSubscriptionCompoundButton;
    private CompoundButton allowOtherEmailCompoundButton;
    private ProgressDialog progressDialog;
    private Bitmap bitmap;

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(UserService.getInstance(this).getCurrentUser()!=null)
        {
            goToWelcomeActivity();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        profileImageView = (ImageView) findViewById(R.id.imageview_profile);
        usernameEditText = (EditText) findViewById(R.id.edittext_username);
        passwordEditText = (EditText) findViewById(R.id.edittext_password);
        emailEditText = (EditText) findViewById(R.id.edittext_email);
        genderRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_gender);
        maleRadioButton = (RadioButton) findViewById(R.id.radiobutton_male);
        femaleRadioButton = (RadioButton) findViewById(R.id.radiobutton_female);
        newsletterSubscriptionCompoundButton = (CompoundButton) findViewById(R.id.switch_subscription);
        if(newsletterSubscriptionCompoundButton == null)
        {
            newsletterSubscriptionCompoundButton = (CompoundButton) findViewById(R.id.checkbox_subscription);
        }
        allowOtherEmailCompoundButton = (CompoundButton) findViewById(R.id.switch_allow_email);
        if(allowOtherEmailCompoundButton == null) {
            allowOtherEmailCompoundButton = (CompoundButton) findViewById(R.id.checkbox_allow_email);
        }
        maleRadioButton.setChecked(true);

        profileImageView.setOnClickListener(onClickProfileImageViewListener);

    }

    View.OnClickListener onClickProfileImageViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            chooseImage();
        }
    };

    private void chooseImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(data.getData());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=2;
                bitmap = BitmapFactory.decodeStream(stream, null, options);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                double scale = 100.0/height;
                height = (int)(height*scale);
                width = (int)(width*scale);
                bitmap = Bitmap.createScaledBitmap(bitmap, width,height, false);
                stream.close();
                profileImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit) {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();
            User.Gender gender;
            if(genderRadioGroup.getCheckedRadioButtonId() == R.id.radiobutton_male)
            {
                gender = User.Gender.MALE;
            }
            else
            {
                gender = User.Gender.FEMALE;
            }
            boolean newsletterSubscribed = newsletterSubscriptionCompoundButton.isChecked();
            boolean allowedOtherEmail = allowOtherEmailCompoundButton.isChecked();

            register(username,password,email,gender,newsletterSubscribed,allowedOtherEmail);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void register(String username,String password, String email, User.Gender gender, boolean newsletterSubscribed, boolean allowedOtherEmail)
    {
        progressDialog.show();
        UserService.getInstance(SignUpActivity.this).register(username,password,email,gender,newsletterSubscribed,allowedOtherEmail,registerListener,bitmap);
    }

    UserService.RegisterListener registerListener = new UserService.RegisterListener() {
        @Override
        public void onResponce(boolean registered, String message, User user) {
            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
            if(registered)
            {
                goToWelcomeActivity();
            }
        }
    };

    private void goToWelcomeActivity()
    {
        Intent intent = new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
