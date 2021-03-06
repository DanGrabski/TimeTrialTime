package dgrabski_at_gmail_dot_com.starthousetimer;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.EditText;
import android.app.AlertDialog;


public class MainActivity extends AppCompatActivity {

    // done v0.01: Move timer into new activity (name activity_timer)
    // done v0.01: basic functionality to just jump to Timer activity
    // done v0.01: add entry for countdown time
    // done v0.02: pass countdown time to TimerActivity
    // ToDo: come up with settings
    // done: complete timerSetup.setOnClickListener - do we need additional info?
    // done v0.02: clean up all old buttons and references
    // ToDo: add entry for light pattern selection
    // done v0.03: lock to portrait mode
    // ToDo: create user error for invalid entry on countdown time

    public static final String START_SECONDS = "dgrabski_at_gmail_dot_com.STARTSECONDS";
    public static final int MAX_COUNTDOWN = R.integer.time_max;

    Handler handler;
    Button timerSetup;
    int startSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSetup = findViewById(R.id.buttonSetupTimer);
        handler = new Handler();

        timerSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupTimer(view);   // sends us to TimerActivity
            }
        });

    }

    public void setupTimer(View view) {
        // takes the amount of countdown time, and the countdown type,
        // and generates the activity to start the countdown and timing
        // (doesn't start until the user presses Start on the new
        // activity)

        // starting with just the countdown time
        // done v0.02: make sure we're passing countdown time correctly
        // done v0.02: error checking on input_startSeconds - could be blank, which crashes things
//        Intent intent = new Intent(this, TimerActivity.class); // Display Me... -> new activity name
//        EditText countdownTime = findViewById(R.id.input_startSeconds);        // entry for time on countdown
//        String startTime = countdownTime.getText().toString();
//        try {
//            startSeconds = Integer.parseInt(startTime);
//            // ok so not the best way of handling out of range
//            // ToDo: handle out of range better instead of quietly ignoring
//            startSeconds = (startSeconds < 0) || (startSeconds > MAX_COUNTDOWN) ? 0 : startSeconds;
//        }
//        catch (NumberFormatException e) {
//
//            startSeconds = 0;
//        }
//        finally {
//            intent.putExtra(START_SECONDS, startSeconds);
//        }
//        startActivity(intent);
        EditText countdownTime = findViewById(R.id.input_startSeconds);        // entry for time on countdown
        String startTime = countdownTime.getText().toString();
        try {
            startSeconds = Integer.parseInt(startTime);
            if ((startSeconds < getResources().getInteger(R.integer.time_min)) || (startSeconds > getResources().getInteger(R.integer.time_max))) {
                // create exception
                throw new OutOfRangeException();
            } else {
                Intent intent = new Intent(this, TimerActivity.class); // Display Me... -> new activity name
                intent.putExtra(START_SECONDS, startSeconds);
                startActivity(intent);
            }
        } catch (NumberFormatException | OutOfRangeException e ) {
            TimeEntryPopup _timePopup = new TimeEntryPopup();
            FragmentManager _fm = getFragmentManager();
            _timePopup.show(_fm, "timeEntryPopup");
        }

    }

}

class OutOfRangeException extends Exception {
    public OutOfRangeException() {
        super();
    }
}