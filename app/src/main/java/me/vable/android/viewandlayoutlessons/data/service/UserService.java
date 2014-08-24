package me.vable.android.viewandlayoutlessons.data.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import me.vable.android.viewandlayoutlessons.data.User;

/**
 * Created by Varavut on 8/21/2014.
 */
public class UserService {

    private static String FILE_NAME = "users";

    private static UserService instance;
    public static UserService getInstance(Context context)
    {
        if(instance==null)
            instance = new UserService(context);
        return  instance;
    }

    private Context context;
    private User currentUser;
    private List<User> users;

    public UserService(Context context)
    {
        this.context = context;
        deserializeUsers();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void login(String username,String password,LoginListener listener)
    {
        if(username ==null || password == null || username.trim().isEmpty() || password.trim().isEmpty()){
            if(listener!=null)
                listener.onResponce(false,"Please enter username and password",null);
        }
        else {
            new LoginTask().execute(new Object[]{username, password, listener});
        }
    }

    public void logout()
    {
        setCurrentUser(null);
    }

    public void getUserList(GetUserListListener getUserListListener)
    {
        if(getUserListListener!=null)
            getUserListListener.onResponce(true,"",users);
    }

    public void register(String username,String password,String email,User.Gender gender,boolean newsletterSubscribed,
                         boolean allowedOtherEmail,RegisterListener listener,Bitmap bitmap)
    {
        if(username ==null || password == null || email == null || username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()){
            if(listener!=null)
                listener.onResponce(false,"Please enter username, password, and email",null);
        }
        else {
            new RegisterTask().execute(new Object[]{username, password, email, gender, newsletterSubscribed, allowedOtherEmail, bitmap, listener});
        }
    }

    private void deserializeUsers()
    {
        File file = context.getFileStreamPath(FILE_NAME);

        if(file.exists()) {
            try
            {
                FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                users = (List<User>)objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                users = new ArrayList<User>();
            }
        }
        else
        {
            users = new ArrayList<User>();
        }
    }

    private void serializeUser()
    {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(users);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {

        }
    }

    public Bitmap getProfileImage(User user)
    {
        Bitmap bitmap =null;
        FileInputStream fin = null;
        try{
            fin = new FileInputStream(context.getFileStreamPath(user.getProfileImage()));
            bitmap = BitmapFactory.decodeStream(fin);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void saveBitmapTofile(Bitmap bitmap,String name)
    {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(context.getFileStreamPath(name));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoginTask extends AsyncTask<Object,Void,User>
    {
        LoginListener listener;
        @Override
        protected User doInBackground(Object... objects) {
            String username = String.valueOf(objects[0]).trim().toLowerCase();
            String password = String.valueOf(objects[1]).trim().toLowerCase();

            listener = (LoginListener)objects[2];

            User tempUser = new User();
            tempUser.setUsername(username);
            tempUser.setPassword(password);
            boolean exist = users.contains(tempUser);
            if(exist)
            {
                int index = users.indexOf(tempUser);
                User user = users.get(index);
                UserService.this.setCurrentUser(user);
                return user;
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            boolean loggedin = (user==null)?false:true;
            String message = loggedin?"Logged In":"Invalid Username or Password";
            if(listener!=null)
                listener.onResponce(loggedin,message,user);
        }
    }

    private class RegisterTask extends AsyncTask<Object,Void,User>
    {
        RegisterListener listener;
        @Override
        protected User doInBackground(Object... objects) {
            String username = String.valueOf(objects[0]).trim().toLowerCase();
            String password = String.valueOf(objects[1]).trim().toLowerCase();
            String email = String.valueOf(objects[2]).trim().toLowerCase();
            User.Gender gender = (User.Gender)objects[3];
            boolean newsletterSubscribed = Boolean.parseBoolean(objects[4].toString());
            boolean allowedOtherEmail = Boolean.parseBoolean(objects[5].toString());
            Bitmap bitmap = (Bitmap) objects[6];
            listener = (RegisterListener)objects[7];

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            boolean exist = users.contains(user);
            if(!exist)
            {
                user.setEmail(email);
                user.setGender(gender);
                user.setNewsletterSubscribed(newsletterSubscribed);
                user.setAllowedOtherEmail(allowedOtherEmail);
                user.setProfileImage(username+".png");
                saveBitmapTofile(bitmap, user.getProfileImage());
                users.add(user);
                serializeUser();
                UserService.this.setCurrentUser(user);
                return user;
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            boolean registered = (user==null)?false:true;
            String message = registered?"Registered":"Username is unavailable";
            if(listener!=null)
                listener.onResponce(registered,message,user);
        }
    }

    public interface LoginListener
    {
        public void onResponce(boolean loggedin,String message, User user);
    }

    public interface RegisterListener
    {
        public void onResponce(boolean registered, String message, User user);
    }

    public interface GetUserListListener
    {
        public void onResponce(boolean success, String message, List<User> users);
    }
}
