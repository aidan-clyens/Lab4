package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.content.Context;
import android.util.Log;

/**
 * Created by aidan on 6/23/2017.
 */

public class GameBlock extends GameBlockTemplate {

    private final float IMAGE_SCALE = 0.65f;

    private GameLoopTask.directions myDir;
    private int myCoordX;
    private int myCoordY;

    private final int ACC = 15;
    private int velocity = 0;
    private int[] position = new int[2];

    public boolean moving;

    public GameBlock(Context myContext, int coordX, int coordY) {

        super(myContext);

        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);

        this.setX(coordX);
        this.setY(coordY);

        moving = false;
    }

    public void setBlockDirection(GameLoopTask.directions d) {

        if(!moving) myDir = d;  //  If the block is not currently moving, change it's direction
    }

    //  Using the direction received from the Accelerometer Event Handler, determine x and y coordinates of block
    public void move() {

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

                break;

            default:
                break;
        }
    }
}
