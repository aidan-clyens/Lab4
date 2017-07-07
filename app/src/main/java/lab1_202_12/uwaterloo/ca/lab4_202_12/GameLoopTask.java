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

    private LinkedList<GameBlock> myGBList = new LinkedList<>();

    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private GameBlock newBlock;

    private boolean createdBlock;
    private boolean blockMoving;

    private Random random = new Random();

    private int coordX;
    private int coordY;

    private int targetPosX;
    private int targetPosY;


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
        targetPosX = 0;
        targetPosY = 0;

        //  Set target directions
        switch (d) {
            case UP:
                targetPosY = (TOP + OFFSET) / SLOT_SEPARATION;
                break;
            case DOWN:
                targetPosY = (BOTTOM + OFFSET) / SLOT_SEPARATION;
                break;
            case RIGHT:
                targetPosX = (RIGHT + OFFSET) / SLOT_SEPARATION;
                break;
            case LEFT:
                targetPosX = (LEFT + OFFSET) / SLOT_SEPARATION;
                break;
            case NO_MOVEMENT:
                break;
            default:
                break;
        }

        //  For each Game Block in the linked list, set the same direction for all of them
        for(GameBlock gb : myGBList) gb.setBlockDirection(d, targetPosX, targetPosY);

        //  Test if any Game Blocks are moving or not
        for(GameBlock gb : myGBList) blockMoving = gb.moving;

        //  Create one new Game Block if a gesture has been used and if the existing Game Blocks are not already moving
        if(d != directions.NO_MOVEMENT && !createdBlock && !blockMoving) {

            createBlock();
            createdBlock = true;

        } else if(d == directions.NO_MOVEMENT) {

            createdBlock = false;
        }

    }

    private boolean isOccupied(int x, int y) {
        //  Check the target positions of all Game Blocks.
        //  Return true if the slot is occupied, false if not
        //  Take x: 0 to 3, and y: 0 to 3 as inputs
        boolean occupied = false;

        for(GameBlock gb : myGBList) {

            occupied = (x == gb.getTargetPosition()[0] && y == gb.getTargetPosition()[1]);
        }

        return occupied;
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
        newBlock.setBlockDirection(directions.NO_MOVEMENT, targetPosX, targetPosY);

        myGBList.add(newBlock);
    }
}
