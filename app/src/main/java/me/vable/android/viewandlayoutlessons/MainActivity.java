package me.vable.android.viewandlayoutlessons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import me.vable.android.viewandlayoutlessons.data.ActivityData;
import me.vable.android.viewandlayoutlessons.data.adapter.ActivityDataListAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//set the activity_main.xml as screen content


        List<ActivityData> activityDataList = new ArrayList<ActivityData>();//List of the ActivityData instance, put you ActivityData here
        activityDataList.add(new ActivityData("Login",LoginActivity.class));
        activityDataList.add(new ActivityData("Welcome",WelcomeActivity.class));
        activityDataList.add(new ActivityData("Sign up Activity",SignUpActivity.class));
        activityDataList.add(new ActivityData("Profile Activity",ProfileActivity.class));
        activityDataList.add(new ActivityData(getString(R.string.title_activity_sample_inflate_view_from_xml),SampleInflateViewFromXmlActivity.class));//add the ActivityData to the List
        activityDataList.add(new ActivityData(getString(R.string.title_activity_sample_create_view_in_java_code),SampleCreateViewInJavaCodeActivity.class));
        activityDataList.add(new ActivityData("My Fist Activity",MyFirstActivity.class));
        activityDataList.add(new ActivityData("TextView Example",TextViewExampleActivity.class));
        activityDataList.add(new ActivityData("EditText Example",EditTextExampleActivity.class));
        activityDataList.add(new ActivityData("ImageView Example",ImageViewExampleActivity.class));
        activityDataList.add(new ActivityData("Button Example",ButtonExampleActivity.class));
        activityDataList.add(new ActivityData("CheckBox Example",CheckBoxExampleActivity.class));
        activityDataList.add(new ActivityData("LinearLayout  Example",LinearLayoutExampleActivity.class));
        activityDataList.add(new ActivityData("RelativeLayout  Example",RelativeLayoutExampleActivity.class));
        activityDataList.add(new ActivityData("Options Menu  Example",OptionsMenuExampleActivity.class));
        activityDataList.add(new ActivityData("Radio Button  Example",RadioButtonExampleActivity.class));
        activityDataList.add(new ActivityData("Switch  Example",SwitchExampleActivity.class));

        ActivityDataListAdapter activityDataListAdapter = new ActivityDataListAdapter(this,activityDataList);//create the Data Adapter for ListView

        ListView listView = (ListView)findViewById(android.R.id.list);//get the instance of ListView that has an id @android:id/list
        listView.setAdapter(activityDataListAdapter);//set the ListView to consume the data from Adapter
        listView.setOnItemClickListener(onItemClickListener);//set click event action
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ActivityData activityData = (ActivityData)adapterView.getAdapter().getItem(i);//get the selected item from the Adapter
            if(activityData.getActivityClass()!=null)
            {
                try{
                    Intent intent = new Intent(MainActivity.this, activityData.getActivityClass());//create the intent for start the new Activity
                    startActivity(intent);//start the new Activity
                }
                catch(ActivityNotFoundException e)//Activity not found or the class that you provide is not an Activity
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);//Create alert dialog builder
                    builder.setIcon(R.drawable.ic_error);//set dialog icon to drawable resource
                    builder.setTitle(R.string.error_dialog_title);//set dialog title
                    builder.setMessage(
                            String.format(
                                    getString(R.string.error_dialog_activity_not_found_message),
                                    activityData.getActivityClass().getSimpleName()
                            )
                    );//set dialog message
                    builder.setPositiveButton(android.R.string.ok,null);//set positive button title and action
                    builder.show();//show the dialog
                }
            }
        }
    };
}
