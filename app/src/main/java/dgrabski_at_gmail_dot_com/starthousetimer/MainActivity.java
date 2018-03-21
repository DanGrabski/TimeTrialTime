package dgrabski_at_gmail_dot_com.starthousetimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Handler;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ToDo: Move timer into new activity (name activity_timer)
    // ToDo: basic functionality to just jump to Timer activity
    // ToDo: add entry for countdown time
    // ToDo: pass countdown time to TimerActivity
    // ToDo: come up with settings
    // ToDo: complete timerSetup.setOnClickListener
    // ToDo: clean up all old buttons and references
    // ToDo: add entry for light pattern selection
    // ToDo: lock to portrait mode

    public static final String START_SECONDS = "dgrabski_at_gmail_dot_com.STARTSECONDS";

    Handler handler;
//    ListView listView;
//    String[] ListElements = new String[] {  };
//    List<String> ListElementsArrayList;
//    ArrayAdapter<String> adapter;

    Button timerSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSetup = findViewById(R.id.buttonSetupTimer);
        handler = new Handler();
//        ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
//        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
//        listView.setAdapter(adapter);

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
        // ToDo: make sure we're passing countdown time correctly
        // ToDo: error checking on input_startSeconds - could be blank, which crashes things
        Intent intent = new Intent(this, TimerActivity.class); // Display Me... -> new activity name
//        EditText editText = findViewById(R.id.input_startSeconds);        // entry for time on countdown
//        String startTime = editText.getText().toString();
//        int startSeconds = Integer.valueOf(startTime);
//        intent.putExtra(START_SECONDS, startSeconds);
        intent.putExtra(START_SECONDS, 10);     // ToDo: THIS IS FOR DEBUG/TEST ONLY
        startActivity(intent);
    }



}