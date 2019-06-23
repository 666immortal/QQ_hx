package com.szu.qq_hx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecordboxAdapter extends RecyclerView.Adapter<RecordboxAdapter.RecordListHolder> {

    private Context mContext;
    private OnItemClickListerner mListerner;

    // 在此传入列表数据
    // private List<String> list;
    // Adapter改写成下面这样
    // public MessageAdapter(Context context, list)

    public RecordboxAdapter(Context context, OnItemClickListerner listerner){
        this.mContext = context;
        this.mListerner = listerner;
    }

    @NonNull
    @Override
    public RecordboxAdapter.RecordListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecordListHolder(LayoutInflater.from(mContext).inflate(R.layout., viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecordboxAdapter.RecordListHolder viewHolder, final int position) {
        // 在这里可以给控件设置内容等操作
        viewHolder.textview.setText("Message List");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListerner.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class RecordListHolder extends RecyclerView.ViewHolder{

        // 布局里的控件在这里声明
        private TextView textview;

        public RecordListHolder(View itemView){
            super(itemView);
            textview = itemView.findViewById(R.id.tv_title1);
        }
    }

    // 回调接口，这样就可以在外面调用这里的函数
    public interface OnItemClickListerner{
        void onClick(int pos);
    }
}
