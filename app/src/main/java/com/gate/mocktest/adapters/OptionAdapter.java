package com.gate.mocktest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gate.mocktest.R;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.VH> {

    public interface OnOptionSelectedListener {
        void onOptionSelected(String option);
    }

    private String[] labels = {};
    private String[] texts = {};
    private String selectedOption = null;
    private final OnOptionSelectedListener listener;

    public OptionAdapter(OnOptionSelectedListener listener) {
        this.listener = listener;
    }

    public void setOptions(String[] labels, String[] texts, String selectedOption) {
        this.labels = labels;
        this.texts = texts;
        this.selectedOption = selectedOption;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_option, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        String label = labels[pos];
        boolean isSelected = label.equals(selectedOption);

        h.tvLabel.setText(label);
        h.tvText.setText(texts[pos]);

        if (isSelected) {
            h.itemView.setBackgroundResource(R.drawable.bg_option_selected);
            h.tvLabel.setTextColor(ContextCompat.getColor(h.itemView.getContext(), R.color.primary));
            h.tvText.setTextColor(ContextCompat.getColor(h.itemView.getContext(), R.color.primary));
        } else {
            h.itemView.setBackgroundResource(R.drawable.bg_option_normal);
            h.tvLabel.setTextColor(ContextCompat.getColor(h.itemView.getContext(), R.color.text_secondary));
            h.tvText.setTextColor(ContextCompat.getColor(h.itemView.getContext(), R.color.text_primary));
        }

        h.itemView.setOnClickListener(v -> listener.onOptionSelected(label));
    }

    @Override
    public int getItemCount() {
        return labels.length;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvLabel, tvText;
        VH(View v) {
            super(v);
            tvLabel = v.findViewById(R.id.tv_option_label);
            tvText = v.findViewById(R.id.tv_option_text);
        }
    }
}
