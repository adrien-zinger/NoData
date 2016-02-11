package com.android.osloh.nodata.ui.activity;

import com.android.osloh.nodata.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.osloh.nodata.ui.constant.FragmentConstants;

/**
 * Splash screen
 * Created by adrien zinger
 */
public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.putExtra("fragment", FragmentConstants.Goto.HOME);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}