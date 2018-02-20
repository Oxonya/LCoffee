package com.coffee.luwak.lcoffee.ui.admin.newOrder;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.model.FSUtils;
import com.coffee.luwak.lcoffee.model.MenuItem;
import com.coffee.luwak.lcoffee.model.Order;
import com.coffee.luwak.lcoffee.ui.MasterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class OrderFragment extends MasterFragment implements View.OnClickListener {
    private TextView tvDateTime, tvDiscountValue, tvSummary;
    private SeekBar sb;
    private RadioButton rbCard, rbCash;
    private View progress;
    private Button btnAddToMenu;

    private EditText etEmail;

    private RecyclerView rvMenuItems;
    private OrderAdapter adapter;

    private ArrayList<MenuItem> availableMenuItems = new ArrayList<>();

    private Order currentOrder = new Order();

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        tvDiscountValue = view.findViewById(R.id.tvDiscountValue);
        sb = view.findViewById(R.id.sbDiscount);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String newText = "Скидка: " + progress + "%";

                tvDiscountValue.setText(newText);
                recalculateSum();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        rbCard = view.findViewById(R.id.radioCard);
        rbCash = view.findViewById(R.id.radioCash);

        etEmail = view.findViewById(R.id.etEmail);
        tvSummary = view.findViewById(R.id.tvSummary);
        tvDateTime = view.findViewById(R.id.tvDateTime);

        rvMenuItems = view.findViewById(R.id.rvMenuItems);
        rvMenuItems.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderAdapter();
        rvMenuItems.setAdapter(adapter);

        adapter.clearItems();

        progress = view.findViewById(R.id.progress);
        btnAddToMenu = view.findViewById(R.id.btnAddToMenu);
        btnAddToMenu.setOnClickListener(this);

        initForm();

        receiveOrders();

        view.findViewById(R.id.btnOrder).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToMenu:
                addOrderItem();
                break;
            case R.id.btnOrder:
                saveOrder();
                break;
            default:
                break;
        }
    }

    private void receiveOrders() {
        final DocumentReference ref = FSUtils.getMenuRef();
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> map = task.getResult().getData();
                availableMenuItems.clear();

                for (String key : map.keySet()) {
                    MenuItem item = new MenuItem(key, (Long) map.get(key));
                    availableMenuItems.add(item);
                }

                progress.setVisibility(View.GONE);
                rvMenuItems.setVisibility(View.VISIBLE);
                btnAddToMenu.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addOrderItem() {
        String[] arr = new String[availableMenuItems.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = availableMenuItems.get(i).name + " (" + availableMenuItems.get(i).cost + ")";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("Выберите товар")
                .setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.addItem(availableMenuItems.get(which));
                        recalculateSum();
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void recalculateSum() {
        String text = "ИТОГО: ";
        Long calc = 0L;
        for(MenuItem item : adapter.getItems()) {
            calc += item.cost;
        }

        float k = 1 / 100f * sb.getProgress(); // доля от единицы

        currentOrder.sum = calc - Math.round(calc * k);

        text += currentOrder.sum;
        tvSummary.setText(text);
    }

    private void saveOrder() {
        // простейшая проверка
        if (etEmail.getText().length() == 0) {
            etEmail.setError("Необходимо ввести e-mail");
            return;
        }
        // если нечего заказывать, то зачем делать заказ
        if (adapter.getItems().size() == 0) return;


        /*
            По всем полям соответствий мы ищем те, которые есть в текущем заказе.
            Если в текущем заказе есть что-то, что подходит под описание акционных "кружек",
            то мы создаём документ для пользователя с нужным полем
            и он сможет увидеть количество купленных собою кружек
         */
        FSUtils.getConformityRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> map = task.getResult().getData();
                for (String key : map.keySet()) {
                    for (MenuItem item : adapter.getItems()) {
                        if (key.equals(item.name)) {

                            Integer count = adapter.getItemsMap().get(key);

                            FSUtils.addCupToUser(etEmail.getText().toString(), (String)map.get(key), count);
                            break;
                        }
                    }
                }
            }
        });

        currentOrder.userEmail = etEmail.getText().toString();
        currentOrder.isCardPay = rbCard.isChecked(); // если радио кнопка "картой" не выбрана, значит выбрана "наличными"
        // Зальём документ в firebase со сгенерированным ключем
        FSUtils.getOrdersCol().document().set(currentOrder);

//        initForm();
        activity.toast("Заказ оформлен");
    }

    private void initForm() {
        etEmail.setText("");

        Date today = new Date();

        String now = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US).format(today);
        tvDateTime.setText(now);

        currentOrder.date = today.getTime();
        currentOrder.dateStr = now;

        sb.setProgress(0);
    }
}
