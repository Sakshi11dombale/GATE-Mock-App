package com.gate.mocktest.database.dao;
import androidx.room.*;
import com.gate.mocktest.database.entities.User;
@Dao
public interface UserDao {
    @Insert(onConflict=OnConflictStrategy.ABORT) long insert(User u);
    @Update void update(User u);
    @Query("SELECT * FROM users WHERE username=:u AND password=:p LIMIT 1") User login(String u,String p);
    @Query("SELECT * FROM users WHERE username=:u LIMIT 1") User findByUsername(String u);
    @Query("SELECT * FROM users WHERE email=:e LIMIT 1") User findByEmail(String e);
    @Query("SELECT * FROM users WHERE isLoggedIn=1 LIMIT 1") User getLoggedInUser();
    @Query("UPDATE users SET isLoggedIn=0") void logoutAll();
    @Query("UPDATE users SET isLoggedIn=1 WHERE id=:id") void setLoggedIn(int id);
    @Query("UPDATE users SET branch=:branch WHERE id=:id") void updateBranch(int id,String branch);
}
