package com.bilal.phonecalldetection.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bilal.phonecalldetection.R;

public class CallStartInfoService extends Service {

    private static final String TAG = "CallStartInfoService";

    private WindowManager mWindowManager;
    private View mCallPopupView;

    private TextView mPhoneNumberTextView;


    public CallStartInfoService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate");

        //Inflate the chat head layout we created
        mCallPopupView = LayoutInflater.from(this).inflate(R.layout.serivce_call_start_popup, null);

        mPhoneNumberTextView = (TextView) mCallPopupView.findViewById(R.id.textview_incoming_call_number_CallStartIntentService);


        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                PixelFormat.TRANSLUCENT);

        //Specify the chat head position
        //Initially view will be added to top-left corner
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mCallPopupView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        if (intent.getExtras() != null) {
            mPhoneNumberTextView.setText(intent.getExtras().getString("number"));
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mCallPopupView != null) mWindowManager.removeView(mCallPopupView);
    }
}
