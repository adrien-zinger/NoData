package com.android.osloh.nodata.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.fragment.MainFragment;
import com.android.osloh.nodata.ui.services.UpdaterServiceManager;

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
}
