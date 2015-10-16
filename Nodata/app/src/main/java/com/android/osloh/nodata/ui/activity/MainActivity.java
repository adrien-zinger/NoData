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
import com.android.osloh.nodata.ui.nodataUtils.Item;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.fragment.MainFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String LANDING_MAIN_TAG = "com.android.osloh.nodata.ui.activity.main.tag";
    private int back;
    private final SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd/HH/mm",
            Locale.FRANCE);

    @Bind(R.id.main_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadFragment(FragmentConstants.Goto.INBOX, new Bundle());   //testing
        back = 0;
        //startService(new Intent(MainActivity.this, UpdaterServiceManager.class));
        // Load services to send notifications
    }

    public void loadFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        MainFragment productGalleryFragmentV3 = fragment.getInstance(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_for_main_activity,
                productGalleryFragmentV3, "com.android.osloh.nodata.ui.activity.main.tag");
        fragmentTransaction.commit();
    }

    public List<Item> getGalleryContent(String box) {
        Set<String> addressList = new HashSet<>();
        List<Item> items = new ArrayList<>();
        String[] reqCols = new String[]{"address", "read", "date", "body"};
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/" + box),
                reqCols, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); cursor.moveToNext();) {
                if (!addressList.contains(cursor.getString(0))) {
                    Item msgData = new Item(cursor.getString(0), cursor.getString(2),
                            cursor.getString(3));
                    addressList.add(cursor.getString(0));
                    items.add(msgData);
                }
            }
            cursor.close();
        }
        return items;
    }

    public List<Item> getConversContent(String box) {
        List<Item> items = new ArrayList<>();
        String[] reqCols = new String[]{"address", "read", "date", "body"};
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/" + box),
                reqCols, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); cursor.moveToNext();) {
                Item msgData = new Item(cursor.getString(0), cursor.getString(2),
                        box.endsWith("inbox")?cursor.getString(3):"You : "+cursor.getString(3));
                items.add(msgData);
            }
            cursor.close();
        }
        return items;
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
        // todo on fait ça ou pas pour annuler ?
        // .setAction(R.string.snackbar_action_undo, clickListener)
    }

    public String convertDate(String in){
        long date = Long.parseLong(in);
        String dateString = mFormatter.format(new Date(date));
        return dateString;
    }
}

