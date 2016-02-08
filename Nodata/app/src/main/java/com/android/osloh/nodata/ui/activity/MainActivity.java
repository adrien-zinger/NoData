package com.android.osloh.nodata.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.viewNoData.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private int mBack;

    @Bind(R.id.main_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBack = 0;
        loadFragment(FragmentConstants.Goto.HOME, new Bundle());
        ButterKnife.bind(this);
    }

    public void loadFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        Log.d("Noda", "load fragment");
        MainFragment productGalleryFragmentV3 = fragment.getInstance(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_for_main_activity,
                productGalleryFragmentV3, "com.android.osloh.nodata.ui.activity.main.tag");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mBack < 2) {
            mBack++;
            loadFragment(FragmentConstants.Goto.INBOX, new Bundle());
        } else {
            moveTaskToBack(true);
            mBack = 0;
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

