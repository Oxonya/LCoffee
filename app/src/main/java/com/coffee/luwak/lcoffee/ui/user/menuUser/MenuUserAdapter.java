package com.coffee.luwak.lcoffee.ui.user.menuUser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.model.MenuItem;

import java.util.ArrayList;

public class MenuUserAdapter extends RecyclerView.Adapter<MenuUserAdapter.MUHolder> {
    private ArrayList<MenuItem> items = new ArrayList<>();

    @Override
    public MUHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MUHolder(parent);
    }

    @Override
    public void onBindViewHolder(final MUHolder holder, final int position) {
        final MenuItem item = items.get(position);

        if (item.cost != null) {
            holder.etCost.setText("Стоимость: " + item.cost);
        }
        if (item.name != null) {
            holder.etName.setText("Наименование: " + item.name);
        }
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

    class MUHolder extends RecyclerView.ViewHolder {
        TextView etName, etCost;

        MUHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_user, parent, false));
            etName = itemView.findViewById(R.id.tvName);
            etCost = itemView.findViewById(R.id.tvCost);
        }
    }
}
