package com.gate.mocktest.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gate.mocktest.databinding.ItemTestCardBinding;
import com.gate.mocktest.models.TestCardItem;

import java.util.ArrayList;
import java.util.List;

public class TestCardAdapter
        extends RecyclerView.Adapter<TestCardAdapter.ViewHolder> {

    public interface OnTestClick {
        void onClick(TestCardItem item);
    }

    private final OnTestClick listener;

    private final List<TestCardItem> items =
            new ArrayList<>();

    public TestCardAdapter(OnTestClick listener) {
        this.listener = listener;
    }

    public void submitList(List<TestCardItem> list) {

        items.clear();

        if (list != null) {
            items.addAll(list);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        ItemTestCardBinding binding =
                ItemTestCardBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        TestCardItem item = items.get(position);

        holder.binding.tvTestName.setText(item.name);

        holder.binding.tvTestInfo.setText(item.info);

        holder.binding.tvBadge.setText(item.badge);

        if ("PYQ".equalsIgnoreCase(item.badge)) {

            holder.binding.tvAction.setText("OPEN PYQ  ›");

            holder.binding.tvCardIcon.setText("▣");

        } else {

            holder.binding.tvAction.setText("START  ›");

            holder.binding.tvCardIcon.setText("✎");
        }

        holder.itemView.setOnClickListener(
                v -> listener.onClick(item)
        );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder
            extends RecyclerView.ViewHolder {

        ItemTestCardBinding binding;

        ViewHolder(ItemTestCardBinding binding) {

            super(binding.getRoot());

            this.binding = binding;
        }
    }
}