package com.coffee.luwak.lcoffee.ui.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class MenuAdminFragment extends MasterFragment {
    public MenuAdminFragment() {
        // Required empty public constructor
    }

    public static MenuAdminFragment newInstance() {
        return new MenuAdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_admin, container, false);

        return view;
    }

}
