package com.example.theengineer.lab_flashlight;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

/**
 * Created by Tomislav Romic on 10.11.2017.
 */
//This class provides all callbacks that were used in this application

public class GUI_ClickEvents implements View.OnClickListener, DialogInterface.OnClickListener{

    GUI_settings mySettings;

    public GUI_ClickEvents(GUI_settings mySettings){
        this.mySettings=mySettings;
    }

    @Override
    public void onClick(View clickedWidget) {

        switch (clickedWidget.getId()) {
            case R.id.flashlightPanel:
                mySettings.stopSosSignal();
                mySettings.toggleSettings();
                break;
            case R.id.button4Red:
                mySettings.stopSosSignal();
                mySettings.changeColor(GUI_settings.SelectableColors.COLOR_RED);
                break;
            case R.id.button4Blue:
                mySettings.stopSosSignal();
                mySettings.changeColor(GUI_settings.SelectableColors.COLOR_BLUE);
                break;
            case R.id.button4white:
                mySettings.stopSosSignal();
                mySettings.changeColor(GUI_settings.SelectableColors.COLOR_WHITE);
                break;
            case R.id.button4Yellow:
                mySettings.stopSosSignal();
                mySettings.changeColor(GUI_settings.SelectableColors.COLOR_YELLOW);
                break;
            case R.id.button4Green:
                mySettings.stopSosSignal();
                mySettings.changeColor(GUI_settings.SelectableColors.COLOR_GREEN);
                break;
            case R.id.button4setColor:
                mySettings.setFlashlightColorFromRadioButtons();
                mySettings.closeStartupWindow();
                break;
            case R.id.button4cancel:
                mySettings.closeStartupWindow();
                break;
            case R.id.buttonSOS:
                mySettings.startSosSignal();
                break;
            default:
                break;
        }
            mySettings.showOnScreen();

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                mySettings.fullscreenMode(false);
                mySettings.disableColdStartMethods();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                mySettings.fullscreenMode(true);
                mySettings.disableColdStartMethods();
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                mySettings.disableColdStartMethods();

                break;
            default:
                break;
        }
        mySettings.showOnScreen();

    }


}
