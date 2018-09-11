package com.example.theengineer.lab_flashlight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Intent.ACTION_BATTERY_LOW;
import static android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY;
import static android.os.BatteryManager.EXTRA_LEVEL;

/**
 * * Created by Tomislav Romic on 13.12.2017.
 */

public class GUI_BroadcastReceiver extends BroadcastReceiver{

    GUI_settings mySettings;

    public GUI_BroadcastReceiver(GUI_settings mySettings){
       this.mySettings=mySettings;
    }
        @Override
        public void onReceive(Context context, Intent intent) {

        //Check battery level and turn on/off power saving mode
            int status = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            if(status <=15){
                mySettings.enablePowerSaveMode(true);

            }else{
                mySettings.enablePowerSaveMode(false);
            }

        }
}
