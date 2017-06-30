package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by aidan on 6/22/2017.
 */

public class GameLoopTask extends TimerTask {

    protected static final int OFFSET = 80;
    protected static final int TOP = 0 - OFFSET;
    protected static final int LEFT = 0 - OFFSET;
    protected static final int BOTTOM = 1080 - OFFSET;
    protected static final int RIGHT = 1080 - OFFSET;

    protected static final int SLOT_SEPARATION = 360;

    enum directions{UP, DOWN, LEFT, RIGHT, NO_MOVEMENT}
    directions dir = directions.NO_MOVEMENT;

    LinkedList<GameBlock> myGBList = new LinkedList<>();

    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private GameBlock newBlock;

    private boolean createdBlock;
    private boolean blockMoving;

    private Random random = new Random();

    private int coordX;
    private int coordY;


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

                        //  Move all existing Game Blocks at once
                        for(GameBlock gb : myGBList) {

                            gb.move();
                        }
                    }
                }
        );
    }


    public void setDirection(directions d) {

        dir = d;

        //  For each Game Block in the linked list, set the same direction for all of them
        for(GameBlock gb : myGBList) {

            gb.setBlockDirection(d);
        }

        //  Test if Game Blocks are moving or not
        for(GameBlock gb : myGBList) {

            if(gb.moving) blockMoving = true;
            else blockMoving = false;

        }

        //  Create one new Game Block if a gesture has been used and if the existing Game Blocks are not already moving
        if(d != directions.NO_MOVEMENT && !createdBlock && !blockMoving) {

            createBlock();
            createdBlock = true;

        } else if(d == directions.NO_MOVEMENT) {

            createdBlock = false;
        }

    }

    private void createBlock() {

        //  Choose a slot on the 4 by 4 gameboard
        int x = random.nextInt(3);
        int y = random.nextInt(3);

        //  Determine the selected slot's coordinates
        coordX = (x * SLOT_SEPARATION) - OFFSET;
        coordY = (y * SLOT_SEPARATION) - OFFSET;

        //  Add new Game Block to board
        newBlock = new GameBlock(myContext, myRL, coordX, coordY);
        newBlock.setBlockDirection(directions.NO_MOVEMENT);

        myGBList.add(newBlock);
    }
}
