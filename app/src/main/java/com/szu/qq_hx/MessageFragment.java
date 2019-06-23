package com.szu.qq_hx;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MessageFragment extends Fragment {
    private RecyclerView m_RvMsg;

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

        m_RvMsg = (RecyclerView)view.findViewById(R.id.rv_msg);
        // 线性布局管理器
        m_RvMsg.setLayoutManager(new LinearLayoutManager(getActivity()));
        // onDraw 绘制Item前， onDrawOver绘制到Item上， getItemOffset在Item周边绘制内容
        m_RvMsg.addItemDecoration(new MyDecoration());
        // 这个OnItemClickListerner是我们使用接口自己写的
        m_RvMsg.setAdapter(new MessageAdapter(getActivity(), new MessageAdapter.OnItemClickListerner() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(getActivity(), ChatSurfaceActivity.class);
                startActivity(intent);
            }
        }));
    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
