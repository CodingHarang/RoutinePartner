package com.astudio.routinepartner;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class YJS {

    public static void numPickerSetting(View V, NumberPicker[] NP) {
        NP[0] = V.findViewById(R.id.numPickerAMPMS);
        NP[1] = V.findViewById(R.id.numPickerAMPME);
        NP[2] = V.findViewById(R.id.numPickerHourS);
        NP[3] = V.findViewById(R.id.numPickerHourE);
        NP[4] = V.findViewById(R.id.numPickerMinuteS);
        NP[5] = V.findViewById(R.id.numPickerMinuteE);

        for(int i = 2; i < 4; i++) {
            NP[i].setTextSize(40);
            NP[i].setMaxValue(12);
            NP[i].setMinValue(1);
            NP[i].setTextColor(0xFF333333);
            NP[i].setTextSize(60);
        }
        for(int i = 4; i < 6; i++) {
            NP[i].setTextSize(40);
            NP[i].setMaxValue(59);
            NP[i].setMinValue(0);
            NP[i].setTextColor(0xFF333333);
            NP[i].setTextSize(60);
        }
        for(int i = 0; i < 2; i++) {
            NP[i].setTextSize(30);
            NP[i].setMaxValue(1);
            NP[i].setMinValue(0);
            NP[i].setTextColor(0xFF333333);
            NP[i].setDisplayedValues(new String[] {"AM", "PM"});
            NP[i].setTextSize(60);
        }
    }
}