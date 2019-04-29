package com.lnlyj.socket.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lnlyj.socket.LnlyjSocket;
import com.lnlyj.socket.LnlyjSocketCallback;
import com.lnlyj.socket.R;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LnlyjSocket.open("10.110.16.24", 1000).addCallback(new LnlyjSocketCallback() {
            @Override
            public void onConnect() {

            }

            @Override
            public void onDisConnect(boolean isServer) {

            }

            @Override
            public void onSend(byte[] data) {

            }

            @Override
            public void onReceived(byte[] data) {
                Log.d(TAG, "onReceived: " + new String(data));
            }

            @Override
            public void onError(int err) {

            }
        })
        .connect();

    }
}
