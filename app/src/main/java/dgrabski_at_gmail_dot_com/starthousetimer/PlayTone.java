package dgrabski_at_gmail_dot_com.starthousetimer;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;

/**
 * Created by Dan on 3/22/2018.
 */

class PlayTone extends Activity {

    //@Override
    public void onCreate(Bundle savedInstanceState, BeepTone beepTone) {
        // retrieve tone
        byte[] sound = beepTone.getSound();
        int sampleRate = beepTone.getSampleRate();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, sound.length,
                AudioTrack.MODE_STATIC);

        audioTrack.write(sound, 0, sound.length);
        audioTrack.play();
    }




}
