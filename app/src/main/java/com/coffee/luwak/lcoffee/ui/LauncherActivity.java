package com.coffee.luwak.lcoffee.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.coffee.luwak.lcoffee.App;
import com.coffee.luwak.lcoffee.model.Role;
import com.coffee.luwak.lcoffee.ui.admin.AdminActivity;
import com.coffee.luwak.lcoffee.ui.user.UserActivity;

/**
 * "Разводной" экран, без UI, всегда запускается первым и определяет какое действие совершить
 */
public class LauncherActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemClock.sleep(1000); // пусть сплеш-скрин секунду повисит (будто у нас что-то грузится)

        // Если пользователь уже вошел, то покажем основной в зависимости от его роли,
        // иначе - экран входа/регистрации

        if (App.isUserLoggedIn()) {
            Role role = App.getCurrentRole();

            if (role == Role.admin || role == Role.barista) {
                /*
                    Следующие 4 строки заставляют новую Activity открываться как бы в новом Task
                    (Про таски и процессы можешь почитать отдельно)
                    Бэкстек будет чист и мы не сможем вернуться назад из этой Activity
                    Есть другие способы это сделать, но этот, вроде как, один из самых простых,
                    хотя, при открытии приложения можно увидеть такой "скачок"
                 */
                Intent intent = new Intent(this, AdminActivity.class);
                ComponentName cn = intent.getComponent();
                Intent intent1 = Intent.makeRestartActivityTask(cn);
                startActivity(intent1);
            } else {
                Intent intent = new Intent(this, UserActivity.class);
                ComponentName cn = intent.getComponent();
                Intent intent1 = Intent.makeRestartActivityTask(cn);
                startActivity(intent1);
            }

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            ComponentName cn = intent.getComponent();
            Intent intent1 = Intent.makeRestartActivityTask(cn);
            startActivity(intent1);
        }
    }
}
