package com.example.theengineer.lab_flashlight;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

/**
 * Created by Tomislav Romic on 09.11.2017.
 */
//This class has all methods to interact with the GUI
public class GUI_creation {

    public Activity activity;
    View LayoutOverlayWindow;

    public GUI_creation(Activity _activity){
        activity =_activity;
        LayoutOverlayWindow = activity.getLayoutInflater().inflate(R.layout.startup_menu,null);
    }


    /**changes flashlight panel color
     *
     * @param color: holds the value for the desired color
     */
    public void show_flashlightColor(int color){

        Log.i("changingColorOnScreen", "show_flashlightColor: ");
        View flashlightPanel = activity.findViewById(R.id.flashlightPanel);
        flashlightPanel.setBackgroundColor(color);
    }

    /**
     * enables or disables the description on the flashlight panel
     * @param desOn: description on || description off
     */
    public void show_description(boolean desOn){

        Log.i("changingDescription","show_description: ");

        if(desOn){
            TextView test =activity.findViewById(R.id.flashlightPanel);
            test.setText(R.string.UserDescription_MainLayer);
        }
        else {
            TextView test =activity.findViewById(R.id.flashlightPanel);
            test.setText("");
        }
    }

    /**
     * changes the mode of the application.
     * @param fullscreen : fullscreen on || fullscreen off
     */
    public void show_fullscreen(boolean fullscreen) {

        Log.i("changingFullscreenMode", "show_fullscreen: ");

        View flashlightPanel = activity.findViewById(R.id.flashlightPanel);
        ViewGroup.MarginLayoutParams mlp =
                (ViewGroup.MarginLayoutParams) flashlightPanel.getLayoutParams();

        if(fullscreen){
            mlp.bottomMargin=0;
            flashlightPanel.setLayoutParams(mlp);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //((AppCompatActivity)activity).getSupportActionBar().hide();
            flashlightPanel.bringToFront();
        }
        else{
            final float scale = activity.getResources().getDisplayMetrics().density;
            int padding_50dp= (int) (50* scale + 0.5f);
            mlp.bottomMargin=padding_50dp;
            flashlightPanel.setLayoutParams(mlp);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //((AppCompatActivity)activity).getSupportActionBar().show();
        }
    }

    /**
     * Lets previouse selected menu buttons disappear
     * @param menu: reference to the options menu that is used
     * @param mode: disables or enables options menu items
     */
    public void show_optionsMenu(Menu menu,int mode){
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        switch(mode ){
            case 0:
                menu.findItem(R.id.MenuButtonNextStartFullscreen).setEnabled(true);
                menu.findItem(R.id.MenuButtonNextStartNoFullscreen).setEnabled(false);
                break;
            case 1:
                menu.findItem(R.id.MenuButtonNextStartFullscreen).setEnabled(false);
                menu.findItem(R.id.MenuButtonNextStartNoFullscreen).setEnabled(true);
                break;
            default:
                break;
        }
    }

    /**
     * builds a alert window and shows it to the user
     * @param listener: reference to the used listener that handles the desired onClick event
     */
    public void show_AlertWindow(DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Startup Button Visibility");
        builder.setNegativeButton("Invisible", listener).setPositiveButton("Visible",listener).setNeutralButton("Cancel",listener);

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    /**
     * Shows startup window (startup_menu.xml)
     * @param listener: connected listener
     * @param show: enables view
     */
    public void show_StartupDialogWindows(View.OnClickListener listener, boolean show){
        if(show){
            try {
                activity.addContentView(LayoutOverlayWindow, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                View startupButtonSetColor = activity.findViewById(R.id.button4setColor);
                View startupButtonCancel = activity.findViewById(R.id.button4cancel);
                startupButtonSetColor.setOnClickListener(listener);
                startupButtonCancel.setOnClickListener(listener);
            }
            catch(Exception e){
                Log.w("Error: StartupWindow", "show_StartupDialogWindows: Can't create startup window!" );
            }
        }
        else{
            LayoutOverlayWindow.setVisibility(View.GONE);
        }
    }

    /**
     * accesses the radio button widget and gets the selected radio button
     * @return id of the color that was chosen by the user
     */
    public int getSelectedRadioButton(){

        View display = activity.findViewById(R.id.radioGroup4buttons);
        RadioGroup myRadioGroup = (RadioGroup)display;
        int radioGroupID = myRadioGroup.getCheckedRadioButtonId();
        RadioButton myCheckedButton = display.findViewById(radioGroupID);
        int index = myRadioGroup.indexOfChild(myCheckedButton);

        switch(index){
            case 0:
                return Color.WHITE;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.RED;
            default:
                break;
        }
        return index;
    }

    /**
     *
     * @param listener
     */
    public void connectOnClickListener(GUI_ClickEvents listener){
        View myflashlight = activity.findViewById(R.id.flashlightPanel);
        View buttonWhite = activity.findViewById(R.id.button4white);
        View buttonBlue = activity.findViewById(R.id.button4Blue);
        View buttonRed = activity.findViewById(R.id.button4Red);
        View buttonYellow = activity.findViewById(R.id.button4Yellow);
        View buttonGreen=activity.findViewById(R.id.button4Green);
        View buttonSOS = activity.findViewById(R.id.buttonSOS);

        myflashlight.setOnClickListener(listener);
        buttonWhite.setOnClickListener(listener);
        buttonBlue.setOnClickListener(listener);
        buttonRed.setOnClickListener(listener);
        buttonYellow.setOnClickListener(listener);
        buttonGreen.setOnClickListener(listener);
        buttonSOS.setOnClickListener(listener);
    }

    /**
     *
     * @param toastText
     */
    public void createToast(String toastText){
        Context context = activity.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    /**
     *
     * @param text
     */
    public void showCenteredTextOnTextview(String text){
        TextView myflashlight = activity.findViewById(R.id.flashlightPanel);
        myflashlight.setGravity(Gravity.CENTER);
        myflashlight.setText(text);
    }
}
