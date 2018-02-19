package com.coffee.luwak.lcoffee.ui.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class OrderFragment extends MasterFragment implements View.OnClickListener {
    private TextView tvDiscountValue;
    private SeekBar sb;
    private RadioButton rbCard, rbCash;

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

        tvDiscountValue = view.findViewById(R.id.tvDiscountValue);
        sb = view.findViewById(R.id.sbDiscount);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String newText = "Скидка: " + progress + "%";

                tvDiscountValue.setText(newText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        rbCard = view.findViewById(R.id.radioCard);
        rbCash = view.findViewById(R.id.radioCash);

        view.findViewById(R.id.btnOrder).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOrder:
                break;
            default:
                break;
        }
    }
}
