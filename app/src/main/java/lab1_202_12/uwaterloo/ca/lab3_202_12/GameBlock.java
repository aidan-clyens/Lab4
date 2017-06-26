package lab1_202_12.uwaterloo.ca.lab3_202_12;

import android.content.Context;
import android.util.Log;

/**
 * Created by aidan on 6/23/2017.
 */

public class GameBlock extends android.support.v7.widget.AppCompatImageView {

    private final float IMAGE_SCALE = 0.65f;
    private GameLoopTask.directions myDir;
    private int myCoordX;
    private int myCoordY;

    public GameBlock(Context myContext, int coordX, int coordY) {

        super(myContext);

        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);

        //  Top left corner, (x1,y1) = (0,0)
        //  Top right corner, (x2,y2) = (1080, 0)
        //  Bottom left corner, (x3,y3) = (0, 1080)
        //  Bottom right corner, (x4,y4) = (1080, 1080)

        this.setX(coordX-80);
        this.setY(coordY-80);

        myCoordX = coordX;
        myCoordY = coordY;
    }

    public void setBlockDirection(GameLoopTask.directions d) {

        myDir = d;
        Log.d("Block Direction", d.toString());
    }
}
