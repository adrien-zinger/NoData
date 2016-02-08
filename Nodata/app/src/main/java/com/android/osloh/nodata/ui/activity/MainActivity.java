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
import com.android.osloh.nodata.ui.utils.SMSReader;
import com.android.osloh.nodata.ui.utils.servicesSms.Sms;
import com.android.osloh.nodata.ui.utils.servicesSms.SmsListener;
import com.android.osloh.nodata.ui.utils.servicesSms.SmsRadar;
import com.android.osloh.nodata.ui.utils.servicesSms.SmsRadarService;
import com.android.osloh.nodata.ui.viewNoData.fragment.MainFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
        //new DownloadDbTask(this, this).execute();
        back = 0;
        //DBAccess.getInstance(this).update();

        //GET ALL SMS
        SMSReader smsReader = new SMSReader();
        smsReader.getAllSms(getContentResolver());
        smsReader.getLastWeak(getContentResolver());
        loadFragment(FragmentConstants.Goto.INBOX, new Bundle());
        ButterKnife.bind(this);
    }

    public void loadFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        Log.d("Noda main", "load fragment");
        MainFragment productGalleryFragmentV3 = fragment.getInstance(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_for_main_activity,
                productGalleryFragmentV3, "com.android.osloh.nodata.ui.activity.main.tag");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (back < 2) {
            back++;
            loadFragment(FragmentConstants.Goto.INBOX, new Bundle());
        } else {
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

    @Override
    public void onStop() {
        super.onStop();
    }
}

