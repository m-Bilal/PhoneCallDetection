package com.bilal.phonecalldetection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IncomingCallInfoActivity extends AppCompatActivity {

    private final String TAG = "IncomingCallInfo";

    TextView textViewPhoneNumber;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call_info);

        Log.i(TAG, "Activity started");

        textViewPhoneNumber = (TextView) findViewById(R.id.textview_incoming_call_number_IncomingCallInfoActivity);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_IncomingCallInfoActivity);

        setWindowParams();
    }

    public void setWindowParams() {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.dimAmount = 0;
        wlp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        getWindow().setAttributes(wlp);
    }
}
