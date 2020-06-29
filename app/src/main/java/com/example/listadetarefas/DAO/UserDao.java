package com.example.listadetarefas.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listadetarefas.Model.User;


@Dao
public interface UserDao {
    @Query("SELECT * FROM User where email= :mail and password= :password")

    User getUser(String mail, String password);

    @Query("SELECT COUNT() FROM User where email= :mail ")
    int getCount(String mail);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}