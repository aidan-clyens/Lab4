package lab1_202_12.uwaterloo.ca.lab3_202_12;

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

    public GameBlock(Context myContext, int coordX, int coordY) {

        super(myContext);

        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);

        this.setX(coordX-80);
        this.setY(coordY-80);


        myCoordX = coordX;
        myCoordY = coordY;
    }

    public void setBlockDirection(GameLoopTask.directions d) {

        myDir = d;
        Log.d("Block Direction", d.toString());
    }

    //  Using the direction received from the Accelerometer Event Handler, determine x and y coordinates of block
    public void move(GameLoopTask.directions d) {

        switch(d) {
            case DOWN:
                this.setY(BOTTOM);
                break;

            case UP:
                this.setY(TOP);
                break;

            case LEFT:
                this.setX(LEFT);
                break;

            case RIGHT:
                this.setX(RIGHT);
                break;

            default:
                break;
        }
    }
}
