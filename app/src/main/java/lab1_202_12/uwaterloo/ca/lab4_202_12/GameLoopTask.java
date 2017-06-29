package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.TimerTask;

/**
 * Created by aidan on 6/22/2017.
 */

public class GameLoopTask extends TimerTask {

    enum directions{UP, DOWN, LEFT, RIGHT, NO_MOVEMENT}
    directions dir = directions.NO_MOVEMENT;
    LinkedList myGBList = new LinkedList();

    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private GameBlock newBlock;


    public GameLoopTask(Activity myAct, Context myCon, RelativeLayout rl) {
        myActivity = myAct;
        myContext = myCon;
        myRL = rl;

        createBlock();
    }

    public void run() {
        myActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {

                        newBlock.move();
                    }
                }
        );
    }

    public void setDirection(directions d) {
        dir = d;
        newBlock.setBlockDirection(d);
    }

    private void createBlock() {

        newBlock = new GameBlock(myContext, 0, 0);
        myRL.addView(newBlock);
    }
}
