package com.makroid.result;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by himanshu on 12/25/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context context;
    private final static int FADE_DURATION = 1000;
    private int lastPosition = -1;
    private List<ListItem> listData;
    private LayoutInflater inflater;
    public MyAdapter(List<ListItem> dataFromlist, Context c){
        this.context = c;
        inflater = LayoutInflater.from(c);
        this.listData = dataFromlist;
    }
    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
//        if(position % 5 == 0)
//            holder.cardView.setCardBackgroundColor(Color.RED);
//        else if(position%5==1)
//            holder.cardView.setCardBackgroundColor(Color.BLACK);
//        else if(position%5==2)
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#789456"));
//        else if(position%5==3)
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#241234"));
//        else if(position%5==4)
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#634511"));
        ListItem item = listData.get(position);
        holder.exam_layout.setText(item.getexam());
        holder.marks_layout.setText(item.getmarks());
        holder.internal.setText(item.getinternal());
        holder.external.setText(item.getexternal());
//        setFadeAnimation(holder.container);
    }
//    private void setFadeAnimation(View view) {
//        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(1000);
//        view.startAnimation(anim);
//    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    class MyHolder extends RecyclerView.ViewHolder {

        private TextView exam_layout,marks_layout,internal,external;
        private CardView cardView;
        private LinearLayout container;

        public MyHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.action_container);
            exam_layout = (TextView)itemView.findViewById(R.id.exam_layout);
            marks_layout = (TextView)itemView.findViewById(R.id.marks_layout);
            internal = (TextView)itemView.findViewById(R.id.internal);
            external = (TextView)itemView.findViewById(R.id.external);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

}
