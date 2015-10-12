package com.android.osloh.nodata.ui.activity;

import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.Utils.Item;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.fragment.MainFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String LANDING_MAIN_TAG = "com.android.osloh.nodata.ui.activity.main.tag";
    private int back;

    @Bind(R.id.main_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //loadFragment(FragmentConstants.Goto.CONVERSATION, new Bundle());
        loadFragment(FragmentConstants.Goto.INBOX, new Bundle());   //testing
        back = 0;
        //startService(new Intent(MainActivity.this, UpdaterServiceManager.class));//Load services to send notifications
    }

    public void loadFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        MainFragment productGalleryFragmentV3 = fragment.getInstance(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_for_main_activity, productGalleryFragmentV3, "com.android.osloh.nodata.ui.activity.main.tag");
        fragmentTransaction.commit();
    }
    public ArrayList<Item> displayBox(String box, boolean isConvers) {
        ArrayList<Item> m_parts = new ArrayList<Item>();
        String[] reqCols = new String[]{"read", "date","address", "body"};
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/" + box), reqCols, null, null, null);
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                Item msgData = new Item();
                boolean exist = false;
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    if (cursor.getColumnName(idx).endsWith("address"))
                    {
                        if(!isConvers)
                            exist = isAuthorExit(cursor.getString(idx), m_parts);
                        if(!exist)
                            msgData.setAdress(cursor.getString(idx));
                    }
                    if (cursor.getColumnName(idx).endsWith("date") && !exist)
                    {
                        if (!isConvers)
                            msgData.setDate(convertDate(cursor.getString(idx)));
                        else
                            msgData.setDate(cursor.getString(idx));
                    }
                    if (cursor.getColumnName(idx).endsWith("body") && !exist)
                        msgData.setContent(cursor.getString(idx));
                }
                if(msgData.getAddress() != null)
                    m_parts.add(msgData);
            } while (cursor.moveToNext());
        }
        return m_parts;
    }
    @Override
    public void onBackPressed() {
        if (back < 2)
        {
            back++;
            loadFragment(FragmentConstants.Goto.INBOX, new Bundle());
        }
        else {
            moveTaskToBack(true);
            back = 0;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return (id == R.id.action_settings) || super.onOptionsItemSelected(item);
    }
    public void showSnackbar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
        // todo on fait ça ou pas pour annuler ? .setAction(R.string.snackbar_action_undo, clickListener)
    }
    public boolean isAuthorExit(String add, ArrayList<Item> in){
        if (!in.isEmpty()&& add != null)
            for (Item it:in)
                if(it.getAddress() != null)
                    if(it.getAddress().endsWith(add))
                        return true;
        return false;
    }
    public String convertDate(String in){
        long date = Long.parseLong(in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH/mm", Locale.FRANCE);
        String dateString = formatter.format(new Date(date));
        return dateString;
    }
}

