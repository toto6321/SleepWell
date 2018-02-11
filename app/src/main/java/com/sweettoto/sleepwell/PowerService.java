package com.sweettoto.sleepwell;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sweettoto.sleepwell.util.TimeUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Carlos on 2018/2/11.
 */

public class PowerService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mBroadcastReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private BroadcastReceiver mBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                SaveToJsonFile("off", TimeUtils.getTimeStamp());
            }else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
                SaveToJsonFile("on", TimeUtils.getTimeStamp());
            }
        }
    };

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
