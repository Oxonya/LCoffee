package com.coffee.luwak.lcoffee.ui.admin.menuAdmin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.model.FSUtils;
import com.coffee.luwak.lcoffee.model.MenuItem;
import com.coffee.luwak.lcoffee.ui.MasterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuAdminFragment extends MasterFragment implements View.OnClickListener {
    private RecyclerView rvMenu;
    private MenuAdminAdapter adapter;

    public MenuAdminFragment() {
        // Required empty public constructor
    }

    public static MenuAdminFragment newInstance() {
        return new MenuAdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_admin, container, false);

        view.findViewById(R.id.btnAdd).setOnClickListener(this);
        view.findViewById(R.id.btnSave).setOnClickListener(this);

        rvMenu = view.findViewById(R.id.rvMenuItems);
        rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MenuAdminAdapter();
        rvMenu.setAdapter(adapter);

        getMenu();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                adapter.addNewItem();
                rvMenu.scrollToPosition(adapter.getItemCount() - 1);
                break;
            case R.id.btnSave:
                saveMenu(adapter.getItems());
                break;
            default:
                break;
        }
    }

    /**
     * Закачаем меню в firestore, сначала очистим документ, а затем заново его запишем
     * @param items меню
     */
    private void saveMenu(ArrayList<MenuItem> items) {
        Map<String, Object> docData = new HashMap<>();
        if (items != null) {
            for (MenuItem item : items) {
                if (item.name != null && item.name.length() > 0 && item.cost != null)
                    docData.put(item.name, item.cost);
            }
            FSUtils.getMenuRef()
                    .delete();

            FSUtils.getMenuRef()
                    .set(docData)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            activity.toast("Не удалось сохранить меню");
                        }
                    });
        }
    }

    /**
     * Загрузим меню из firestore
     */
    private void getMenu() {
        final DocumentReference docRef = FSUtils.getMenuRef();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> map = task.getResult().getData();
                ArrayList<MenuItem> menuItems = new ArrayList<>();

                for (String key : map.keySet()) {
                    MenuItem item = new MenuItem(key, (Long) map.get(key));
                    menuItems.add(item);
                }

                adapter.setItems(menuItems);
            }
        });
    }
}
