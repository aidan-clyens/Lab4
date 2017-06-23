package lab1_202_12.uwaterloo.ca.lab3_202_12;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by Evan on 6/2/2017.
 */

public class MotionFSM {

    enum States{ Waiting, Rising, Stable, Determined };
    private States signalState;


    enum directions{ Left, Right,Up, Down, Undetermined};
    private directions dirs;


    private final float[] threshHolds = {0.5f, 1f, 0.4f};

    private int countDefault = 30;

    private int count;
    private float[] oldVal= {0,0,0};


    private TextView directionTV;

    public MotionFSM(TextView t)
    {
        count = countDefault;
        signalState = States.Waiting;
        dirs = directions.Undetermined;
        directionTV = t;
    }

    public void resetFSM()
    {
        count = countDefault;
        signalState = States.Waiting;
        dirs = directions.Undetermined;
    }

    public directions getGesture() {
        return dirs;
    }

    public void runFSM(float[] accVals)
    {

        float slopeX = accVals[0] - oldVal[0];
        float slopeY = accVals[1] - oldVal[1];
        switch (signalState)
        {
            case Waiting:
                //Log.d("FSM Says:", String.format("Waiting, Slope: %f", slopeX));
                if( accVals[0] > threshHolds[0])
                {
                    dirs = directions.Right;
                    signalState = States.Rising;
                }
                else if( accVals[0] < -threshHolds[0])
                {
                    dirs = directions.Left;
                    signalState = States.Rising;
                }
                else if( accVals[1] > threshHolds[0])
                {
                    dirs = directions.Up;
                    signalState = States.Rising;
                }
                else if( accVals[1] < -threshHolds[0])
                {
                    dirs = directions.Down;
                    signalState = States.Rising;
                }
                break;


            case Determined:
                Log.d("FSM Says:", "Determined");
                directionTV.setText(dirs.toString());
                resetFSM();
                break;


            case Rising:
                Log.d("FSM Says:", "Rising");
                if (dirs == directions.Right)
                {
                    if (slopeX <= 0)
                    {
                        if(accVals[0] > threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }
                else if (dirs == directions.Left)
                {
                    if (slopeX >= 0)
                    {
                        if(accVals[0] < -threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }
                else if (dirs == directions.Up)
                {
                    if (slopeY <= 0)
                    {
                        if(accVals[1] > threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }
                else if (dirs == directions.Down)
                {
                    if (slopeY >= 0)
                    {
                        if(accVals[1] < -threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }


                break;

            case Stable:
                Log.d("FSM Says:", "Falling" + count);
                count --;
                if( count == 0)
                {
                    Log.d("IN", "IN");
                    if ( dirs == directions.Right)
                    {
                        if (accVals[0] < threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                    else if ( dirs == directions.Left)
                    {
                        if (accVals[0] > -threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                    else if ( dirs == directions.Up)
                    {
                        if (accVals[1] < threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                    else if ( dirs == directions.Down)
                    {
                        if (accVals[1] > -threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = directions.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }

                break;



            default:
                resetFSM();
                break;
        }
        oldVal[0] = accVals[0];
        oldVal[1] = accVals[1];

    }



}
