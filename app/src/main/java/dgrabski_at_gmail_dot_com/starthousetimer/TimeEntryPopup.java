package dgrabski_at_gmail_dot_com.starthousetimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import dgrabski_at_gmail_dot_com.starthousetimer.R;

/**
 * Created by Dan on 3/24/2018.
 */

public class TimeEntryPopup extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // I know this is clunky and not extensible, but it's all I need
        // for right now to get things going
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Time");
        builder.setMessage(R.string.str_error_time_entry + String.valueOf(R.integer.time_min) +
                R.string.str_error_time_entry_2 + String.valueOf(R.integer.time_max));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
