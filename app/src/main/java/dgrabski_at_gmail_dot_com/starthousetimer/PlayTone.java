package dgrabski_at_gmail_dot_com.starthousetimer;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Created by Dan on 3/22/2018.
 */

class PlayTone {

    PlayTone(BeepTone beepTone) {

        // retrieve tone
        byte[] sound = beepTone.getSound();
        int sampleRate = beepTone.getSampleRate();

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, sound.length,
                AudioTrack.MODE_STATIC);

        audioTrack.write(sound, 0, sound.length);
        Thread t = new Thread(new BeeperRunnable(audioTrack));
        t.start();
    }

}

class BeeperRunnable implements Runnable {
    private AudioTrack _audioTrack;

    BeeperRunnable(AudioTrack _audioTrack) {
        this._audioTrack = _audioTrack;
    }

    public void run() {
        _audioTrack.play();
    }
}
