package com.szu.qq_hx;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChatSurfaceActivity extends AppCompatActivity {

    private RecyclerView m_rvRecord;
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_surface);

        btn_send = findViewById(R.id.btn_send_msg);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatSurfaceActivity.this, "send message", Toast.LENGTH_SHORT).show();
            }
        });

        m_rvRecord = (RecyclerView)findViewById(R.id.chat_show_box);
        m_rvRecord.setLayoutManager(new LinearLayoutManager(ChatSurfaceActivity.this));
        m_rvRecord.setAdapter(new RecordboxAdapter(ChatSurfaceActivity.this, new RecordboxAdapter.OnItemClickListerner() {
            @Override
            public void onClick(int pos) {

            }
        }));
    }
}
