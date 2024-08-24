package com.example.mealplanner.Database.Model.User;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(User user);


    @Query("SELECT * FROM user WHERE userId = :userId")
    LiveData<User> getUserById(String userId);


    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}

