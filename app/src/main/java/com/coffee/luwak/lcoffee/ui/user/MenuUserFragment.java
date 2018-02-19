package com.coffee.luwak.lcoffee.ui.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class MenuUserFragment extends MasterFragment {
    public MenuUserFragment() {
        // Required empty public constructor
    }

    public static MenuUserFragment newInstance() {
        return new MenuUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_user, container, false);
        return view;
    }

}
