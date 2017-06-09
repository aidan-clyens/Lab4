package lab1_202_12.uwaterloo.ca.lab2_202_12;

import android.widget.TextView;

/**
 * Created by aidan on 6/6/2017.
 */

public class DirectionFSM {

    private enum States{WAIT, RISE, FALL, STABLE, DETERMINED}
    private enum Directions{UNDETERMINED, RIGHT, LEFT, UP, DOWN}
    private enum UpDownStates{UNDETERMINED, RISE, FALL}

    private States state;
    private Directions direction;
    private UpDownStates riseFall;

    private final float THRESHOLD_RIGHT[] = {0.1f, 1.3f};
    private final float THRESHOLD_LEFT[] = {-0.1f, -1.3f};
    private final float ZERO_THRESHOLD = 0.5f;

    private final int SAMPLE_COUNT = 30;

    private int count;

    private TextView display;

    private float prevVal = 0;

    // 0 is x-axis and 1 is y-axis
    boolean axis = false;

    public DirectionFSM(TextView tv, boolean a) {
        state = States.WAIT;
        direction = Directions.UNDETERMINED;
        riseFall = UpDownStates.UNDETERMINED;
        count = SAMPLE_COUNT;
        display = tv;
        axis = a;
    }

    private void resetFSM() {

        state = States.WAIT;
        direction = Directions.UNDETERMINED;
        riseFall = UpDownStates.UNDETERMINED;
        count = SAMPLE_COUNT;
    }

    public void runFSM(float val) {

        float slope = val - prevVal;

        switch(state) {

            case WAIT:

                if(slope >= THRESHOLD_RIGHT[0]) {
                    state = States.RISE;

                } else if(slope <= THRESHOLD_LEFT[0]){
                    state = States.FALL;

                }

                break;

            case RISE:

                if(slope <= 0) {

                    if(prevVal >= THRESHOLD_RIGHT[1]) {
                        state = States.STABLE;
                        riseFall = UpDownStates.RISE;

                    } else {
                        state = States.DETERMINED;
                        direction = Directions.UNDETERMINED;

                    }
                }

                break;

            case FALL:

                if(slope >= 0) {

                    if(prevVal <= THRESHOLD_LEFT[1]) {
                        state = States.STABLE;
                        riseFall = UpDownStates.FALL;

                    } else {
                        state = States.DETERMINED;
                        direction = Directions.UNDETERMINED;

                    }
                }

                break;

            case STABLE:

                count--;

                if(count == 0) {

                    state = States.DETERMINED;

                    if(Math.abs(val) <= ZERO_THRESHOLD) {

                        if(riseFall == UpDownStates.RISE) {

                            if(axis) direction = Directions.UP;
                            else direction = Directions.RIGHT;

                        } else if(riseFall == UpDownStates.FALL) {

                            if(axis) direction = Directions.DOWN;
                            else direction = Directions.LEFT;

                        }
                    } else {

                        direction = Directions.UNDETERMINED;
                    }
                }

                break;

            case DETERMINED:

                display.setText(direction.toString());

                resetFSM();

                break;

            default:

                resetFSM();

                break;
        }

        prevVal = val;
    }
}
