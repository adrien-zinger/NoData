package com.android.osloh.nodata.ui.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.constant.FragmentConstants;

/**
 * Created by Charles on 11/11/2015.
 */
// ASync Task Begin to perform Billing information
public class DownloadDbTask extends AsyncTask<Void, Void, Void>{

    private ProgressDialog Dialog;
    private Context context;
    private  MainActivity mainActivity;
    public DownloadDbTask(Context cont, MainActivity main) {
        context =cont;
        mainActivity = main;
        Dialog = new ProgressDialog(cont);
    }

    @Override
    protected Void doInBackground(Void ... voids) {

        return null;
    }

    protected void onPreExecute(){
            Dialog.setMessage(" please wait while loading............");
            Dialog.show();
    }

    protected void onPostExecute(Void unused){
            Dialog.dismiss();

    }
}