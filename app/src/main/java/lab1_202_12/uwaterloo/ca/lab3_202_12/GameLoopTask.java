package lab1_202_12.uwaterloo.ca.lab3_202_12;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.TimerTask;

/**
 * Created by aidan on 6/22/2017.
 */

public class GameLoopTask extends TimerTask {

    enum directions{UP, DOWN, LEFT, RIGHT, NO_MOVEMENT}
    directions dir = directions.NO_MOVEMENT;

    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;


    public GameLoopTask(Activity myAct, Context myCon, RelativeLayout rl) {
        myActivity = myAct;
        myContext = myCon;
        myRL = rl;

        createBlock();
    }

    private int i = 1;
    private int sec = 0;

    public void run() {
        myActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        //  Do something - Test
                        if ((i % 20) == 0) {
                            Log.d("Test", String.format("%d", sec));
                            sec++;
                        }

                        i++;
                    }
                }
        );
    }

    public void setDirection(directions d) {
        dir = d;
    }

    private void createBlock() {

        GameBlock newBlock = new GameBlock(myContext, 1080, 1080);
        myRL.addView(newBlock);
    }
}
