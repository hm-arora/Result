package com.makroid.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InformationFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    TextView name,college,roll;
    public InformationFragment newInstance(String param1, String param2,String param3) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment,container,false);
        name =  (TextView) view.findViewById(R.id.name);
        college =  (TextView) view.findViewById(R.id.college_name);
        roll =  (TextView) view.findViewById(R.id.roll);
        name.setText(getArguments().getString(ARG_PARAM1));
        college.setText(getArguments().getString(ARG_PARAM2));
        roll.setText(getArguments().getString(ARG_PARAM3));
        return view;
    }
}
