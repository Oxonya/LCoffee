package com.coffee.luwak.lcoffee.model;

import com.coffee.luwak.lcoffee.App;
import com.coffee.luwak.lcoffee.Const;

import java.util.HashMap;
import java.util.Map;

public class FSUtils {
    /**
     * Для текущего пользователя добавит документ с информацией о купленных кружках.
     * Используется при регистрации
     */
    public static void initCupDocument() {
        Map<String, Object> cups = new HashMap<>();
        cups.put(Const.T5, 0);
        cups.put(Const.T10, 0);
        cups.put(Const.T25, 0);
        cups.put(Const.T35, 0);
        cups.put(Const.T45, 0);

        App.fbStore.collection(Const.CUPS_COLLECTION)
                .document(App.fbAuth.getCurrentUser().getEmail() + Const.CUPS_SUFFIX)
                .set(cups);
    }

    /**
     * Устанавливает значение приобретённых пользователем кружек
     * @param email ящик пользователя
     * @param cupType кружка (t50, t100, ...)
     * @param count сколько кружек добавлено
     */
    public static void setCupsToUser(String email, String cupType, int count) {
        Map<String, Object> cups = new HashMap<>();
        cups.put(cupType, count);

        App.fbStore.collection(Const.CUPS_COLLECTION)
                .document(email + Const.CUPS_SUFFIX)
                .set(cups);
    }


}
