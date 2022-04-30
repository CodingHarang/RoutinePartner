package com.astudio.routinepartner;



import android.view.View;
import android.widget.NumberPicker;

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
            NP[i].setMinValue(0);
            NP[i].setBackgroundColor(0xFF000000);
            NP[i].setTextColor(0xFFFFFFFF);
        }
        for(int i = 4; i < 6; i++) {
            NP[i].setTextSize(40);
            NP[i].setMaxValue(59);
            NP[i].setMinValue(0);
            NP[i].setBackgroundColor(0xFF000000);
            NP[i].setTextColor(0xFFFFFFFF);
        }
        for(int i = 0; i < 2; i++) {
            NP[i].setTextSize(30);
            NP[i].setMaxValue(1);
            NP[i].setMinValue(0);
            NP[i].setBackgroundColor(0xFF000000);
            NP[i].setTextColor(0xFFFFFFFF);
            NP[i].setDisplayedValues(new String[] {"AM", "PM"});
        }
    }
}