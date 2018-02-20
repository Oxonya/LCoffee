package com.coffee.luwak.lcoffee.ui.user.menuUser;


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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MenuUserFragment extends MasterFragment {
    MenuUserAdapter adapter;
    private RecyclerView rvMenu;

    public MenuUserFragment() {
        // Required empty public constructor
    }

    public static MenuUserFragment newInstance() {
        return new MenuUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_user, container, false);

        rvMenu = view.findViewById(R.id.rvMenuItems);
        rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MenuUserAdapter();
        rvMenu.setAdapter(adapter);

        getMenu();

        return view;
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
