package com.example.shabbatclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CalendarView calendarView;
    Button confirm;
    static String date;
    String strDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        strDate = sdf.format(c.getTime());
        String strTime = time.format(c.getTime());
        Log.d("Date","DATE : " + strDate);
        Log.d("Date","DATE : " + strTime);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String  curDate = String.valueOf(dayOfMonth);
                String  Year = String.valueOf(year);

                String  Month = String.valueOf(+1);
                if(curDate.length() == 0){
                    curDate = "00";
                }
                if(curDate.length() == 1){
                    curDate = "0"+curDate;
                }
                if(Month.length() == 1){
                    Month = "0"+Month;
                }

                date =  Year+"/"+Month+"/"+curDate;

                Log.d("date",Year+"/"+Month+"/"+curDate);
            }
        });
        confirm = (Button) findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Log.d("date", String.valueOf(date));
        if(date == null){
            date = strDate;
        }
        Intent intent = new Intent(this, SelectHour.class);
        intent.putExtra("DATE", date);
        startActivity(intent);
    }
}