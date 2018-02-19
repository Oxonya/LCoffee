package com.coffee.luwak.lcoffee.ui.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class PromoFragment extends MasterFragment {
    public PromoFragment() {
        // Required empty public constructor
    }

    public static PromoFragment newInstance() {
        return new PromoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo, container, false);
        return view;
    }

}
