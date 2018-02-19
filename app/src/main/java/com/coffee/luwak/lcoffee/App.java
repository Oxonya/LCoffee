package com.coffee.luwak.lcoffee;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.coffee.luwak.lcoffee.model.Role;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class App extends Application {
    public static final String CUR_ROLE = "CurRole";
    /**
     * Нужен для возможности обращения к контексту приложения из любого места
     * Утечки памяти не будет, т.к. для получения контекста
     * мы используем метод {@link Application#getApplicationContext()}
     *
     * (с this, вроде как, могут быть утечки; об этом говорят, хотя я не сталкивался)
     */
    public static Context CONTEXT;

    /**
     * Может пригодиться для выполнения каких-то асинхронных задач.
     */
    public static Handler HANDLER;

    /**
     * Нужен для чтения "настроек" приложения
     */
    public static SharedPreferences PREFS;
    /**
     * Нужен для редактирования "настроек" приложения. (Возможно, пригодится)
     * С помощью этого поля мы можем сохранять какие-то данные локально на устройстве,
     * без использования БД
     */
    public static SharedPreferences.Editor PREFS_EDITOR;

    /**
     * Для авторизации
     */
    public static FirebaseAuth fbAuth;

    /**
     * Для хранилища
     * (Возможно, не стоит помещать инстанс firestore в статическую переменную.
     * Если что, можно будет заменить на {@link FirebaseFirestore#getInstance()})
     */
    public static FirebaseFirestore fbStore;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();
        HANDLER = new Handler();

        PREFS = getSharedPreferences("settings", Context.MODE_PRIVATE);
        PREFS_EDITOR = PREFS.edit();

        fbAuth = FirebaseAuth.getInstance();
        fbStore = FirebaseFirestore.getInstance();
    }

    /**
     * @return Есть ли уже пользователь, который был залогинен
     */
    public static boolean isUserLoggedIn() {
        return fbAuth.getCurrentUser() != null;
    }

    /**
     * @return роль текущего пользователя
     */
    public static Role getCurrentRole() {
        String role = PREFS.getString(CUR_ROLE, Role.user.name());
        for (Role role1 :
                Role.values()) {
            if (role1.toString().equals(role))
                return role1;
        }

        return Role.user;
    }

    /**
     * Устанавливает роль текущего пользователя
     */
    public static void setCurrentRole(Role role) {
        PREFS_EDITOR.putString(CUR_ROLE, role.name()).apply();
    }
}
