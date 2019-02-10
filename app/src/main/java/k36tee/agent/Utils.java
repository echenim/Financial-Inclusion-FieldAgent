package k36tee.agent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by myron echenim  on 8/8/16.
 */
public class Utils {

    public static void loadPage(FragmentManager fm, Fragment fragment, boolean shouldStack) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.layout_frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private static String VALID_EMAIL_ADDRESS_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    public static boolean isValidEmail(String email) {
        return email.matches(VALID_EMAIL_ADDRESS_REGEX);
    }

    public static void showMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}
