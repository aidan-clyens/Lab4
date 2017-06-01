package lab1_202_12.uwaterloo.ca.lab2_202_12;

import android.util.Log;

/**
 * Created by aidan on 6/1/2017.
 */

public class myFSM {

    enum FSMStates{WAIT, RISE, FALL, STABLE, DETERMINED};
    private FSMStates myStates;

    enum Signatures{LEFT, RIGHT, UNDETERMINED};
    private Signatures mySig;

    private final float[] THRESHOLD_RIGHT = {1.0f, 1.5f, 0.2f};

    private int sampleCounter;
    private final int SAMPLE_COUNTER_DEFAULT = 30;

    private float previousReading;

    public myFSM() {
        myStates = FSMStates.WAIT;
        mySig = Signatures.UNDETERMINED;
        sampleCounter = SAMPLE_COUNTER_DEFAULT;
        previousReading = 0;

    }

    public void resetFSM() {
        myStates = FSMStates.WAIT;
        mySig = Signatures.UNDETERMINED;
        sampleCounter = SAMPLE_COUNTER_DEFAULT;
        previousReading = 0;
    }

    public void activateFSM(float accInput) {

        float accSlope = accInput - previousReading;

        switch(myStates) {
            case WAIT:

                Log.d("My FSM Says:", String.format("I'm Waiting on Slope %f", accSlope));

                if (accSlope >= THRESHOLD_RIGHT[0]) {
                    myStates = FSMStates.RISE;
                }

                break;

            case RISE:

                Log.d("My FSM Says:", "I'm Rising...");

                break;

            case FALL:

                break;

            case STABLE:

                break;

            case DETERMINED:

                break;

            default:
                resetFSM();
                break;
        }

        previousReading = accInput;
    }
}
