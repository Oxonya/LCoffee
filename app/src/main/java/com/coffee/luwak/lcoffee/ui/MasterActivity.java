package com.coffee.luwak.lcoffee.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Основной класс для всех наших Activity, здесь мы можем хранить общие для всех методы
 */
public abstract class MasterActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Загрузка...");
    }

    /**
     * "Тост" показывает сообщение в нижней части экрана
     * (хотя его положение вроде как можно настраивать)
     *
     * Используя метод в этом классе, мы экономим целую кучу кода
     * @param message сообщение для отображения на экране
     */
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Покажем простой диалог прогресса
     */
    public void showProgress() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * Скроем простой диалог прогресса
     */
    public void hideProgress() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    /**
     * Перезапустит приложение
     */
    public void relaunch() {
        startActivity(new Intent(this, LauncherActivity.class));
    }
}
