package com.example.theengineer.lab_flashlight;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    GUI_settings mySettings;
    GUI_ClickEvents myClickEvents;
    GUI_BroadcastReceiver myBroadcasrReceiver;
    SharedPreferences myPreferences;

    final int StartupWithOutButton=0;
    final int StartupWithButton=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loads preferences from default file
        myPreferences = getPreferences(0);
        //stores loaded preferences in variables
        int preferencesForMenu =myPreferences.getInt("SelectedMenuButton",0);
        boolean preferencesColdStart = myPreferences.getBoolean("ColdStart",true);

        //GUI_settings instance is created with considering stored preferences and the temporary stores Bundle
        mySettings = new GUI_settings(this,savedInstanceState,preferencesForMenu,preferencesColdStart);
        //GUI_ClickEvents instance is created to enable all onClick listeners that were used in this application
        myClickEvents =new GUI_ClickEvents(mySettings);
        myBroadcasrReceiver = new GUI_BroadcastReceiver(mySettings);
        IntentFilter bcFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(myBroadcasrReceiver, bcFilter);

        //Connects to the current running GUI_ClickEvents instance
        mySettings.setOnClickListener(myClickEvents);
        //used at this moment to show the default screen
        mySettings.showOnScreen();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //pauses SOS signal, so it can continue after app rotation
        mySettings.pauseSosSignal();

        //stores all current settings in a Bundle (e.g. used after app rotation)
        mySettings.storeData(outState);
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putBoolean("ColdStart",mySettings.startupSettingsApplied());
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //enables Options Menu
        mySettings.enableoptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //stores behavior that was chose by the user in the options menu
        SharedPreferences.Editor editor = myPreferences.edit();

        switch(item.getItemId()) {
            case R.id.MenuButtonNextStartFullscreen:
                editor.putInt("SelectedMenuButton",StartupWithOutButton);
                break;
            case R.id.MenuButtonNextStartNoFullscreen:
                editor.putInt("SelectedMenuButton",StartupWithButton);
                break;
            default:
                break;
        }

        editor.apply();
        return super.onOptionsItemSelected(item);
    }
}
