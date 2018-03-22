package dgrabski_at_gmail_dot_com.starthousetimer;

import java.util.ArrayList;

/**
 * Created by Dan on 3/22/2018.
 */

class BeepHandler {

    private ArrayList<ArrayList<Integer>> beepArray = new ArrayList<>();


    int addBeep(int second, int duration, int frequency) {
        ArrayList<Integer> _add = new ArrayList<>();
        try {
            _add.add(second);       // adds the second to make the beep
            _add.add(duration);     // duration of beep in ms
            _add.add(frequency);    // frequency of beep in Hz
        } catch (Exception e) {
            return -1;
        }

        beepArray.add(_add);
        return 0;
    }

    byte[] returnBeep (int seconds) {
        // search for beeps at this second value

    }
}
