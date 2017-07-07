package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import static android.R.color.black;

/**
 * Created by aidan on 6/23/2017.
 */

public class GameBlock extends GameBlockTemplate {

    private final float IMAGE_SCALE = 0.65f;

    private GameLoopTask.directions myDir;

    //  Used for the moving animation
    private final int ACC = 15;
    private int velocity = 0;
    private int[] position = new int[2];

    public boolean moving;

    private RelativeLayout myRL;
    private TextView tv;
    private int blockNumber;

    //  Target positions
    private int[] targetPosition = new int[2];

    //  Current Slot
    int[] currentSlot = {0,0};


    public GameBlock(Context myContext, RelativeLayout RL, int coordX, int coordY) {

        super(myContext);

        Random ran = new Random();

        //  Set the image resourse and scale of the newly created Game Block
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);

        this.setX(coordX);
        this.setY(coordY);

        position[0] = coordX;
        position[1] = coordY;

        //  Randomly choose 2 or 4 to display on the Game Black
        blockNumber = (ran.nextInt(2) + 1) * 2;

        //  Create a TextView to attach to the GameBlock, displaying the block number
        tv = new TextView(myContext);
        tv.setX(coordX+250);
        tv.setY(coordY+225);
        tv.setScaleX(3f);
        tv.setScaleY(3f);
        tv.setText(String.format("%d", blockNumber));
        tv.setTextColor(getResources().getColor(black));
        tv.bringToFront();

        RL.addView(this);
        RL.addView(tv);

        myRL = RL;

        //  When the Game Block is created, it is not moving
        moving = false;
    }


    public void setBlockDirection(GameLoopTask.directions d, int targetPosX, int targetPosY) {
        //  Set block direction and target position
        //  Create temporary field to track the number of blocks in between current and target position
        //  Create temporary field to track the number of slots along the way
        //  Original target position is boundary



        if(d == GameLoopTask.directions.UP || d == GameLoopTask.directions.DOWN) {

            targetPosition[0] = getCurrentPosition()[0];
            targetPosition[1] = (targetPosY + GameLoopTask.OFFSET) / GameLoopTask.SLOT_SEPARATION;

        } else if(d == GameLoopTask.directions.LEFT || d == GameLoopTask.directions.RIGHT) {

            targetPosition[0] = (targetPosX + GameLoopTask.OFFSET) / GameLoopTask.SLOT_SEPARATION;
            targetPosition[1] = getCurrentPosition()[1];
        }

        Log.d("Set direction", String.format("(%d, %d)", targetPosition[0], targetPosition[1]));

        //  1) Check if the target position is occupied, if yes, increment blockCount
        //  2) Increment slotCount for all slots checked
        //  3) Check the coordinate 1 slot closer to the current location of the Game Block, increment blockCount and slotCount if needed
        //  4) Calculate the number of empty slots by subtracting blockCount from slotCount, use to determine correct target position

        if(!moving) myDir = d;  //  If the block is not currently moving, change it's direction
    }


    public int[] getCurrentPosition() {
        //  Returns current position

        float xMod = (position[0] + GameLoopTask.OFFSET) % GameLoopTask.SLOT_SEPARATION;
        float yMod = (position[1] + GameLoopTask.OFFSET) % GameLoopTask.SLOT_SEPARATION;

        if(xMod == 0 && yMod == 0) {
            currentSlot[0] = (position[0] + GameLoopTask.OFFSET) / GameLoopTask.SLOT_SEPARATION;
            currentSlot[1] = (position[1] + GameLoopTask.OFFSET) / GameLoopTask.SLOT_SEPARATION;
        }

        return this.currentSlot;
    }


    public int[] getTargetPosition() {
        //  Returns target position
        //  Target position must have correct x and y components

        targetPosition[0] = (targetPosition[0] - GameLoopTask.OFFSET) / GameLoopTask.SLOT_SEPARATION;
        targetPosition[1] = (targetPosition[1] - GameLoopTask.OFFSET) / GameLoopTask.SLOT_SEPARATION;

        Log.d("Target Slot", String.format("(%d, %d)", targetPosition[0], targetPosition[1]));

        return this.targetPosition;
    }


    //  Using the direction received from the Accelerometer Event Handler, determine x and y coordinates of block
    public void move() {
        //  Instead of setting position to boundaries, use target position
        int[] x = this.getCurrentPosition();

        switch(myDir) {
            case DOWN:

                moving = true;  //  The block is moving

                position[1] += velocity;
                velocity += ACC;

                //  If the y-coord is outside of the BOTTOM boundary, set the y-position to the BOTTOM boundary and the velocity to 0
                if(position[1] > GameLoopTask.BOTTOM) {
                    position[1] = GameLoopTask.BOTTOM;
                    velocity = 0;
                    moving = false;     //  The block has stopped moving
                }

                this.setY(position[1]);
                tv.setY(position[1]+225);

                break;

            case UP:

                moving = true;  //  The block is moving

                position[1] -= velocity;
                velocity += ACC;

                //  If the y-coord is outside of the TOP boundary, set the y-position to the TOP boundary and the velocity to 0
                if(position[1] < GameLoopTask.TOP) {
                    position[1] = GameLoopTask.TOP;
                    velocity = 0;
                    moving = false;     //  The block has stopped moving
                }

                this.setY(position[1]);
                tv.setY(position[1]+225);

                break;

            case LEFT:

                moving = true;  //  The block is moving

                position[0] -= velocity;
                velocity += ACC;

                //  If the x-coordinate is outside of the LEFT boundary, set the y-position to the LEFT boundary and the velocity to 0
                if(position[0] < GameLoopTask.LEFT) {
                    position[0] = GameLoopTask.LEFT;
                    velocity = 0;
                    moving = false;     //  The block has stopped moving
                }

                this.setX(position[0]);
                tv.setX(position[0]+250);

                break;

            case RIGHT:

                moving = true;  //  The block is moving

                position[0] += velocity;
                velocity += ACC;

                //  If the x-coordinate is outside of the RIGHT boundary, set the y-position to the RIGHT boundary and the velocity to 0
                if(position[0] > GameLoopTask.RIGHT) {
                    position[0] = GameLoopTask.RIGHT;
                    velocity = 0;
                    moving = false;     //  The block has stopped moving
                }

                this.setX(position[0]);
                tv.setX(position[0]+250);

                break;

            default:
                break;
        }
    }
}
