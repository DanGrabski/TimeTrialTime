package dgrabski_at_gmail_dot_com.starthousetimer;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    // done v0.03: lock to portrait mode
    // done v0.02: Add countdown timer before starting stopwatch
    // done v0.02: Add visual countdown (display of time remaining)
    // done v0.02: round (up) countdown time remaining to nearest second
    // ToDo: Add red light/green light to countdown
    // ToDo: Add beeps to countdown timer
    // ToDo: add different light patterns
    // ToDo: pass textview object to function to adjust colors instead of the current way

    int countdownTime;
    int countdownRemain;
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
    BeepHandler beepHandler;
    AudioTrack audioTrack;

    CountDownTimer cdt_start;

    static final int sampleRate = 8000;

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
        // set the countdown initial color
        countdownView.setBackgroundColor(countdownColors(countdownTime)[0]);
        countdownView.setTextColor(countdownColors(countdownTime)[1]);

        final BeepTone lowShortBeep = new BeepTone(100,440, sampleRate);
        BeepTone lowLongBeep = new BeepTone(500, 440, sampleRate);
        BeepTone highLongBeep = new BeepTone(500, 880, sampleRate);
        beepHandler = new BeepHandler();
//        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
//                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
//                AudioFormat.ENCODING_PCM_16BIT, sound.length,
//                AudioTrack.MODE_STATIC);
        // ToDo: THIS IS DEBUG ONLY
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, lowShortBeep.getSound().length,
                AudioTrack.MODE_STATIC);

        // ToDo: replace with beep configuration from settings
        //beepHandler.addBeep(10, lowLongBeep);
        beepHandler.addBeep(5, lowShortBeep);
        beepHandler.addBeep(4, lowShortBeep);
        beepHandler.addBeep(3, lowShortBeep);
        beepHandler.addBeep(2, lowShortBeep);
        beepHandler.addBeep(1, lowShortBeep);
        //beepHandler.addBeep(0, highLongBeep);

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
                BeepTone _sound = beepHandler.returnBeep(countdownRemain);
                if (_sound != null) {
                    // ToDo: DEBUG ONLY
                    audioTrack.write(lowShortBeep.getSound(), 0, lowShortBeep.getSound().length);
                    BeeperThread t = new BeeperThread(audioTrack);
                    StopBeeperThread tl = new StopBeeperThread(t);
                    t.start();
                    tl.start();
//                    PlayTone _playTone = new PlayTone(_sound);
                }
            }

            @Override
            public void onFinish() {
                countdownView.setText("0");
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
                // done: reset countdown display time
                countdownView.setText(String.format(Locale.US, "%d", countdownTime));
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

        countdownView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // update background color as needed
                String _s = charSequence.toString();
                int _val = Integer.parseInt(_s);
                countdownView.setBackgroundColor(countdownColors(_val)[0]);
                countdownView.setTextColor(countdownColors(_val)[1]);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

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

    private int[] countdownColors(int timeRemain) {
        // returns the required color for the current time remaining
        int _backColor;
        int _foreColor;
        if (timeRemain == 0) {
            _backColor = Color.GREEN;
            _foreColor = Color.BLACK;
        } else if (timeRemain <= 5) {
            _backColor = Color.YELLOW;
            _foreColor = Color.BLACK;
        } else {
            _backColor = Color.RED;
            _foreColor = Color.WHITE;
        }
        int _retarray[] = {_backColor, _foreColor};
        return _retarray;
    }


}

//class TimeLimitRunnable implements Runnable {
//    Thread _beepThread;
//    int _waitTimeMs;
//    long _killTimeMs;
//
//    TimeLimitRunnable(Thread _beepThread, int _waitTimeMs) {
//        this._beepThread = _beepThread;
//        this._waitTimeMs = _waitTimeMs;
//        _killTimeMs = SystemClock.uptimeMillis() + _waitTimeMs;
//    }
//
//    public void run() {
//        while (SystemClock.uptimeMillis() < _killTimeMs) {
//            // just wait
//        }
//        _beepThread.interrupt();
//        Thread.currentThread().interrupt();
//    }
//}

//// turn this just into runnable?
//class BeeperRunnable implements Runnable {
//    private AudioTrack _audioTrack;
//
//    BeeperRunnable(AudioTrack _audioTrack) {
//        this._audioTrack = _audioTrack;
//    }
//
//    public void run() {
//        _audioTrack.play();
////        }
//    }
//}

// so this probably isn't the best way to do this, but it
// means practice working with threads
class BeeperThread extends Thread {
    private boolean _isRunning = true;
    private AudioTrack _audioTrack;

    BeeperThread(AudioTrack _audioTrack) {
        this._audioTrack = _audioTrack;
    }

    public void run() {
        _audioTrack.play();
        while (_isRunning) {
            // just let it play
        }
    }

    public void stopBeeperThread() {
        _audioTrack.pause();
        _audioTrack.flush();
        _isRunning = false;
    }
}

class StopBeeperThread extends Thread {
    private BeeperThread _beeper;

    StopBeeperThread(BeeperThread _beeper) {
        this._beeper = _beeper;
    }

    public void run() {
        // timer
        try {
            Thread.currentThread().sleep(400);
        } catch (InterruptedException e) {
            _beeper.stopBeeperThread();
            Thread.currentThread().interrupt();
        }
    }
}