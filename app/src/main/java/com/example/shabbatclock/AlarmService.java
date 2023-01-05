package com.example.shabbatclock;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmService extends Service {
    MediaPlayer player;
    String timeToStop;
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    String timeToAlarm;
    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);

        }
        @Override
        public void handleMessage(Message msg) {

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
        }
    }

    @Override
    public void onCreate() {

        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        Toast.makeText(this, "clock set", Toast.LENGTH_SHORT).show();
        HandlerThread thread = new HandlerThread("ServiceStartArguments");
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player =  MediaPlayer.create(getBaseContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
        Toast.makeText(this, "clock set", Toast.LENGTH_SHORT).show();
        timeToAlarm = intent.getStringExtra("TIME");
        timeToStop = intent.getStringExtra("STOP");
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        AsyncTask.execute(new Runnable() {

        boolean hadRun = false;

            public void run() {

                String str;
                while (true) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                    String strDate = sdf.format(c.getTime());
                    String strTime = time.format(c.getTime());
                    Log.d("Date", "DATE : " + strDate);
                    Log.d("Date", "DATE : " + strTime);


                    str = strDate + "," + strTime;

                    Log.d("running", str + " " + timeToAlarm);
                    Log.d("isEquel", String.valueOf(str.equals(timeToAlarm)));
                    Log.d("hadRun", String.valueOf(hadRun));
                if (str.equals(timeToAlarm) && !hadRun) {
                    player = MediaPlayer.create(getBaseContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
                    player.setLooping(true);
                    player.start();
                    hadRun = true;
                }
                    else if(str.equals(timeToStop)) {
                        try{
                        player.stop();
                        hadRun = false;}
                        catch (Exception e){

                        }
                        hadRun = false;
                        return;
                    }

                    try {
                        Thread.sleep(5000);
                    }
                    catch (Exception e){

                    }
                }
            }
        });

        return START_STICKY;
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job

    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {

    }


}


