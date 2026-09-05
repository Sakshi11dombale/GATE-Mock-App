package com.gate.mocktest.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName="user_profile")
public class UserProfile {
    @PrimaryKey public int id=1;
    public String name,email,branch,avatarInitials;
    public int targetYear,totalTestsTaken,totalQuestionsSolved,currentStreak,maxStreak,globalRank;
    public long totalStudyTimeMillis,lastActiveDate;
    public float overallAccuracy;
}
