package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;
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

    private Random random = new Random();


    public GameLoopTask(Activity myAct, Context myCon, RelativeLayout rl) {
        myActivity = myAct;
        myContext = myCon;
        myRL = rl;

        int x = random.nextInt(1080);
        int y = random.nextInt(1080);

        createBlock(x, y);
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

    private void createBlock(int xCoord, int yCoord) {

        newBlock = new GameBlock(myContext, xCoord, yCoord);
        myRL.addView(newBlock);
    }
}
