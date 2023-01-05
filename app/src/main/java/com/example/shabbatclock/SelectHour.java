package com.example.shabbatclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

public class SelectHour extends AppCompatActivity  {
    public static EditText timeToRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hour);
        timeToRun = findViewById(R.id.editTextNumber);

    }

    public void showTimePickerDialog(View v) {

        String time = TimePickerFragment.time;
        Bundle extras = getIntent().getExtras();
        String date = null;
        if (extras != null) {
            date = extras.getString("DATE");

            //The key argument here must match that used in the other activity
        }
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");


    }
}