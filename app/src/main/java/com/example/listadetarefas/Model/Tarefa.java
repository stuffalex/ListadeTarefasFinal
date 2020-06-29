package com.example.listadetarefas.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "tarefas",
        foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "user_id")
)
public class Tarefa {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String descricao;
    private boolean realizado = false;
    private long dataHora;

    private int user_id;


    public Tarefa(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;

    }

    public Tarefa() {
    }

    @Override
    public String
    toString() {
        return id + " - " + descricao + " - " + realizado + " - " + dataHora;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public long getDataHora() {
        return dataHora;
    }

    public void setDataHora(long dataHora) {
        this.dataHora = dataHora;
    }
}
