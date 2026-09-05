package com.gate.mocktest.viewmodels;

import android.app.Application;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.Question;
import com.gate.mocktest.models.ExamSession;
import com.gate.mocktest.utils.JsonQuestionLoader;
import java.util.*;

public class ExamViewModel extends AndroidViewModel {
    private final AppDatabase db;
    private ExamSession session;
    private CountDownTimer timer;
    private final MutableLiveData<ExamSession> sessionLive = new MutableLiveData<>();
    private final MutableLiveData<Long> timeRemaining = new MutableLiveData<>();
    private final MutableLiveData<Boolean> timeUp = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public ExamViewModel(@NonNull Application app) {
        super(app);
        db = AppDatabase.getInstance(app);
    }

    /**
     * Builds a compact GATE-pattern practice paper instead of taking random questions
     * from the whole database. Technical questions are branch-specific; General Aptitude
     * is deliberately added as a small, separate section.
     */
    public void startMockTest(String branch, int count, long dur) {
        load(() -> {
            List<Question> all = JsonQuestionLoader.loadMockBank(getApplication(), branch);
            Collections.shuffle(all);
            if (count > 0 && all.size() > count) return new ArrayList<>(all.subList(0, count));
            return all;
        }, dur, "GATE Full Mock", ExamSession.TYPE_MOCK, branch, "");
    }

    public void startTopicTest(String subject, String altSubject, String branch, int count, long dur) {
        final String query = (altSubject == null || altSubject.trim().isEmpty()) ? subject : altSubject;
        load(() -> { List<Question> q=JsonQuestionLoader.loadTopic(getApplication(), branch, query); Collections.shuffle(q); if(count>0 && q.size()>count) return new ArrayList<>(q.subList(0,count)); return q; }, dur,
                subject + " Practice", ExamSession.TYPE_TOPIC, branch, subject);
    }

    public void startPYQTest(String branch, int year, long dur) {
        load(() -> JsonQuestionLoader.loadPYQ(getApplication(), branch, year), dur,
                "GATE " + year + " PYQ", ExamSession.TYPE_PYQ, branch, "");
    }

    private interface QuestionLoader { List<Question> get(); }

    private void load(QuestionLoader loader, long dur, String name, String type, String branch, String subject) {
        loading.postValue(true);
        error.postValue(null);
        timeUp.postValue(false);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Question> q = loader.get();
            if (q == null) q = new ArrayList<>();
            // Never replace an empty topic result with unrelated questions.
            // A topic test must contain questions from the selected topic only.
            if (q.isEmpty()) {
                loading.postValue(false);
                error.postValue("No GATE questions are available for this test yet. Please refresh the question bank.");
                return;
            }
            session = new ExamSession(q, dur, name, type, branch, subject);
            sessionLive.postValue(session);
            loading.postValue(false);
            startTimer(dur);
        });
    }

    private void startTimer(long dur) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (timer != null) timer.cancel();
            timer = new CountDownTimer(dur, 1000) {
                @Override public void onTick(long m) { timeRemaining.setValue(m); }
                @Override public void onFinish() {
                    timeRemaining.setValue(0L);
                    timeUp.setValue(true);
                }
            }.start();
        });
    }

    public void pauseTimer() { if (timer != null) timer.cancel(); }
    public void selectAnswer(String opt) { if (session == null) return; session.selectAnswer(opt); sessionLive.setValue(session); }
    public void clearAnswer() { if (session == null) return; session.clearAnswer(); sessionLive.setValue(session); }
    public void markForReview() { if (session == null) return; session.markForReview(); session.goNext(); sessionLive.setValue(session); }
    public void goNext() { if (session == null) return; session.goNext(); sessionLive.setValue(session); }
    public void goPrev() { if (session == null) return; session.goPrev(); sessionLive.setValue(session); }
    public void navigateTo(int i) { if (session == null) return; session.navigateTo(i); sessionLive.setValue(session); }
    public ExamSession getSession() { return session; }
    public LiveData<ExamSession> getSessionLive() { return sessionLive; }
    public LiveData<Long> getTimeRemainingMillis() { return timeRemaining; }
    public LiveData<Boolean> getTimeUp() { return timeUp; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getError() { return error; }
    public static String formatTime(long ms) {
        long s = ms / 1000, h = s / 3600, m = (s % 3600) / 60, sec = s % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", h, m, sec);
    }
    @Override protected void onCleared() { super.onCleared(); if (timer != null) timer.cancel(); }
}
