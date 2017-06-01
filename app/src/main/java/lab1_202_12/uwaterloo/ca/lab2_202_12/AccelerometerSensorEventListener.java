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
    private TextView output;
    private LineGraphView graph;

    private double vals[][] = new double[100][3];

    private double highestX = 0;
    private double highestY = 0;
    private double highestZ = 0;

    //  AccelerometerSensorEventListener Constructor
    public AccelerometerSensorEventListener(TextView outputView, LineGraphView graphView, double data[][]) {
        graph = graphView;
        output = outputView;
        vals = data;
    }

    //  Setters allow the stored max values of data to be set to a specified value
    public void setHighestX(double max) {
        this.highestX = max;
    }

    public void setHighestY(double max) {
        this.highestY = max;
    }

    public void setHighestZ(double max) {
        this.highestZ = max;
    }

    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se) {
        if(se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double x = se.values[0];
            double y = se.values[1];
            double z = se.values[2];

            //  Set the max values to the max absolute values of the readings in the x, y and z directions
            if(Math.abs(x) > highestX) {
                highestX = Math.abs(x);
            }
            if(Math.abs(y) > highestY) {
                highestY = Math.abs(y);
            }
            if(Math.abs(z) > highestZ) {
                highestZ = Math.abs(z);
            }

            //  Shift the index of each column of the acceleration data array up by 1 and write to the start of the array
            for (int i=98; i>=0; i--) {
                vals[i+1][0] = vals[i][0];
                vals[i+1][1] = vals[i][1];
                vals[i+1][2] = vals[i][2];
            }

            vals[0][0] = x;
            vals[0][1] = y;
            vals[0][2] = z;

            graph.addPoint(se.values);

            String outS = String.format("(%f, %f, %f)", x,y,z);
            String maxS = String.format("(%f, %f, %f)", highestX,highestY,highestZ);
            output.setText("\nAcceleration: " + outS + "\nRecord-High: " + maxS);

        }
    }
}
