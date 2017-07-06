package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

/**
 * Created by Tina on 2017-06-29.
 */

public abstract class GameBlockTemplate extends AppCompatImageView {  //new abstract class
    public GameBlockTemplate(Context context) {
        super(context);
    }//constructor

    abstract void setBlockDirection(GameLoopTask.directions d, int targetPosX, int targetPosY);

    abstract void move();
}
