package com.nidhi.bounded;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nidhi.bounded.services.BoundedService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = MainActivity.this;

    private Button btPlay, btStop;

    BoundedService boundedService;

    private boolean isBounded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btPlay = (Button)findViewById(R.id.button_play);
        btPlay.setOnClickListener(this);

        btStop = (Button)findViewById(R.id.button_stop);
        btStop.setOnClickListener(this);


                     



    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(context, BoundedService.class);
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBounded){
            unbindService(serviceConnection);
            isBounded = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_play:
                if(isBounded){
                    boundedService.startBoundedService();
                }
                break;

            case R.id.button_stop:

              /*  Intent intent = new Intent(context, BoundedService.class);
                stopService(intent);*/

                if(isBounded){
                    unbindService(serviceConnection);
                    isBounded = false;
                }

                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            isBounded = true;
            BoundedService.MyBinder myBinder = (BoundedService.MyBinder) service;
            boundedService = myBinder.getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            isBounded = false;

        }
    };
}
