package dgrabski_at_gmail_dot_com.starthousetimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Created by Dan on 3/24/2018.
 */

public class TimeEntryPopup extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String _errorString = "";
        Resources _r = getResources();

        // I know this is clunky and not extensible, but it's all I need
        // for right now to get things going
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Time");

        _errorString += _r.getString(R.string.str_error_time_entry);
        _errorString += " ";
        _errorString += _r.getInteger(R.integer.time_min);
        _errorString += " ";
        _errorString += _r.getString(R.string.str_error_time_entry_2);
        _errorString += " ";
        _errorString += _r.getInteger(R.integer.time_max);

        builder.setMessage(_errorString);
        //builder.setMessage(R.string.str_error_time_entry + String.valueOf(getResources().getInteger(R.integer.time_min)) +
        //        R.string.str_error_time_entry_2 + String.valueOf(getResources().getInteger(R.integer.time_max)));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
