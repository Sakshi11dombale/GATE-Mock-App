package com.gate.mocktest.adapters;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gate.mocktest.R;
import com.gate.mocktest.models.WeakTopic;

public class WeakTopicAdapter extends ListAdapter<WeakTopic, WeakTopicAdapter.VH> {

    public WeakTopicAdapter() {
        super(new DiffUtil.ItemCallback<WeakTopic>() {
            @Override
            public boolean areItemsTheSame(@NonNull WeakTopic a, @NonNull WeakTopic b) {
                return a.subject.equals(b.subject);
            }

            @Override
            public boolean areContentsTheSame(@NonNull WeakTopic a, @NonNull WeakTopic b) {
                return a.accuracy == b.accuracy;
            }
        });
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weak_topic, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        WeakTopic w = getItem(pos);
        h.tvSubject.setText(w.subject);
        h.tvAcc.setText(String.format("%.0f%%", w.accuracy));
        h.pb.setProgress((int) w.accuracy);

        int color = w.accuracy < 50 ? 0xFFE24B4A : 0xFFEF9F27;
        h.pb.setProgressTintList(ColorStateList.valueOf(color));
        h.tvAcc.setTextColor(color);
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvSubject, tvAcc;
        ProgressBar pb;

        VH(View v) {
            super(v);
            tvSubject = v.findViewById(R.id.tv_weak_subject);
            tvAcc = v.findViewById(R.id.tv_weak_accuracy);
            pb = v.findViewById(R.id.pb_weak);
        }
    }
}
