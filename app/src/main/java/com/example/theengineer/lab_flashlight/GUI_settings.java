package com.example.theengineer.lab_flashlight;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

import static java.lang.Thread.sleep;

/**
 * Created by Tomislav Romic on 09.11.2017.
 */

//provides all settings and methods that are controlling the gui
public class GUI_settings {
    enum SelectableColors {
        COLOR_WHITE,
        COLOR_BLUE,
        COLOR_RED,
        COLOR_YELLOW,
        COLOR_GREEN,
        COLOR_BLACK

    }

    GUI_creation m_myCreation;
    GUI_ClickEvents m_myClickEvents;


    private int m_selectedColor = Color.RED;
    private boolean m_fullscreen = false;
    private boolean m_textdescription = true;
    private boolean m_toggleVar = false;
    private boolean m_ColdStart = true;
    private int m_selectedMenuItem = 0;
    private boolean m_showStartupWindow = true;
    private boolean m_SosStarted =false;
    private boolean m_powerSaveMode=false;

    private int m_indexForSosSignal =0;

    //Keys
    final int StartupWithOutButton = 0;
    final int StartupWithButton = 1;

    private AsyncTask SosTask =null;

    /**
     * Constructor: Creates GUI_creation instance, to interact with the GUI
     *              Restores persistent variables
     *              Restores saved instance
     *
     * @param savedInstance     used to restore instance data e.g. at app rotation
     * @param setPreferences    used to restore persistent data
     * @param coldStart         distinguishes different behaviors at startup
     */
    public GUI_settings(MainActivity instance, Bundle savedInstance, int setPreferences, boolean coldStart) {
        m_myCreation = new GUI_creation(instance);

        m_ColdStart = coldStart;

        setAndApplySavedPreferences(setPreferences);

        if (savedInstance != null) {
            Log.i("RestoringData", "GUI_settings: Restoring Data... ");
            restoreData(savedInstance);
        }

    }
    /**
     * Stores members of class in a Bundle
     *
     * @param savedInstance used to store all members of class GUI_settings (e.g. app rotation)
     */
    public void storeData(Bundle savedInstance) {
        if (savedInstance != null) {
            boolean[] storeBoolArr = new boolean[5];
            storeBoolArr[0] = m_fullscreen;
            storeBoolArr[1] = m_textdescription;
            storeBoolArr[2] = m_toggleVar;
            storeBoolArr[3] = m_showStartupWindow;
            storeBoolArr[4] = m_SosStarted;

            savedInstance.putBooleanArray("GUI_SETTINGS: ALL BOOLEAN ATTRIBUTES", storeBoolArr);

            int[] storeInt = new int[3];
            storeInt[0] = m_selectedColor;
            storeInt[1] = m_selectedMenuItem;
            storeInt[2] = m_indexForSosSignal;
            savedInstance.putIntArray("GUI_SETTINGS: ALL INTEGER ATTRIBUTES", storeInt);
        } else {
            Log.e("Error: Storing!", "storeData: Called w/o initialized storage");
        }

    }
    /**
     * Restores members of class from Bundle
     *
     * @param savedInstance used to restore all members of class GUI_settings (e.g. after app rotation)
     */
    public void restoreData(Bundle savedInstance) {
        if (savedInstance != null) {

            boolean[] restoredBool;
            restoredBool = savedInstance.getBooleanArray("GUI_SETTINGS: ALL BOOLEAN ATTRIBUTES");
            m_fullscreen = restoredBool[0];
            m_textdescription = restoredBool[1];
            m_toggleVar = restoredBool[2];
            m_showStartupWindow = restoredBool[3];
            m_SosStarted=restoredBool[4];

            int[] restoredInt;
            restoredInt = savedInstance.getIntArray("GUI_SETTINGS: ALL INTEGER ATTRIBUTES");
            m_selectedColor = restoredInt[0];
            m_selectedMenuItem = restoredInt[1];
            m_indexForSosSignal=restoredInt[2];



            if(m_SosStarted==true){
                startSosSignal();
            }

        } else {
            Log.e("Error: Restore!", "restoreData: Called w/o initialized storage");
        }


    }

    /**
     * Toggles from fullscreen mode to default mode
     */
    public void toggleSettings() {
        Log.i("ToggleCall", "toggleSettings: ");
        m_fullscreen = !m_fullscreen;
        m_textdescription = false;
        m_toggleVar = !m_toggleVar;
    }

    /**
     * sets member that is used to enable/disable fullscreen mode (m_fullscreen)
     *
     * @param  fullscreen
     * @return //
     * @throws //
     */
    public void fullscreenMode(boolean fullscreen) {
        m_fullscreen = fullscreen;
    }
    /**
     * sets member that is used to change the color of the flashlight panel(m_selectedColor)
     *
     * @param  colors: the desired color for the flashlight panel
     * @return returns true if color could be mapped to the color variable
     */
    public boolean changeColor(SelectableColors colors) {
        switch (colors) {
            case COLOR_WHITE:
                m_selectedColor = Color.WHITE;
                break;
            case COLOR_BLUE:
                m_selectedColor = Color.BLUE;
                break;
            case COLOR_RED:
                m_selectedColor = Color.RED;
                break;
            case COLOR_GREEN:
                m_selectedColor = Color.GREEN;
                break;
            case COLOR_YELLOW:
                m_selectedColor = Color.YELLOW;
                break;
            case COLOR_BLACK:
                m_selectedColor = Color.BLACK;
                break;
            default:
                //UPPS: Something went wrong
                Log.e("DefaultError", "changeColor: Switch Error");
                return false;
        }
        return true;
    }
    /**
     * sets member that is used to show the description on flashlight panel (m_textdescription)
     */
    public void enableDescription(boolean desOn) {
        m_textdescription = desOn;
    }

    /**
     * sets member that is used to show options menu(m_selectedMenuItem)
     */
    public void enableoptionsMenu(Menu menu) {
        m_myCreation.show_optionsMenu(menu, m_selectedMenuItem);
    }

    public void setAndApplySavedPreferences(int myPreferences) {

        switch (myPreferences) {
            case StartupWithButton:
                m_selectedMenuItem = 0;
                m_fullscreen = false;
                break;
            case StartupWithOutButton:
                m_selectedMenuItem = 1;
                m_fullscreen = true;
                break;
            default:
                break;
        }

    }
    /**
     * connects GUI_creation instance with the running GUI_ClickEvents instance
     * @param  listener: currently active onClick_listener that handles all click events
     */
    public void setOnClickListener(GUI_ClickEvents listener) {
        m_myClickEvents = listener;
        m_myCreation.connectOnClickListener(listener);
    }

    /**
     * main method to apply all settings that were stored as members in this class
     */
    public void showOnScreen() {
        if (m_ColdStart) {
            m_myCreation.show_AlertWindow(m_myClickEvents);
        } else {
            m_myCreation.show_description(m_textdescription);
            m_myCreation.show_flashlightColor(m_selectedColor);
            m_myCreation.show_fullscreen(m_fullscreen);

            m_myCreation.show_StartupDialogWindows(m_myClickEvents, m_showStartupWindow);
        }
    }

    /**
     * sets the member that is used to change the color of the flashlight panel. The color is chosen via
     * the selected radio buttons, that were shown at the beginning of the app
     */
    public void setFlashlightColorFromRadioButtons() {
        m_selectedColor = m_myCreation.getSelectedRadioButton();

    }

    /**
     * sets the member that is used to distinguish methods from a cold start (m_ColdStart)
     */
    public void disableColdStartMethods() {
        m_ColdStart = false;
    }

    /**
     * sets member that is used to show the StartUp window that is created in the GUI-creation instance(m_showStartupWindow)
     */
    public void closeStartupWindow() {
        m_showStartupWindow = false;
    }


    public void startSosSignal() {

        if(SosTask == null) {
            SosTask = new SOSSignal().execute();
            m_SosStarted=true;
        }else{

        }
    }

    /**
     * used to stop a running SOS signal task
     */
    public void stopSosSignal() {
        if(SosTask != null){
        SosTask.cancel(true);
        SosTask=null;
        m_myCreation.createToast("Stopped SOS-Signal.");
        m_SosStarted=false;
        }
    }

    /**
     * used to pause a running SOS signal task (e.g. after a app rotation)
     */
    public void pauseSosSignal() {
        if(SosTask != null){
            SosTask.cancel(true);
            SosTask=null;
            m_SosStarted=true;
        }
    }
    /**
     *
     */
    public void enablePowerSaveMode(boolean pwOn){m_powerSaveMode=pwOn;}
    /**
     * checks if the currently acitivity is started cold
     * @return boolean m_ColdStart
     */
    public boolean startupSettingsApplied() {
        return m_ColdStart;
    }

    /**
     * Used to communicate with the running SOS async task. Changes the color from white to yellow.
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.i(""+msg.arg1, "handleMessage: ");

            switch (msg.what) {
                case 0:
                    changeColor(SelectableColors.COLOR_WHITE);
                    showOnScreen();
                    break;
                case 1:
                    changeColor(SelectableColors.COLOR_YELLOW);
                    showOnScreen();
                    break;
                case 2:
                    changeColor(SelectableColors.COLOR_BLACK);
                    showOnScreen();
                    break;
            }

            return false;
        }
    });

    /**
     * Async task sends via handler.sendEmptyMessage commands to the handler, so the handler changes
     * the screen color from one color to another to a desired frequency. In this case it is used to
     * send a SOS signal in morse code.
     */

    private class SOSSignal extends AsyncTask {
        public final long dotTime =500;
        public final long dashTime = 800;
        public final long pauseTime = 200;
        public final long powerSaveTime=2000;

        long[] SosSeq= new long[] {dotTime, dotTime, dotTime
                                  ,dashTime, dashTime, dashTime
                                  ,dotTime, dotTime, dotTime};

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            while (!isCancelled()) {
                for(m_indexForSosSignal=0; m_indexForSosSignal< SosSeq.length;m_indexForSosSignal++) {
                    if (!isCancelled()) {
                        handler.sendEmptyMessage(0);
                        try {
                            sleep(pauseTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return 0;
                    }
                    if(!isCancelled()) {
                        handler.sendEmptyMessage(1);
                        try {
                            sleep(SosSeq[m_indexForSosSignal]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return 0;
                    }
                }
                if(!isCancelled() && m_powerSaveMode){
                    handler.sendEmptyMessage(2);
                    try {
                        sleep(powerSaveTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return 1;
        }
    }
}
