package com.coffee.luwak.lcoffee.ui.admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.MainWorkingActivity;
import com.coffee.luwak.lcoffee.ui.admin.menuAdmin.MenuAdminFragment;
import com.coffee.luwak.lcoffee.ui.admin.newOrder.OrderFragment;

public class AdminActivity extends MainWorkingActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_order:
                    openOrderFragment();
                    return true;
                case R.id.nav_menu:
                    openMenuFragment();
                    return true;
                case R.id.nav_promo:
                    openPromoFragment();
                    return true;
                case R.id.nav_report:
                    openReportFragment();
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
        setContentView(R.layout.activity_admin);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        openOrderFragment();
    }

    private void openOrderFragment() {
        if (currentFragment instanceof OrderFragment) return;
        setFragment(OrderFragment.newInstance());
    }

    private void openMenuFragment() {
        if (currentFragment instanceof MenuAdminFragment) return;
        setFragment(MenuAdminFragment.newInstance());
    }

    private void openPromoFragment() {
        if (currentFragment instanceof PromoFragment) return;
        setFragment(PromoFragment.newInstance());
    }

    private void openReportFragment() {
        if (currentFragment instanceof ReportFragment) return;
        setFragment(ReportFragment.newInstance());
    }
}

