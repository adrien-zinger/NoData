package com.android.osloh.nodata.ui.Utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;


/**
 * Created by adrien zinger on 29/09/15.
 * A bunny who can create sms and send sms
 */
public class SmsBunny {

    private static SmsBunny mBunny;
    public static SmsBunny getBunny() {
        if (mBunny == null)
            mBunny = new SmsBunny();
        return mBunny;
    }

    public boolean sendSmsForGroup(Context context) {
        return sendSms(context);
    }

    private boolean sendSms(Context context) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage("0659085314", null, "sms message", null, null);
            smsManager.sendTextMessage("0638800279", null, "sms message", null, null);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", "default content");
            sendIntent.setType("vnd.android-dir/mms-sms");
            context.startActivity(sendIntent);
            if (context instanceof MainActivity) {
                ((MainActivity) context).showSnackbar(context.getResources().getString(R.string.message_send));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
