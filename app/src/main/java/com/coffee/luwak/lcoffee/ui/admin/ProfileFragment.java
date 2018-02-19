package com.coffee.luwak.lcoffee.ui.admin;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.luwak.lcoffee.App;
import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.LauncherActivity;
import com.coffee.luwak.lcoffee.ui.MasterFragment;

public class ProfileFragment extends MasterFragment implements View.OnClickListener {
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.btnExit).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle("Выход из профиля")
                        .setMessage("Вы действительно хотите выйти?")
                        .create();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.fbAuth.signOut();
                        activity.relaunch();
                    }
                });

                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                break;
            default:
                break;
        }
    }
}
