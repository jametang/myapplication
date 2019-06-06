package com.example.admin.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class RemindService extends Service implements MediaPlayer.OnPreparedListener{
    private Timer mTimer;
    private NotificationChannel notificationChannelTips;
    private NotificationChannel notificationChannelTimesShow;
    private final static int NOTIFICATION_ID = 1001;
    private final static int NOTIFICATION_PERIOD = 60 * 20;
    private Uri defaultURI;
    private MediaPlayer mediaPlayer;
    private MyInterface myInterface;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void startTask(){
        Log.e("TANGLI","开启前台服务");
        defaultURI  = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gaoguaimp3 );
        mediaPlayer = MediaPlayer.create(getBaseContext(),defaultURI);
        mediaPlayer.setOnPreparedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannelTips = new NotificationChannel("tips","提醒", NotificationManager.IMPORTANCE_HIGH);
            notificationChannelTimesShow = new NotificationChannel("timesShow","计次",NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannelTips);
            manager.createNotificationChannel(notificationChannelTimesShow);
        }
        startTimer();
    }

    private int i = 0;
    private void startTimer() {
        if(mTimer == null){
            mTimer = new Timer();

        }
        mTimer.schedule(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                if(i == NOTIFICATION_PERIOD){
                    Log.e("TANGLI","端正坐姿");
                    showNotification("端正坐姿",1);
                    startMedia();
                    i = 0;
                }else{
                    showNotification((NOTIFICATION_PERIOD - i)+"",0);
                    if(i % 20 == 0){
                       stopMedia();
                    }
                }
                myInterface.refreshUI(i);
                i++;
            }
        },1000,1000);
    }

    private void stopMedia() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    private void startMedia() {
        mediaPlayer.prepareAsync();
    }

    @Override
    public void onDestroy() {
        Log.e("TANGLI","停止前台服务");
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String tips,int type){
        Notification n = new Notification.Builder(getApplicationContext())
                .setContentTitle("Personal remind Notification")
                .setContentText(tips)
                .setSmallIcon(android.R.drawable.ic_media_pause)
                .setDefaults(Notification.DEFAULT_ALL)
                .setChannelId((type == 0) ?  "timesShow" : "tips")
                .build();
        startForeground(NOTIFICATION_ID,n);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public void setMyInterface(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    public class MyBinder extends Binder {
        public RemindService getService(){
            return RemindService.this;
        }
    }
}
