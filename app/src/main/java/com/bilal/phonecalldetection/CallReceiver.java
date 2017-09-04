package com.bilal.phonecalldetection;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.bilal.phonecalldetection.service.CallStartInfoService;

import java.util.Date;

/**
 * Created by Bilal on 20-Aug-17.
 */

public class CallReceiver extends PhonecallReceiver {

    public static final String TAG = "CallReceiver";

    @Override
    protected void onIncomingCallReceived(final Context ctx, final String number, Date start) {
        Log.d(TAG, "Incoming Call Received, Number : " + number);

        // Start the service
        Intent intent = new Intent(ctx, CallStartInfoService.class);
        intent.putExtra(CallStartInfoService.INTENT_EXTRA_PHONE_NUMBER, number);
        ctx.startService(intent);
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
        Log.d(TAG, "Incoming Call Answered, Number : " + number);
        try {
            ctx.stopService(new Intent(ctx, CallStartInfoService.class));
        } catch (Exception e) {
            Log.e(TAG, "onIncomingCallAnswered : " + e.toString());
        }
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG, "Incoming Call Ended, Number : " + number);
        try {
            ctx.stopService(new Intent(ctx, CallStartInfoService.class));
        } catch (Exception e) {
            Log.e(TAG, "onIncomingCallAnswered : " + e.toString());
        }

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
        try {
            ctx.stopService(new Intent(ctx, CallStartInfoService.class));
        } catch (Exception e) {
            Log.e(TAG, "onIncomingCallAnswered : " + e.toString());
        }

        Toast.makeText(ctx, "Missed Call from " + number, Toast.LENGTH_SHORT).show();
    }

}
