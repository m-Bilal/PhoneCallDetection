package com.bilal.phonecalldetection.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilal.phonecalldetection.MainActivity;
import com.bilal.phonecalldetection.R;

public class CallStartInfoService extends Service {

    private static final String TAG = "CallStartInfoService";

    public static final String INTENT_EXTRA_PHONE_NUMBER = "number";
    public static final String INTENT_EXTRA_CALL_TYPE = "call_type";

    private WindowManager mWindowManager;
    private View mCallPopupView;
    private TextView mPhoneNumberTextView;
    private TextView mCallTypeTextView;
    private LinearLayout mLinearLayout;


    public CallStartInfoService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        //Inflate the popup layout
        mCallPopupView = LayoutInflater.from(this).inflate(R.layout.serivce_call_start_popup, null);
        // Reference views in popup layout
        mPhoneNumberTextView = mCallPopupView.findViewById(R.id.textview_call_number_CallStartIntentService);
        mCallTypeTextView = mCallPopupView.findViewById(R.id.textview_call_type_CallStartIntentService);
        mLinearLayout = mCallPopupView.findViewById(R.id.linear_layout_CallStartInfoService);

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
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


        mLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //As we implemented on touch listener with ACTION_MOVE,
                        //we have to check if the previous action was ACTION_DOWN
                        //to identify if the user clicked the view or not.
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            //Open the chat conversation click.
                            Intent intent = new Intent(CallStartInfoService.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            //close the service and remove the chat heads
                            stopSelf();
                        }
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mCallPopupView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Called after onCreate
        // Overridden to get access to intent extras
        Log.i(TAG, "onStartCommand");

        if (intent.getExtras() != null) {
            mPhoneNumberTextView.setText(intent.getExtras().getString(INTENT_EXTRA_PHONE_NUMBER));
            mCallTypeTextView.setText(intent.getExtras().getString(INTENT_EXTRA_CALL_TYPE));
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
