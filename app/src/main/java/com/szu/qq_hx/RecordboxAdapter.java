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

    // 构造函数
    public RecordboxAdapter(Context context, OnItemClickListerner listerner){
        this.mContext = context;
        this.mListerner = listerner;
    }

    @NonNull
    @Override
    public RecordboxAdapter.RecordListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == 0) {
            return new RecordListHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_record_list_recv, viewGroup, false));
        }else{
            return new RecordListHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_record_list_send, viewGroup, false));
        }
    }

    // 此处用来设置不同item不同返回值，以便在onCreateViewHolder中进行判断
    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordboxAdapter.RecordListHolder viewHolder, final int position) {
        // 在这里可以给控件设置内容等操作
        if(position % 2 == 0){
            viewHolder.tv_name.setText("老王");
            viewHolder.tv_charcontent.setText("我非常好");
        }else{
            viewHolder.tv_name.setText("小刘");
            viewHolder.tv_charcontent.setText("您好");
        }

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
        private TextView tv_name;
        private TextView tv_charcontent;

        public RecordListHolder(View itemView){
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_username);
            tv_charcontent = itemView.findViewById(R.id.tv_chatcontent);
        }
    }

    // 回调接口，这样就可以在外面调用这里的函数
    public interface OnItemClickListerner{
        void onClick(int pos);
    }
}
