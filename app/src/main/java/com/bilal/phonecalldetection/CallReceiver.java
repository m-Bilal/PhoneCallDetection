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
    protected void onIncomingCallReceived(final Context ctx, String number, Date start) {
        Log.d(TAG, "Incoming Call Received, Number : " + number);

        // Start the service
        try {
            Intent intent = new Intent(ctx, CallStartInfoService.class);
            intent.putExtra(CallStartInfoService.INTENT_EXTRA_PHONE_NUMBER, number);
            intent.putExtra(CallStartInfoService.INTENT_EXTRA_CALL_TYPE, "Incoming call from:");
            ctx.startService(intent);
        } catch (Exception e) {
            Log.e(TAG, "onIncomingCallStarted : " + e.toString());
        }
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

        // Start the service
        try {
            Intent intent = new Intent(ctx, CallStartInfoService.class);
            intent.putExtra(CallStartInfoService.INTENT_EXTRA_PHONE_NUMBER, number);
            intent.putExtra(CallStartInfoService.INTENT_EXTRA_CALL_TYPE, "Outgoing call to:");
            ctx.startService(intent);
        } catch (Exception e) {
            Log.e(TAG, "onOutgoingCallStarted : " + e.toString());
        }
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG, "Outgoing Call Ended, Number : " + number);
        try {
            ctx.stopService(new Intent(ctx, CallStartInfoService.class));
        } catch (Exception e) {
            Log.e(TAG, "onIncomingCallAnswered : " + e.toString());
        }

        Toast.makeText(ctx, "Outgoing Call to " + number, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d(TAG, "Missed Call, Number : " + number);
        try {
            Intent intent = new Intent(ctx, CallStartInfoService.class);
            intent.putExtra(CallStartInfoService.INTENT_EXTRA_PHONE_NUMBER, number);
            intent.putExtra(CallStartInfoService.INTENT_EXTRA_CALL_TYPE, "Incoming call from:");
            ctx.startService(intent);
        } catch (Exception e) {
            Log.e(TAG, "onIncomingCallAnswered : " + e.toString());
        }

        Toast.makeText(ctx, "Missed Call from " + number, Toast.LENGTH_SHORT).show();
    }

}
