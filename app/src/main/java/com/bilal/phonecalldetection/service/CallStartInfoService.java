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

    public static final String INTENT_EXTRA_PHONE_NUMBER = "number";

    private WindowManager mWindowManager;
    private View mCallPopupView;
    private TextView mPhoneNumberTextView;


    public CallStartInfoService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        //Inflate the popup layout
        mCallPopupView = LayoutInflater.from(this).inflate(R.layout.serivce_call_start_popup, null);
        // Reference textview in popup layout
        mPhoneNumberTextView = (TextView) mCallPopupView.findViewById(R.id.textview_incoming_call_number_CallStartIntentService);

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                // Allow the activity behind the popup to receive touch events
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                // The following flags allow the popup to appear even when the device is locked
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                // Making the remaining of the screen transparent
                PixelFormat.TRANSLUCENT);

        //Specify the popup position
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mCallPopupView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Called after onCreate
        // Overridden to get access to intent extras
        Log.i(TAG, "onStartCommand");

        if (intent.getExtras() != null) {
            mPhoneNumberTextView.setText(intent.getExtras().getString(INTENT_EXTRA_PHONE_NUMBER));
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
        if (mCallPopupView != null) {
            mWindowManager.removeView(mCallPopupView);
        }
    }
}
