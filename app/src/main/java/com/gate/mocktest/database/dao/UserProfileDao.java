package com.gate.mocktest.database.dao;
import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.gate.mocktest.database.entities.UserProfile;
@Dao
public interface UserProfileDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE) void insert(UserProfile p);
    @Update void update(UserProfile p);
    @Query("SELECT * FROM user_profile WHERE id=1") LiveData<UserProfile> getProfile();
    @Query("UPDATE user_profile SET totalTestsTaken=totalTestsTaken+1 WHERE id=1") void incrementTestCount();
    @Query("UPDATE user_profile SET totalQuestionsSolved=totalQuestionsSolved+:c WHERE id=1") void addQuestionsSolved(int c);
}
