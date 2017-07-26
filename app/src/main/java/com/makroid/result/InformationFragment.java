package com.makroid.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InformationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    TextView nameText, collegeText, rollText;

    public InformationFragment newInstance(String param1, String param2, String param3) {
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
        View view = inflater.inflate(R.layout.info_fragment, container, false);
//        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(),"custom.ttf");
        nameText = (TextView) view.findViewById(R.id.name);
        collegeText = (TextView) view.findViewById(R.id.college_name);
        rollText = (TextView) view.findViewById(R.id.roll);
        nameText.setText(getArguments().getString(ARG_PARAM1));
//        nameText.setTypeface(customFont);
        collegeText.setText(getArguments().getString(ARG_PARAM2));
//        collegeText.setTypeface(customFont);
        rollText.setText(getArguments().getString(ARG_PARAM3));
//        rollText.setTypeface(customFont);
        return view;
    }
}
