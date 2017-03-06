package com.makroid.result.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.makroid.result.informationclass.ListItem;
import com.makroid.result.R;

import java.util.List;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyHolder> {
    private Context context;
    private final static int FADE_DURATION = 1000;
    private int lastPosition = -1;
    private List<ListItem> listData;
    private LayoutInflater inflater;

    public ResultAdapter(List<ListItem> dataFromlist, Context c) {
        this.context = c;
        inflater = LayoutInflater.from(c);
        this.listData = dataFromlist;
    }

    @Override
    public ResultAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.exam_layout.setText(item.getexam());
        holder.marks_layout.setText(item.getmarks());
        holder.internal.setText(item.getinternal());
        holder.external.setText(item.getexternal());
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView exam_layout, marks_layout, internal, external;

        public MyHolder(View itemView) {
            super(itemView);
            exam_layout = (TextView) itemView.findViewById(R.id.exam_layout);
            marks_layout = (TextView) itemView.findViewById(R.id.marks_layout);
            internal = (TextView) itemView.findViewById(R.id.internal);
            external = (TextView) itemView.findViewById(R.id.external);
        }
    }

}
