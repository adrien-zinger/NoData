package com.android.osloh.nodata.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.viewNoData.fragment.MainFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.main_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(FragmentConstants.Goto.HOME, new Bundle());
        ButterKnife.bind(this);
        initDrawerMenu();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initDrawerMenu() {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName(R.string.drawer_item_settings);
        new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return true;
                    }
                })
                .build();
    }

    public void addFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        Log.d("Noda", "load fragment");
        MainFragment instance = fragment.getInstance(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_for_main_activity,
                instance, "com.android.osloh.nodata.ui.activity.main.tag");
        fragmentTransaction.addToBackStack(fragment.getId()).commit();
    }

    public void replaceFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        MainFragment instance = fragment.getInstance(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_for_main_activity,
                instance, "com.android.osloh.nodata.ui.activity.main.tag");
        fragmentTransaction.addToBackStack(fragment.getId()).commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
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

