package lab1_202_12.uwaterloo.ca.lab3_202_12;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import java.util.TimerTask;

/**
 * Created by aidan on 6/22/2017.
 */

public class GameLoopTask extends TimerTask {

    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;

    public GameLoopTask(Activity myAct, Context myCon, RelativeLayout rl) {
        myActivity = myAct;
        myContext = myCon;
        myRL = rl;
    }

    public void run() {

    }
}
