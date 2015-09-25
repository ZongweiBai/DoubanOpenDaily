package com.monosky.daily.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.monosky.daily.R;

/**
 * Created by jonez_000 on 2015/8/24.
 */
public class WaitingDialog {
    private Dialog mDialog;
    private Context mContext;

    public WaitingDialog(Context context, String waitingInfo) {
        mContext = context;

        mDialog = new Dialog(mContext, R.style.WaitingDialog);
        mDialog.setContentView(R.layout.waiting_dialog);
        TextView showText = (TextView) mDialog.findViewById(R.id.dlg_waiting_tip);
        showText.setText(waitingInfo);
    }


    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            try {
                mDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isShow() {
        return mDialog.isShowing();
    }
}
