package dgrabski_at_gmail_dot_com.starthousetimer;

import android.content.Intent;
import android.os.CountDownTimer;
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
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    // done v0.01: Add basic stopwatch functionality (moved over from MainActivity)
    // done v0.01: Configure output for stopwatch/lap display - create text outputs
    // done v0.01: Configure input for stopwatch/lap display - create buttons
    // done v0.01: clean up positioning of controls to fit different sizes
    // ToDo: lock to portrait mode
    // ToDo: Add countdown timer before starting stopwatch
    // ToDo: Add visual countdown (display of time remaining)
    // ToDo: round (up) countdown time remaining to nearest second
    // ToDo: Add red light/green light to countdown
    // ToDo: Add beeps to countdown timer
    // ToDo: add different light patterns

    int countdownTime;
    int countdownRemain;
    String countdownRemainString;
    int tmr_ms, tmr_sec, tmr_min;
    long time_ms, time_start, countdown_start, time_buff, time_update = 0L;  // time_buff: buffer time for pause

    List<String> lapArrayList;
    String[] lapElements = new String[] {};
    ArrayAdapter<String> adapter;

    ListView lapView;
    TextView countdownView, timeView;
    Button start, stop, reset, lap;
    Handler handler;
    Intent intent;

    CountDownTimer cdt_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        intent = getIntent();
        countdownTime = intent.getIntExtra(MainActivity.START_SECONDS, 0);

        countdownView = findViewById(R.id.txt_countdown);
        timeView = findViewById(R.id.txt_time);
        lapView = findViewById(R.id.list_laps);

        // preload the countdown time
        countdownView.setText(String.format(Locale.US, "%d", countdownTime));

        start = findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);
        reset = findViewById(R.id.btn_reset);
        lap = findViewById(R.id.btn_lap);

        cdt_start = new CountDownTimer(countdownTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownRemain = (int)Math.ceil(millisUntilFinished / 1000.0);
                countdownView.setText(String.format(Locale.US, "%d", countdownRemain));
                // ToDo: fire beeps on integral-second intervals
            }

            @Override
            public void onFinish() {

            }
        };

        handler = new Handler();

        lapArrayList = new ArrayList<>(Arrays.asList(lapElements));
        adapter = new ArrayAdapter<>(TimerActivity.this, android.R.layout.simple_list_item_1, lapArrayList);
        lapView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countdown_start = SystemClock.uptimeMillis();
                time_start = countdown_start + 1000 * countdownTime;
                cdt_start.start();
                //handler.postDelayed(timer_countdown, 0);
                handler.postDelayed(timer_stopwatch, 1000 * countdownTime);
                reset.setEnabled(false);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TimeBuff += MillisecondTime;
                //handler.removeCallbacks(timer_countdown);
                handler.removeCallbacks(timer_stopwatch);
                cdt_start.cancel();
                // ToDo: reset countdown display time
                reset.setEnabled(true);
                //start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_ms = 0L;
                time_start = 0L;
                time_buff = 0L;
                time_update = 0L;
                tmr_sec = 0;
                tmr_min = 0;
                tmr_ms = 0;

                timeView.setText(getString(R.string.str_timedefault));
                lapArrayList.clear();
                adapter.notifyDataSetChanged();
                start.setEnabled(true);
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lapArrayList.add(timeView.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }




//    public Runnable timer_countdown = new Runnable() {
//        public void run() {
//            time_cd_ms = SystemClock.uptimeMillis() - countdown_start;
//            //time_update = time_buff + time_ms;
//            tmr_cd_sec = (int) (time_ms / 1000);
//            tmr_min = tmr_sec / 60;
//            tmr_sec = tmr_sec % 60;
//            tmr_ms = (int) (time_ms % 1000);
//            countdownView.setText(String.format(Locale.US, "%d", tmr_cd_sec));
//            handler.postDelayed(this, 0);
//        }
//    };

    public Runnable timer_stopwatch = new Runnable() {
        public void run() {
            time_ms = SystemClock.uptimeMillis() - time_start;
            //time_update = time_buff + time_ms;
            tmr_sec = (int) (time_ms / 1000);
            tmr_min = tmr_sec / 60;
            tmr_sec = tmr_sec % 60;
            tmr_ms = (int) (time_ms % 1000);
            timeView.setText(String.format(Locale.US, "%02d:%02d.%03d", tmr_min, tmr_sec, tmr_ms));
            handler.postDelayed(this, 0);
        }
    };
}
