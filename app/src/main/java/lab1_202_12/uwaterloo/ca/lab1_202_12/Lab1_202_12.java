package lab1_202_12.uwaterloo.ca.lab1_202_12;

import ca.uwaterloo.sensortoy.*;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


import static android.R.color.white;

public class Lab1_202_12 extends AppCompatActivity {
    //  Make the line graph, sensor event listeners, and array for accelerometer data global variables
    LineGraphView graph;
    AccelerometerSensorEventListener aSel;
    MagneticSensorEventListener mSel;
    RotationSensorEventListener rSel;

    double accData[][] = new double[100][3];

    //  Clear the max data from all sensor event handlers
    private void clearMaxData(AccelerometerSensorEventListener a, MagneticSensorEventListener m, RotationSensorEventListener r) {
        a.setHighestX(0);
        a.setHighestY(0);
        a.setHighestZ(0);

        m.setHighestX(0);
        m.setHighestY(0);
        m.setHighestZ(0);

        r.setHighestX(0);
        r.setHighestY(0);
        r.setHighestZ(0);
    }

    //  Write to a CSV file given a 2 dimensional double array of size [100][3]
    private void writeToFile(double data[][]) {

        //  Save to "data.csv" in app folder "Data"
        File file = new File(getExternalFilesDir("Data"), "data.csv");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);

            for(int i=0; i<100; i++) {
                writer.println(String.format("%f,%f,%f", data[i][0], data[i][1], data[i][2]));
            }

        } catch (IOException e) {
            Log.d("PrintWriter", "Cannot open writer!");

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_202_12);

        LinearLayout l = (LinearLayout) findViewById(R.id.linearLayout);
        l.setOrientation(LinearLayout.VERTICAL);

        graph = new LineGraphView(getApplicationContext(), 100, Arrays.asList("x","y","z"));
        l.addView(graph);
        graph.setVisibility(View.VISIBLE);

        // Clear max data button
        Button clearDataBtn = new Button(getApplicationContext());
        clearDataBtn.setText("Clear Max Data");
        l.addView(clearDataBtn);

        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMaxData(aSel, mSel, rSel);
            }
        });

        //  Write accelerometer data to file button
        Button writeFile = new Button(getApplicationContext());
        writeFile.setText("Generate CSV for Acc Data");
        l.addView(writeFile);

        writeFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile(accData);
            }
        });

        // TextViews 1 - 4
        TextView tv1 = new TextView(getApplicationContext());
        tv1.setTextColor(getResources().getColor(white));
        l.addView(tv1);

        TextView tv2 = new TextView(getApplicationContext());
        tv2.setTextColor(getResources().getColor(white));
        l.addView(tv2);

        TextView tv3 = new TextView(getApplicationContext());
        tv3.setTextColor(getResources().getColor(white));
        l.addView(tv3);

        TextView tv4 = new TextView(getApplicationContext());
        tv4.setTextColor(getResources().getColor(white));
        l.addView(tv4);

        // Declare a Sensor Manager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //  Declaration of sensor event listeners

        // Light Sensor Event Listener
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        LightSensorEventListener sel = new LightSensorEventListener(tv1);
        sensorManager.registerListener(sel, lightSensor, sensorManager.SENSOR_DELAY_GAME);

        // Acceleration Sensor Event Listener
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        aSel = new AccelerometerSensorEventListener(tv2, graph, accData);
        sensorManager.registerListener(aSel, accSensor, sensorManager.SENSOR_DELAY_GAME);

        // Magnetic Field Sensor Event Listener
        Sensor magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSel = new MagneticSensorEventListener(tv3);
        sensorManager.registerListener(mSel, magSensor, sensorManager.SENSOR_DELAY_GAME);

        // Rotation Vector Sensor Event Listener
        Sensor rotSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        rSel = new RotationSensorEventListener(tv4);
        sensorManager.registerListener(rSel, rotSensor, sensorManager.SENSOR_DELAY_GAME);
    }
}

class LightSensorEventListener implements SensorEventListener {
    private TextView output;

    //  LightSensorEventListener Constructor
    public LightSensorEventListener(TextView outputView) {

        output = outputView;
        output = outputView;
    }

    public void onAccuracyChanged(Sensor s, int i) { }

    public void onSensorChanged(SensorEvent se) {
        if(se.sensor.getType() == Sensor.TYPE_LIGHT) {
            double lightValue = se.values[0];
            output.setText("\nLight Intensity: " + lightValue);
        }
    }
}

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

class MagneticSensorEventListener implements SensorEventListener {
    private TextView output;
    private double highestX = 0;
    private double highestY = 0;
    private double highestZ = 0;

    //  MagneticSensorEventListener Constructor
    public MagneticSensorEventListener(TextView outputView) {

        output = outputView;
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
        if(se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
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

            String outS = String.format("(%f, %f, %f)", x,y,z);
            String maxS = String.format("(%f, %f, %f)", highestX,highestY,highestZ);
            output.setText("\nMagnetic: " + outS + "\nRecord-High: " + maxS);
        }
    }
}

class RotationSensorEventListener implements SensorEventListener {
    private TextView output;
    private double highestX = 0;
    private double highestY = 0;
    private double highestZ = 0;

    //  RotationSensorEventListener Constructor
    public RotationSensorEventListener(TextView outputView) {

        output = outputView;
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
        if(se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
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

            String outS = String.format("(%f, %f, %f)", x,y,z);
            String maxS = String.format("(%f, %f, %f)", highestX,highestY,highestZ);
            output.setText("\nRotation: " + outS + "\nRecord-High: " + maxS);
        }
    }
}

