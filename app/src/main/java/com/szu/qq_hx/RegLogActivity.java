package com.szu.qq_hx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegLogActivity extends AppCompatActivity {

    private Button m_btnLogin;
    private Button m_btnRegister;
    private EditText m_edtUserName;
    private EditText m_editPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        // 传入Client用，测试完记得解开注释
//        Bundle bundle = getIntent().getExtras();
//        Client client = (Client) bundle.getSerializable("clientClass");


        m_btnLogin = (Button)findViewById(R.id.btn_login);
        m_btnRegister = (Button)findViewById(R.id.btn_register);
        m_edtUserName = (EditText)findViewById(R.id.edt_1) ;
        m_editPassWord = (EditText)findViewById(R.id.edt_2);

        m_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 编写登录按钮的响应函数

                // 以下为测试代码，测试完请注释
                Intent intent = new Intent(RegLogActivity.this, ChatListActivity.class);
                startActivity(intent);
            }
        });


        m_btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 编写注册按钮的响应函数

                // 以下为测试代码，测试完请注释
//                Intent intent = new Intent(RegLogActivity.this, ChatListActivity.class);
//                startActivity(intent);
            }
        });
    }
}
