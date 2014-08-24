package me.vable.android.viewandlayoutlessons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.vable.android.viewandlayoutlessons.data.User;
import me.vable.android.viewandlayoutlessons.data.adapter.UserListAdapter;
import me.vable.android.viewandlayoutlessons.data.service.UserService;


public class UserListActivity extends ActionBarActivity {

    List<User> users = new ArrayList<User>();
    UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        adapter = new UserListAdapter(this, users);

        UserService.getInstance(this).getUserList(getUserListListener);

        ListView userListView = (ListView) findViewById(android.R.id.list);
        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(onItemClickListener);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(UserListActivity.this,ProfileActivity.class);
            intent.putExtra("user",adapter.getItem(i));
            startActivity(intent);
        }
    };

    UserService.GetUserListListener getUserListListener = new UserService.GetUserListListener() {
        @Override
        public void onResponce(boolean success, String message, List<User> userList) {
            if(success)
            {
                users.clear();
                adapter.notifyDataSetChanged();
                users.addAll(userList);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
