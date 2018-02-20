package com.coffee.luwak.lcoffee.model;

import android.support.annotation.NonNull;

import com.coffee.luwak.lcoffee.App;
import com.coffee.luwak.lcoffee.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class FSUtils {
    /**
     * Для текущего пользователя добавит документ с информацией о купленных кружках.
     */
    @Deprecated
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
    public static void setCupsToUser(String email, String cupType, Long count) {
        Map<String, Object> cups = new HashMap<>();
        cups.put(cupType, count);

        App.fbStore.collection(Const.CUPS_COLLECTION)
                .document(email + Const.CUPS_SUFFIX)
                .update(cups);
    }

    /**
     * То же, что и setCups, только добавляет по одной кружке указанного типа
     */
    public static void addCupToUser(final String email, final String cupType, final int cupCount) {
        App.fbStore.collection(Const.CUPS_COLLECTION)
                .document(email + Const.CUPS_SUFFIX)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Long count = (long) cupCount;

                if (task.getResult().exists()) {
                    Map<String, Object> map = task.getResult().getData();

                    // если уже есть кружка такого типа, то получим количество
                    for (String key : map.keySet()) {
                        if (key.equals(cupType)) {
                            count = (Long) map.get(key);
                            break;
                        }
                    }
                }
                count++;
                setCupsToUser(email, cupType, count);
            }
        });
    }

    /**
     * Нужна для редактирования меню
     * @return ссылка
     */
    public static DocumentReference getMenuRef() {
        return App.fbStore
                .collection(Const.MENU_COLLECTION)
                .document(Const.MENU_COLLECTION);
    }

    /**
     * Чтобы соединить понятия, например, "кружка 150мл - это и Латте 150мл и Американо 150мл"
     * @return ссылка
     */
    public static DocumentReference getConformityRef() {
        return App.fbStore
                .collection(Const.MENU_COLLECTION)
                .document(Const.CONFORMITY_DOC);
    }

    /**
     * Для хранения статистики заказов
     * @return ссылка на коллекцию
     */
    public static CollectionReference getOrdersCol() {
        return App.fbStore
                .collection(Const.ORDERS_COLLECTION);
    }
}
