package com.example.listadetarefas.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.listadetarefas.Model.Tarefa;
import com.example.listadetarefas.Model.User;

@Database(entities = {Tarefa.class, User.class}, version = 1, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {
    public abstract TarefaDAO tarefaDAO();
    public abstract UserDao getUserDao();

    private static final String DB_NAME = "db_tarefas";
    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        } return appDatabase;
    }
}
