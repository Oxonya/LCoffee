package com.coffee.luwak.lcoffee.ui.admin.menuAdmin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.model.MenuItem;

import java.util.ArrayList;

public class MenuAdminAdapter extends RecyclerView.Adapter<MenuAdminAdapter.MAHolder> {
    private ArrayList<MenuItem> items = new ArrayList<>();

    @Override
    public MAHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MAHolder(parent);
    }

    @Override
    public void onBindViewHolder(final MAHolder holder, final int position) {
        final MenuItem item = items.get(position);

        if (item.cost != null) {
            holder.etCost.setText(item.cost + "");
        }
        if (item.name != null) {
            holder.etName.setText(item.name);
        }

        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(item);
                notifyDataSetChanged();
            }
        });
        holder.etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                item.name = holder.etName.getText().toString();
            }
        });
        holder.etCost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String text = holder.etCost.getText().toString();
                if (text.length() > 0)
                    item.cost = Long.valueOf(text);
            }
        });
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

    void addNewItem() {
        items.add(new MenuItem());
        notifyItemChanged(items.size() - 1);
    }

    ArrayList<MenuItem> getItems() {
        return items;
    }

    class MAHolder extends RecyclerView.ViewHolder {
        EditText etName, etCost;
        ImageView ivRemove;

        public MAHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_admin, parent, false));
            etName = itemView.findViewById(R.id.etName);
            etCost = itemView.findViewById(R.id.etCost);
            ivRemove = itemView.findViewById(R.id.ivRemove);
        }
    }
}
