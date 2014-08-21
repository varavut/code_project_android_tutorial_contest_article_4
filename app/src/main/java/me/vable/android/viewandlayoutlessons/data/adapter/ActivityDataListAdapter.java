package me.vable.android.viewandlayoutlessons.data.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.vable.android.viewandlayoutlessons.R;
import me.vable.android.viewandlayoutlessons.data.ActivityData;

/**
 * Created by Varavut on 8/17/2014.
 * ActivityDataListAdapter is used to provide the ActivityData to the ListView
 */
public class ActivityDataListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ActivityData> mItems;

    public ActivityDataListAdapter(Context context,List<ActivityData> items)
    {
        mContext = context;
        mItems = items;
    }

    public void add(ActivityData activityData)
    {
        mItems.add(activityData);//add the item to the List
        notifyDataSetChanged();//notify to ListView for reload the View data
    }

    public void addAll(List<ActivityData> items)
    {
        mItems.addAll(items);//add all items to the List
        notifyDataSetChanged();
    }

    public void remove(int index)
    {
        mItems.remove(index);//remove the item in the List by index of item
        notifyDataSetChanged();//notify to ListView for reload the View data
    }

    public void remove(ActivityData activityData)
    {
        mItems.remove(activityData);//remove the item in the List
        notifyDataSetChanged();//notify to ListView for reload the View data
    }

    @Override
    public int getCount() {
        return mItems.size();//return number on item in the List
    }

    @Override
    public ActivityData getItem(int i) {
        return mItems.get(i);//return the specific ActivityData item
    }

    @Override
    public long getItemId(int i) {
        return 0;//return item's id but it useless for this ListView, I'm not implement it
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //ListView will use this method to get each View item of the list data
        if(view == null)
        {
            //if view is null, create a new one
            view = LayoutInflater.from(mContext).inflate(R.layout.listitem_activity_data,null);//construct the View from the listitem_activity_data.xml file
        }

        ActivityData activityData = getItem(i);//get the item at position i

        String title = activityData.getTitle();
        Class clazz = activityData.getActivityClass();

        TextView titleTextView = (TextView)view.findViewById(R.id.textview_title);//get the textview_title instance
        TextView classTextView = (TextView)view.findViewById(R.id.textview_class);//get the textview_class instance

        if(title==null)
            titleTextView.setText(mContext.getString(R.string.listitem_textview_default_activity_title));
        else
            titleTextView.setText(title);

        if(clazz==null)
            classTextView.setText(mContext.getString(R.string.listitem_textview_activity_class_name));
        else
            classTextView.setText(clazz.getSimpleName());

        return view;
    }
}
