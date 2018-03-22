package dgrabski_at_gmail_dot_com.starthousetimer;

import android.os.Handler;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dan on 3/22/2018.
 */

class BeepHandler {

    private SparseArray<BeepTone> beepMap = new SparseArray<>();

    void addBeep(int second, BeepTone _beepTone) {
        beepMap.put(second, _beepTone);
    }

    byte[] returnBeep (int seconds) {
        // search for beeps at this second value
        BeepTone _possibleBeep = beepMap.get(seconds);
        if (_possibleBeep == null) {
            return null;
        } else {
            return _possibleBeep.getSound();
        }
    }
}
