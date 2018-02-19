package com.coffee.luwak.lcoffee.ui;

import android.content.Context;
import android.support.v4.app.Fragment;


public abstract class MasterFragment extends Fragment {

    protected MainWorkingActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activity = (MainWorkingActivity) context;
            activity.currentFragment = this;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must be attached to MainWorkingActivity");
        }
    }
}
