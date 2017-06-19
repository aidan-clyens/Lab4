package lab1_202_12.uwaterloo.ca.lab3_202_12;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

/**
 * Created by aidan on 6/1/2017.
 */
class AccelerometerSensorEventListener implements SensorEventListener {

    private final double SMOOTHING_CONST = 15;

    private TextView output;

    private float vals[][] = new float[100][3];

    MotionFSM accellFSM;

    //  AccelerometerSensorEventListener Constructor
    public AccelerometerSensorEventListener(TextView outputView, float data[][], MotionFSM acc) {
        output = outputView;
        vals = data;
        accellFSM = acc;
    }

    //  Filter the raw accelerometer data and store it in an array
    private void setReading(float[] reading) {

        for (int i=98; i>=0; i--) {
            vals[i+1][0] = vals[i][0];
            vals[i+1][1] = vals[i][1];
            vals[i+1][2] = vals[i][2];
        }

        vals[0][0] += (reading[0] - vals[0][0]) / SMOOTHING_CONST;
        vals[0][1] += (reading[1] - vals[0][1]) / SMOOTHING_CONST;
        vals[0][2] += (reading[2] - vals[0][2]) / SMOOTHING_CONST;
    }


    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se) {
        if(se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            //  Shift the index of each column of the acceleration data array up by 1 and write to the start of the array

            setReading(se.values);

            //  Smooth accelerometer readings
            accellFSM.runFSM(vals[0]);
            output.setText(String.format("(%f, %f, %f)", vals[0][0], vals[0][1], vals[0][2]));

        }
    }
}
