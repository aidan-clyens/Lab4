package lab1_202_12.uwaterloo.ca.lab2_202_12;

import ca.uwaterloo.sensortoy.*;

import android.hardware.Sensor;
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

public class MainActivity extends AppCompatActivity {
    //  Make the line graph, sensor event listeners, and array for accelerometer data global variables
    LineGraphView graph;
    AccelerometerSensorEventListener aSel;


    float accData[][] = new float[100][3];

    //  Write to a CSV file given a 2 dimensional double array of size [100][3]
    private void writeToFile(float data[][]) {

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

        DirectionFSM xDir = new DirectionFSM(tv2);
        DirectionFSM yDir = new DirectionFSM(tv3);


        // Declare a Sensor Manager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //  Declaration of sensor event listeners

        // Acceleration Sensor Event Listener
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        aSel = new AccelerometerSensorEventListener(tv1, graph, accData, xDir, yDir);
        sensorManager.registerListener(aSel, accSensor, sensorManager.SENSOR_DELAY_GAME);

    }
}


