package com.coffee.luwak.lcoffee.ui.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class LkFragment extends MasterFragment {
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
        return view;
    }

}
