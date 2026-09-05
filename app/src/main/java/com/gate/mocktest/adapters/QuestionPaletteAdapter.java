package com.gate.mocktest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gate.mocktest.R;
import com.gate.mocktest.models.ExamSession;

public class QuestionPaletteAdapter extends RecyclerView.Adapter<QuestionPaletteAdapter.VH> {

    public interface OnClickListener {
        void onClick(int index);
    }

    private ExamSession session;
    private final OnClickListener listener;

    public QuestionPaletteAdapter(OnClickListener listener) {
        this.listener = listener;
    }

    public void update(ExamSession session) {
        this.session = session;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_palette_dot, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        if (session == null) return;

        int status = session.getQuestionStatus(pos);
        boolean isCurrent = (pos == session.getCurrentIndex());

        h.tv.setText(String.valueOf(pos + 1));

        int bgColor, textColor;
        if (isCurrent) {
            bgColor = R.color.palette_current;
            textColor = android.R.color.white;
        } else {
            switch (status) {
                case ExamSession.ANSWERED:
                    bgColor = R.color.palette_answered;
                    textColor = android.R.color.white;
                    break;
                case ExamSession.MARKED:
                case ExamSession.ANSWERED_MARKED:
                    bgColor = R.color.palette_marked;
                    textColor = android.R.color.white;
                    break;
                case ExamSession.NOT_ANSWERED:
                    bgColor = android.R.color.white;
                    textColor = R.color.text_secondary;
                    break;
                default:
                    bgColor = R.color.palette_not_visited;
                    textColor = R.color.text_secondary;
                    break;
            }
        }

        h.itemView.setBackgroundTintList(
                ContextCompat.getColorStateList(h.itemView.getContext(), bgColor));
        h.tv.setTextColor(
                ContextCompat.getColor(h.itemView.getContext(), textColor));
        h.itemView.setOnClickListener(v -> listener.onClick(pos));
    }

    @Override
    public int getItemCount() {
        return session != null ? session.getTotalQuestions() : 0;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(View v) {
            super(v);
            tv = v.findViewById(R.id.tv_number);
        }
    }
}
