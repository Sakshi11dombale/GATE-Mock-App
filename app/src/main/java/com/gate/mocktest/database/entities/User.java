package com.gate.mocktest.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;
@Entity(tableName="users",indices={@Index(value="username",unique=true),@Index(value="email",unique=true)})
public class User {
    @PrimaryKey(autoGenerate=true) public int id;
    public String name,username,email,password,branch;
    public int targetYear;
    public long registeredAt;
    public boolean isLoggedIn;
}
