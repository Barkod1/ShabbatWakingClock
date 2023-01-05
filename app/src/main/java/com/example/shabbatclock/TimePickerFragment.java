package com.example.shabbatclock;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment

        implements TimePickerDialog.OnTimeSetListener {
    public static String timeToRun;

    static {
            timeToRun = SelectHour.timeToRun.getText().toString();

    }

    static String time;
    static Intent serviceIntent;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d("time", String.valueOf(hourOfDay)+":" + minute );
        String  hour = String.valueOf(hourOfDay);
        String  min = String.valueOf(minute);
        if(hour.length() == 0){
            hour = "00";
        }
        if(hour.length() == 1){
            hour = "0"+hour;
        }
        if(min.length() == 0){
            min = "00";
        }
        if(min.length() == 1){
            min = "0"+min;
        }
    time = hour +":"+ min;
        String myTime = time;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = df.parse(myTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(timeToRun == null) timeToRun ="1";
        try{
            Integer.valueOf(timeToRun);
        }catch (Exception e){
            Toast myToast = Toast.makeText(this.getContext(), "please choose a real number. default alarm set to 1 minute long", Toast.LENGTH_LONG);
            myToast.show();
            timeToRun="1";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        Log.d("timetorun", timeToRun);
        cal.add(Calendar.MINUTE, Integer.valueOf(timeToRun));
        String timeToStop = df.format(cal.getTime());
        serviceIntent = new Intent(getActivity(),AlarmService.class);

        serviceIntent.putExtra("TIME", MainActivity.date + "," + time);
        serviceIntent.putExtra("STOP", MainActivity.date + "," +timeToStop);
       getActivity().startService(serviceIntent);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);

    }
}