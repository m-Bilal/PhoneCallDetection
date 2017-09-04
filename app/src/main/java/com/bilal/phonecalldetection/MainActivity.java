package com.bilal.phonecalldetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bilal.phonecalldetection.service.CallStartInfoService;

public class MainActivity extends AppCompatActivity {

    private boolean serviceRunning;

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
                intent.putExtra("number", "<Phone Number>");
                startService(intent);

                /*Intent intent = new Intent(getApplicationContext(), IncomingCallInfoActivity.class);
                intent.putExtra("number", "<Call number>");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                */
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(serviceRunning) {
            stopService( new Intent(getApplicationContext(), CallStartInfoService.class));
        } else {
            super.onBackPressed();
        }
    }
}
