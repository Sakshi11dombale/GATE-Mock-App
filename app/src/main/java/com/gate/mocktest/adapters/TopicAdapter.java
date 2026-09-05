package com.gate.mocktest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gate.mocktest.R;
import com.gate.mocktest.models.TopicItem;

public class TopicAdapter extends ListAdapter<TopicItem, TopicAdapter.VH> {

    public interface OnClickListener {
        void onClick(TopicItem item);
    }

    private final OnClickListener listener;

    public TopicAdapter(OnClickListener listener) {
        super(new DiffUtil.ItemCallback<TopicItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull TopicItem a, @NonNull TopicItem b) {
                return a.name.equals(b.name);
            }

            @Override
            public boolean areContentsTheSame(@NonNull TopicItem a, @NonNull TopicItem b) {
                return a.name.equals(b.name) && a.branch.equals(b.branch);
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        TopicItem item = getItem(pos);
        h.tvName.setText(item.name);
        h.tvCount.setText(item.questionCount + " Qs");
        h.tvDiff.setText(item.difficulty);

        switch (item.difficulty) {
            case "Easy":
                h.tvDiff.setBackgroundResource(R.drawable.bg_badge_green);
                h.tvDiff.setTextColor(h.itemView.getContext().getResources().getColor(R.color.success_green, null));
                break;
            case "Hard":
                h.tvDiff.setBackgroundResource(R.drawable.bg_badge_red);
                h.tvDiff.setTextColor(h.itemView.getContext().getResources().getColor(R.color.error_red, null));
                break;
            default:
                h.tvDiff.setBackgroundResource(R.drawable.bg_badge_amber);
                h.tvDiff.setTextColor(h.itemView.getContext().getResources().getColor(R.color.warning_amber, null));
                break;
        }

        h.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvCount, tvDiff;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_topic_name);
            tvCount = v.findViewById(R.id.tv_topic_count);
            tvDiff = v.findViewById(R.id.tv_difficulty);
        }
    }
}
