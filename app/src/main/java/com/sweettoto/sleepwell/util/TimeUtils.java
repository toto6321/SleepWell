package com.sweettoto.sleepwell.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Carlos on 2018/2/11.
 */

public class TimeUtils {

    public static String getTimeStamp() {
        Date currentTime = Calendar.getInstance().getTime();
        //to format the output string of timestamp
        SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return formator.format(currentTime);
    }
}
