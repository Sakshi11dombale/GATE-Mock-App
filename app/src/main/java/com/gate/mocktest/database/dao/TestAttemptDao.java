package com.gate.mocktest.database.dao;
import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.gate.mocktest.database.entities.TestAttempt;
import java.util.List;
@Dao
public interface TestAttemptDao {
    @Insert long insert(TestAttempt a);
    @Query("SELECT * FROM test_attempts WHERE userId=:uid ORDER BY attemptDate DESC") LiveData<List<TestAttempt>> getUserAttempts(int uid);
    @Query("SELECT * FROM test_attempts WHERE userId=:uid ORDER BY attemptDate DESC LIMIT 5") LiveData<List<TestAttempt>> getRecentAttempts(int uid);
    @Query("SELECT AVG(accuracy) FROM test_attempts WHERE userId=:uid") LiveData<Float> getOverallAccuracy(int uid);
    @Query("SELECT COUNT(*) FROM test_attempts WHERE userId=:uid") LiveData<Integer> getTotalTestCount(int uid);
    @Query("SELECT SUM(timeTakenMillis) FROM test_attempts WHERE userId=:uid") LiveData<Long> getTotalStudyTime(int uid);
}
