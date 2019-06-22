package com.szu.qq_hx;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MessageFragment extends Fragment {
    private Button m_btntest;

    @Nullable
    // 相当于Activity中的setContentView
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

    // 创建完成之后的回调方法，可以在里面做一些事情
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_btntest = (Button)view.findViewById(R.id.btn_test_message);
        m_btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "我被点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
