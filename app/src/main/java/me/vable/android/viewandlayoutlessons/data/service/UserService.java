package me.vable.android.viewandlayoutlessons.data.service;

import android.content.Context;
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
    public UserService getInstance(Context context)
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

    public void Login(String username,String password)
    {
        User tempUser = new User();
        tempUser.setUsername(username);
        tempUser.setPassword(password);
        boolean exist = users.contains(tempUser);
        if(exist)
        {
            int index = users.indexOf(tempUser);
            User user = users.get(index);
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


}
