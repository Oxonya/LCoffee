package com.coffee.luwak.lcoffee.ui.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class LkFragment extends MasterFragment {
    private TextView t50, t100, t250, t350, t450;

    public LkFragment() {
        // Required empty public constructor
    }

    public static LkFragment newInstance() {
        return new LkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lk, container, false);

        t50 = view.findViewById(R.id.t50);
        t100 = view.findViewById(R.id.t100);
        t250 = view.findViewById(R.id.t250);
        t350 = view.findViewById(R.id.t350);
        t450 = view.findViewById(R.id.t450);

        updateInfo();

        return view;
    }

    private void updateInfo() {
        t50.setText("0");
        t100.setText("0");
        t250.setText("0");
        t350.setText("0");
        t450.setText("0");
    }
}
