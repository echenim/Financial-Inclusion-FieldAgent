package k36tee.agent;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by myron echenim on 8/5/16.
 */
public class ProcessDialog {

    private ProgressDialog progressDialog;

    public ProgressDialog getProgressDialog() {
        return this.progressDialog;
    }

    public void showDialog(Context context, String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage(message);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
