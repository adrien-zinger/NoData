package com.android.osloh.nodata.ui.viewNoData.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.constant.FragmentConstants;

/**
 * Created by Charles on 20/10/2015.
 *
 */
public class MainDialog extends DialogFragment {

    private OnDismissListener onDismissListener;

    interface OnDismissListener {
        /**
         * Before dismiss
         * @return default true. Undo dismiss if false
         */
        boolean onDismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onDismissListener != null) {
            if (onDismissListener.onDismiss()) {
                super.onDismiss(dialog);
            }
        } else {
            super.onDismiss(dialog);
        }
    }

    public void loadFragment(FragmentConstants.Goto fragment) {
        ((MainActivity) getActivity())
                .loadFragment(fragment, new Bundle());
    }

    public void loadFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        ((MainActivity) getActivity())
                .loadFragment(fragment, bundle);
    }
}
