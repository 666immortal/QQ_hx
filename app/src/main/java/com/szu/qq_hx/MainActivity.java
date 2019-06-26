package com.szu.qq_hx;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import com.szu.qq_hx.Client;

public class MainActivity extends AppCompatActivity {

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(){
//            public void run(){
//                if(client.connect()){
//                    if(client.connect()){
//                        client.register("xiaoliu", "1234");
//                    }
//
//
//                }
//            }
//        }.start();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
//                    Client client = new Client();
//                    if(client.connect())
//                    {
//                        Intent intent = new Intent(MainActivity.this, RegLogActivity.class);
//                        intent.putExtra("clientClass",  client);
//                        startActivity(intent);
//                    }
                // 测试代码，测试完注释掉
                Intent intent = new Intent(MainActivity.this, RegLogActivity.class);
                startActivity(intent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
    }
}
