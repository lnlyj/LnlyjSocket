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

        String abc = "xxxx";

        Infoxx infoxx = new Infoxx();
        infoxx.setIp("12345");
        infoxx.setPort(111);

        String temp = Test.map.get(infoxx);

        if (temp == null) {
            Test.map.put(infoxx, abc);
        } else {
            Log.d(TAG, "onCreate: exit");
        }


        LnlyjSocket.open("10.110.16.24", 1000).addCallback(new LnlyjSocketCallback() {
            @Override
            public void onConnect() {
                Log.d(TAG, "onConnect: ");
            }

            @Override
            public void onDisConnect(boolean isServer) {
                Log.d(TAG, "onDisConnect: ");
            }

            @Override
            public void onSend(byte[] data) {
                Log.d(TAG, "onSend: ");
            }

            @Override
            public void onReceived(byte[] data) {
                Log.d(TAG, "onReceived: " + new String(data));
            }

            @Override
            public void onError(int err) {
                Log.d(TAG, "onError: ");
            }
        })
        .connect();

    }
}
