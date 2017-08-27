package com.bilal.phonecalldetection;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Bilal on 20-Aug-17.
 */

public class CallReceiver extends PhonecallReceiver {

    public static final String TAG = "CallReceiver";

    @Override
    protected void onIncomingCallReceived(final Context ctx, final String number, Date start) {
        Log.d(TAG, "Incoming Call Received, Number : " + number);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ctx, IncomingCallInfoActivity.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        }, 2000);
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
        Log.d(TAG, "Incoming Call Answered, Number : " + number);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG, "Incoming Call Ended, Number : " + number);

        Toast.makeText(ctx, "Incoming Call from " + number, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d(TAG, "Outgoing Call Started, Number : " + number);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG, "Outgoing Call Ended, Number : " + number);

        Toast.makeText(ctx, "Outgoing Call to " + number, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d(TAG, "Missed Call, Number : " + number);

        Toast.makeText(ctx, "Missed Call from " + number, Toast.LENGTH_SHORT).show();
    }

}
