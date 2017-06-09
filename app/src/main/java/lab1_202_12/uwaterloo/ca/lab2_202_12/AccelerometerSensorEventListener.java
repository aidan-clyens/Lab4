package lab1_202_12.uwaterloo.ca.lab2_202_12;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by aidan on 6/1/2017.
 */
class AccelerometerSensorEventListener implements SensorEventListener {

    private final double SMOOTHING_CONST = 25;

    private TextView output;
    private LineGraphView graph;

    private float vals[][] = new float[100][3];

    private DirectionFSM xDir;
    private DirectionFSM yDir;

    //  AccelerometerSensorEventListener Constructor
    public AccelerometerSensorEventListener(TextView outputView, LineGraphView graphView, float data[][], DirectionFSM x, DirectionFSM y) {
        graph = graphView;
        output = outputView;
        vals = data;
        xDir = x;
        yDir = y;
    }

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

    //  Setters allow the stored max values of data to be set to a specified value

    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se) {
        if(se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            //  Shift the index of each column of the acceleration data array up by 1 and write to the start of the array

            setReading(se.values);


            graph.addPoint(vals[0]);

            xDir.runFSM(vals[0][0]);
            yDir.runFSM(vals[0][1]);

//            output.setText(String.format("(%f, %f, %f)", vals[0][0], vals[0][1], vals[0][2]));

        }
    }
}
