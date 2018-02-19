package com.coffee.luwak.lcoffee.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.ui.admin.ProfileFragment;

/**
 * Данный класс также вынесен для удобства.
 * Используется только для {@link com.coffee.luwak.lcoffee.ui.admin.AdminActivity}
 * и {@link com.coffee.luwak.lcoffee.ui.user.UserActivity}
 *
 * Здесь можно расположить общие действия над фрагментами
 */
public abstract class MainWorkingActivity extends MasterActivity {

    public MasterFragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast("Добро пожаловать!");
    }

    /**
     * Открывает определённый фрагмент
     * @param fragment MasterFragment
     */
    public void setFragment(MasterFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (currentFragment != null) transaction.remove(currentFragment);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * Профиль везде идентичен, поэтому метод вынесен в базовый класс
     */
    public void openProfileFragment() {
        if (currentFragment instanceof ProfileFragment) return;
        setFragment(ProfileFragment.newInstance());
    }
}
