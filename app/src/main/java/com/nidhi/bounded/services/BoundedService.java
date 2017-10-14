package com.nidhi.bounded.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.nidhi.bounded.R;

/**
 * Created by nidhi on 6/5/2017.
 */

public class BoundedService extends Service {

    private Context context = BoundedService.this;

    private MediaPlayer mediaPlayer;

    IBinder iBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(context, R.raw.mysong);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
    }


    public void startBoundedService(){
        mediaPlayer.start();
    }


    public class MyBinder extends Binder{

        public BoundedService getService(){
            return BoundedService.this;
        }

    }

}
