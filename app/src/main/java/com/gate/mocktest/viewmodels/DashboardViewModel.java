package com.gate.mocktest.viewmodels;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.*;
import java.util.List;
public class DashboardViewModel extends AndroidViewModel {
    private final AppDatabase db;
    public final LiveData<UserProfile> userProfile;
    public LiveData<List<TestAttempt>> recentAttempts;
    public LiveData<Float> overallAccuracy;
    public LiveData<Integer> totalTests;
    public LiveData<Long> totalStudyTime;
    private int currentUserId=0;
    public DashboardViewModel(@NonNull Application app){
        super(app);db=AppDatabase.getInstance(app);
        userProfile=db.userProfileDao().getProfile();
    }
    public void init(int userId){
        currentUserId=userId;
        recentAttempts=db.testAttemptDao().getRecentAttempts(userId);
        overallAccuracy=db.testAttemptDao().getOverallAccuracy(userId);
        totalTests=db.testAttemptDao().getTotalTestCount(userId);
        totalStudyTime=db.testAttemptDao().getTotalStudyTime(userId);
    }
    public void saveAttempt(TestAttempt a){
        AppDatabase.databaseWriteExecutor.execute(()->{
            db.testAttemptDao().insert(a);
            db.userProfileDao().incrementTestCount();
            db.userProfileDao().addQuestionsSolved(a.totalQuestions);
        });
    }
}
