package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.content.Context;
import android.util.Log;

/**
 * Created by aidan on 6/23/2017.
 */

public class GameBlock extends android.support.v7.widget.AppCompatImageView {

    private final float IMAGE_SCALE = 0.65f;
    //  Boundary conditions, subtract 80 to fix offset
    private final int TOP = 0 - 80;
    private final int BOTTOM = 1080 - 80;
    private final int LEFT = 0 - 80;
    private final int RIGHT = 1080 - 80;

    private GameLoopTask.directions myDir;
    private int myCoordX;
    private int myCoordY;

    private final int ACC = 15;
    private int velocity = 0;
    private int[] position = new int[2];

    private boolean moving;

    public GameBlock(Context myContext, int coordX, int coordY) {

        super(myContext);

        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);

        this.setX(coordX-80);
        this.setY(coordY-80);


        myCoordX = coordX;
        myCoordY = coordY;

        moving = false;
    }

    public void setBlockDirection(GameLoopTask.directions d) {

        if(!moving) myDir = d;
        Log.d("Block Direction", d.toString());
    }

    //  Using the direction received from the Accelerometer Event Handler, determine x and y coordinates of block
    public void move() {

        switch(myDir) {
            case DOWN:

                moving = true;

                position[1] += velocity;
                velocity += ACC;

                if(position[1] > BOTTOM) {
                    position[1] = BOTTOM;
                    velocity = 0;
                    moving = false;
                }

                this.setY(position[1]);
                break;

            case UP:

                moving = true;

                position[1] -= velocity;
                velocity += ACC;

                if(position[1] < TOP) {
                    position[1] = TOP;
                    velocity = 0;
                    moving = false;
                }

                this.setY(position[1]);
                break;

            case LEFT:

                moving = true;

                position[0] -= velocity;
                velocity += ACC;

                if(position[0] < LEFT) {
                    position[0] = LEFT;
                    velocity = 0;
                    moving = false;
                }
                this.setX(position[0]);
                break;

            case RIGHT:

                moving = true;

                position[0] += velocity;
                velocity += ACC;

                if(position[0] > RIGHT) {
                    position[0] = RIGHT;
                    velocity = 0;
                    moving = false;
                }
                this.setX(position[0]);
                break;

            default:
                break;
        }
    }
}
