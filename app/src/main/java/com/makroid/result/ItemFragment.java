package com.makroid.result;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by himanshu on 12/25/16.
 */

public class ItemFragment extends Fragment {
    private RecyclerView recView;
    private MyAdapter adapter;
    View view;
    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.item_fragment, container, false);
        recView = (RecyclerView) view.findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(DeepData.getData(),getActivity());
        SpeedyLinearLayoutManager mLayoutManager = new SpeedyLinearLayoutManager(getActivity());
        recView.setLayoutManager(mLayoutManager);
        recView.smoothScrollToPosition(50);
        recView.setAdapter(adapter);
        return view;
    }

}