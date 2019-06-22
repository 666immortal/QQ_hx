package com.szu.qq_hx;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChatListActivity extends AppCompatActivity {
    private int index;
    private int currentTabIndex;
    private Button[] mTabs;
    private MessageFragment fragment_msg;
    private ContactFragment fragment_ctn;
    private SetFragment fragment_set;
    private Fragment[] fragments;

    ImageView iv_recent_tips,iv_contact_tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        currentTabIndex = 0;
        initView();
        initTab();
    }

    // 初始化按钮和两个小红点图标，并设置默认选中按钮为会话按钮
    private void initView(){
        mTabs = new Button[3];
        mTabs[0] = (Button) findViewById(R.id.btn_message);
        mTabs[1] = (Button) findViewById(R.id.btn_contract);
        mTabs[2] = (Button) findViewById(R.id.btn_set);
        iv_recent_tips = (ImageView)findViewById(R.id.iv_recent_tips);
        iv_contact_tips = (ImageView)findViewById(R.id.iv_contact_tips);

        mTabs[0].setSelected(true);
    }

    // 初始化三个Fragment，并设置默认Fragment为会话
    private void initTab(){
        // 实例化MessageFragment
        fragment_ctn = new ContactFragment();
        fragment_msg = new MessageFragment();
        fragment_set = new SetFragment();
        fragments = new Fragment[]{fragment_msg, fragment_ctn, fragment_set};
        // 把fragment_msg添加到Activity中，记得调用commit
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment_msg).commitAllowingStateLoss();
    }

    public void onTabSelect(View view) {
        switch (view.getId()) {
            case R.id.btn_message:
                index = 0;
                break;
            case R.id.btn_contract:
                index = 1;
                break;
            case R.id.btn_set:
                index = 2;
                break;
        }
        if (currentTabIndex != index) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments[index]).commit();
			/*trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();*/
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
}
