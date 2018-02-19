package com.coffee.luwak.lcoffee.ui.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MainWorkingActivity;
import com.coffee.luwak.lcoffee.ui.MasterActivity;

public class UserActivity extends MainWorkingActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_lk:
                    openLkFragment();
                    return true;
                case R.id.nav_menu:
                    openMenuFragment();
                    return true;
                case R.id.nav_profile:
                    openProfileFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        openLkFragment();
    }

    private void openLkFragment() {
        if (currentFragment instanceof LkFragment) return;
        setFragment(LkFragment.newInstance());
    }

    private void openMenuFragment() {
        if (currentFragment instanceof MenuUserFragment) return;
        setFragment(MenuUserFragment.newInstance());
    }
}
