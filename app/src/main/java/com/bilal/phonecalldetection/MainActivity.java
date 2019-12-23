package com.bilal.phonecalldetection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bilal.phonecalldetection.service.CallStartInfoService;

public class MainActivity extends AppCompatActivity {

    private boolean serviceRunning;

    private final int PERMISSION_ALL = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceRunning = false;

        Button button = (Button) findViewById(R.id.button_show_floating_window_MainActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the service
                serviceRunning = true;
                Intent intent = new Intent(getApplicationContext(), CallStartInfoService.class);
                intent.putExtra(CallStartInfoService.INTENT_EXTRA_PHONE_NUMBER, "<Phone Number>");
                intent.putExtra(CallStartInfoService.INTENT_EXTRA_CALL_TYPE, "<Call Type(Incoming/Outgoing)>");
                startService(intent);
            }
        });

        requestPermissions();
    }

    private void requestPermissions() {
        String permissions[] = {Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.READ_CALL_LOG, // Required in API 26+ to be able to access number of incoming call
            Manifest.permission.SYSTEM_ALERT_WINDOW};

        ActivityCompat.requestPermissions(this, permissions, PERMISSION_ALL);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo!
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(serviceRunning) {
            // Stop the service
            stopService( new Intent(getApplicationContext(), CallStartInfoService.class));
            serviceRunning = false;
        } else {
            super.onBackPressed();
        }
    }
}
