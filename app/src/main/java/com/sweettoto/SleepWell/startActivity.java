package com.sweettoto.sleepwell;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startService(new Intent(StartActivity.this,PowerService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(StartActivity.this,PowerService.class));
    }

    public void GetTimestampOfGoToBed(View view) {
        react(1);
    }

    public void GetTimestampOfSleep(View view) {
        react(2);
    }

    public void GetTimestampOfWakeUp(View view) {
        react(3);
    }

    public void GetTimestampOfGetUp(View view) {
        react(4);
    }

    private void react(int code) {
        String tag;
//        String message = "";
        switch (code) {
            case 1: {
                tag = "GoToBed";
//                message = "GoToBed";
            }
            break;
            case 2: {
                tag = "Sleep";
//                message = "Sleep";
            }
            break;
            case 3: {
                tag = "WakeUp";
//                message = "WakeUp";
            }
            break;
            case 4: {
                tag = "GetUp";
//                message = "GetUp";
            }
            break;
            default:
                tag = "invalid";
        }

        //to generate a record
        String timestamp = getTimeStamp();
        SaveToJsonFile(tag, timestamp);
        // to show a message
        showMessage(tag, timestamp);
    }


    private String getTimeStamp() {
        Date currentTime = Calendar.getInstance().getTime();
        //to format the output string of timestamp
        SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return formator.format(currentTime);
    }

    private void showMessage(String description, String timestamp) {

        String time = timestamp.substring(11).replace("-", ":");
        CharSequence text = description + "\n" + time;
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        // to update the board
        TextView board = (TextView) findViewById(R.id.board);
        board.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            board.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

    }

    /**
     * to save the data(string) into a json file
     *
     * @param tag indicate what kind of record it is
     * @param timeStamp a timestamp
     */
    private void SaveToJsonFile(String tag, String timeStamp) {

        File directoryForYourApp;
        // get the directory in the internal storage for your app
//        directoryForYourApp=getFilesDir();

        // first to create a directory/folder in the external storage for the app
        directoryForYourApp = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
        if (!directoryForYourApp.exists()) {
            directoryForYourApp.mkdir();
        }

        // to create a folder to load the JSON files
        File JSONData = new File(directoryForYourApp.getAbsolutePath(), "JSONData");
        if (!JSONData.exists()) {
            JSONData.mkdir();
        }

        // create the json file
        File jsonFile = new File(JSONData, timeStamp);
        Log.d("MyAPP pathOfData: ", jsonFile.getAbsolutePath());

        // to create an instance of FileWriter to write data into a json file
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // to write data in JSON format
        try {
            assert fileWriter != null;
            fileWriter.write("{" + "\"" + tag + "\"" + ":" + "\"" + timeStamp + "\"" + "}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


