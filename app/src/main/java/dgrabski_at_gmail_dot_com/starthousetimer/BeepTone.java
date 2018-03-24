package dgrabski_at_gmail_dot_com.starthousetimer;

/**
 * Created by Dan on 3/22/2018.
 */

class BeepTone {

    private byte[] sound;
    private int duration;   // duration in ms

    BeepTone(int duration, int frequency, int sampleRate) {
        this.duration = duration;
        int numSamples = duration * sampleRate;
        double[] sample = new double[numSamples];
        sound = new byte[2*numSamples];

        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/frequency));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            sound[idx++] = (byte) (val & 0x00ff);
            sound[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    byte[] getSound() {
        return sound;
    }

    int getDuration() {
        return duration;
    }


}
