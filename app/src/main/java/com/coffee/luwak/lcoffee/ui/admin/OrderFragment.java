package com.coffee.luwak.lcoffee.ui.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class OrderFragment extends MasterFragment {
    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        return view;
    }

}
