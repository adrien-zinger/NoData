package com.android.osloh.nodata.ui.utils.servicesSms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;

import java.util.Calendar;

/**
 * Created by Charles on 10/10/2015.
 */

public class UpdaterServiceManager extends Service {

    private final int UPDATE_INTERVAL = 60 * 1000;
    //private Timer timer = new Timer();
    private static final int NOTIFICATION_EX = 1;
    private NotificationManager notificationManager;

    public UpdaterServiceManager() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // code to execute when the service is first created
        super.onCreate();
        Log.e("MyService", "Service Started.");
        showNotification();
    }

    public void showNotification()
    {
        final Calendar cld = Calendar.getInstance();

        //int time = cld.get(Calendar.HOUR_OF_DAY);
        createNotification(null);
        /*if(time>12)
        {
            createNotification(null);

        }
        else
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setMessage("Not yet");
            alert.setTitle("Error");
            alert.setPositiveButton("OK", null);
            alert.create().show();
        }*/
    }

    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Notification Title")
                .setContentText("Click here to read")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();
        int NOTIFICATION_ID = 12345;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(NOTIFICATION_ID, noti);
    }

    @Override
    public void onDestroy() {
        //if (timer != null) {
            //timer.cancel();
        //}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid)
    {
        return START_STICKY;
    }

    private void stopService() {
       // if (timer != null) timer.cancel();
    }

}