package lab1_202_12.uwaterloo.ca.lab3_202_12;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.R.color.white;

public class MainActivity extends AppCompatActivity {
    //  Make the line graph, sensor event listeners, and array for accelerometer data global variables
    AccelerometerSensorEventListener aSel;

    private int [] GAMEBOARD_DIMENSION  = { 1440,  2560}; //width, height to be chnged to phone


    float accData[][] = new float[100][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_202_12);

        RelativeLayout l = (RelativeLayout) findViewById(R.id.relativeLayout);
        //l.setOrientation(RelativeLayout.VERTICAL); <- don't need?
        l.getLayoutParams().width = GAMEBOARD_DIMENSION[0];
        l.getLayoutParams().height = GAMEBOARD_DIMENSION[1]; //set layout dimensions

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


        // Declare a Sensor Manager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //  Declaration of sensor event listeners
        MotionFSM accelFSM = new MotionFSM( tv2);
        // Acceleration Sensor Event Listener
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        aSel = new AccelerometerSensorEventListener(tv1, accData, accelFSM);
        sensorManager.registerListener(aSel, accSensor, sensorManager.SENSOR_DELAY_GAME);

    }
}


