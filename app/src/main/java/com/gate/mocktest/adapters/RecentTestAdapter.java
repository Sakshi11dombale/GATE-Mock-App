package com.gate.mocktest.adapters;

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
import com.gate.mocktest.database.entities.TestAttempt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecentTestAdapter extends ListAdapter<TestAttempt, RecentTestAdapter.VH> {

    public interface OnClickListener {
        void onClick(TestAttempt attempt);
    }

    private final OnClickListener listener;

    public RecentTestAdapter(OnClickListener listener) {
        super(new DiffUtil.ItemCallback<TestAttempt>() {
            @Override
            public boolean areItemsTheSame(@NonNull TestAttempt a, @NonNull TestAttempt b) {
                return a.id == b.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull TestAttempt a, @NonNull TestAttempt b) {
                return a.id == b.id && a.score == b.score;
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        TestAttempt a = getItem(pos);

        h.tvName.setText(a.testName);
        h.tvScore.setText(String.format("%.2f", a.score));
        h.tvMax.setText("/ " + String.format("%.0f", a.maxScore));
        h.tvCorrect.setText("✓ " + a.correct + " correct");
        h.tvWrong.setText("✗ " + a.wrong + " wrong");
        h.tvAcc.setText(String.format("%.0f%%", a.accuracy) + " acc");
        h.tvDate.setText(new SimpleDateFormat("dd MMM · HH:mm", Locale.getDefault())
                .format(new Date(a.attemptDate)));

        int pct = a.maxScore > 0 ? (int) ((a.score / a.maxScore) * 100) : 0;
        h.pb.setProgress(pct);

        h.itemView.setOnClickListener(v -> listener.onClick(a));
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvScore, tvMax, tvCorrect, tvWrong, tvAcc, tvDate;
        ProgressBar pb;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_result_test_name);
            tvScore = v.findViewById(R.id.tv_result_score);
            tvMax = v.findViewById(R.id.tv_result_max);
            tvCorrect = v.findViewById(R.id.tv_correct_count);
            tvWrong = v.findViewById(R.id.tv_wrong_count);
            tvAcc = v.findViewById(R.id.tv_accuracy_result);
            tvDate = v.findViewById(R.id.tv_result_date);
            pb = v.findViewById(R.id.pb_score);
        }
    }
}
