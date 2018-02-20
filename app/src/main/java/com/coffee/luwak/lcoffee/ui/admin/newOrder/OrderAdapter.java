package com.coffee.luwak.lcoffee.ui.admin.newOrder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.model.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private ArrayList<MenuItem> items = new ArrayList<>();
    private HashMap<String, Integer> itemsMap = new HashMap<>();

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderHolder(parent);
    }

    @Override
    public void onBindViewHolder(final OrderHolder holder, final int position) {
        final MenuItem item = items.get(position);
        String text = (position + 1) + ". ";
        text += item.name + " (" + item.cost + ")";

        holder.etName.setText(text);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(ArrayList<MenuItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    void addItem(MenuItem item) {
        items.add(item);

        if (itemsMap.containsKey(item.name)) {
            Integer count = itemsMap.get(item.name);
            count++;
            itemsMap.put(item.name, count);
        } else
            itemsMap.put(item.name, 1);

        notifyDataSetChanged();
    }

    void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public HashMap<String, Integer> getItemsMap() {
        return itemsMap;
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        TextView etName;

        OrderHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_menu, parent, false));

            etName = itemView.findViewById(R.id.etName);
        }
    }
}
