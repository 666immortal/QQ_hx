package com.szu.qq_hx;

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

public class ContactFragment extends Fragment {
    private RecyclerView m_RvCtn;

    @Nullable
    // 相当于Activity中的setContentView
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }

    // 创建完成之后的回调方法，可以在里面做一些事情
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_RvCtn = (RecyclerView)view.findViewById(R.id.rv_ctn);
        // 线性布局管理器
        m_RvCtn.setLayoutManager(new LinearLayoutManager(getActivity()));
        // onDraw 绘制Item前， onDrawOver绘制到Item上， getItemOffset在Item周边绘制内容
        m_RvCtn.addItemDecoration(new MyDecoration());
        // 这个OnItemClickListerner是我们使用接口自己写的
        m_RvCtn.setAdapter(new ContactAdapter(getActivity(), new ContactAdapter.OnItemClickListerner() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getActivity(), "ctn click..." + pos, Toast.LENGTH_SHORT).show();
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
}
