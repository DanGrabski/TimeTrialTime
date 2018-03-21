package dgrabski_at_gmail_dot_com.starthousetimer;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerActivity extends AppCompatActivity {

    // ToDo: Add basic stopwatch functionality (moved over from MainActivity)
    // ToDo: Configure output for stopwatch/lap display - create text outputs
    // ToDo: Configure input for stopwatch/lap display - create buttons
    // ToDo: clean up positioning of controls to fit different sizes
    // ToDo: lock to portrait mode
    // ToDo: Add countdown timer before starting stopwatch
    // ToDo: Add visual countdown (display of time remaining)
    // ToDo: round (up) countdown time remaining to nearest second
    // ToDo: Add red light/green light to countdown
    // ToDo: Add beeps to countdown timer
    // ToDo: add different light patterns

    int startTime;
    int tmr_ms, tmr_sec, tmr_min;
    //long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    long time_ms, time_start, time_buff, time_update = 0L;  // time_buff: buffer time for pause

    List<String> lapArrayList;
    String[] lapElements = new String[] {};
    ArrayAdapter<String> adapter;

    ListView lapView;
    TextView countdownView, timeView;
    Button start, stop, reset, lap;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        countdownView = findViewById(R.id.txt_countdown);
        timeView = findViewById(R.id.txt_time);
        lapView = findViewById(R.id.list_laps);

        start = findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);
        reset = findViewById(R.id.btn_reset);
        lap = findViewById(R.id.btn_lap);

        handler = new Handler();

        lapArrayList = new ArrayList<>(Arrays.asList(lapElements));
        adapter = new ArrayAdapter<>(TimerActivity.this, android.R.layout.simple_list_item_1, lapArrayList);
        lapView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_start = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });

    }




    public Runnable runnable = new Runnable() {

        public void run() {

            time_ms = SystemClock.uptimeMillis() - time_start;
            time_update = time_buff + time_ms;
            tmr_sec = (int) (time_update / 1000);
            tmr_min = tmr_sec / 60;
            tmr_min = tmr_sec % 60;
            tmr_ms = (int) (time_update % 1000);
            timeView.setText("" + tmr_min + ":"
                    + String.format("%02d", tmr_sec) + ":"
                    + String.format("%03d", tmr_ms));
            handler.postDelayed(this, 0);
        }

    };
}
