package com.example.listadetarefas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.listadetarefas.DAO.AppDatabase;

import com.example.listadetarefas.Model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListarTarefasActivity extends AppCompatActivity {

    private ListView lsvListarTarefas;
    private List<Tarefa> tarefas;
    private FloatingActionButton fabCadastrarTarefa;

    public void atualizarLista(){
        tarefas = AppDatabase.getAppDatabase(this).tarefaDAO().listarTodos();
        ListarTarefasAdapter adapter = new ListarTarefasAdapter(tarefas, this);
        lsvListarTarefas.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_tarefas);

        lsvListarTarefas = (ListView) findViewById(R.id.lsv_listar_tarefas);
        fabCadastrarTarefa = (FloatingActionButton) findViewById(R.id.fab_cadastrar_tarefa);

        fabCadastrarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarTarefasActivity.this, CadastrarTarefaActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        lsvListarTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListarTarefasActivity.this, AlterarTarefaActivity.class);
                intent.putExtra("id_tarefa", tarefas.get(position).getId());
                startActivityForResult(intent, 2);
            }
        });

        lsvListarTarefas.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                final Tarefa tarefaSelecionada = (Tarefa) lsvListarTarefas.getAdapter().getItem(info.position);
                MenuItem compartilhar = menu.add("Compartilhar");
                MenuItem deletar = menu.add("Deletar");

                deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        AlertDialog a = new AlertDialog.Builder(ListarTarefasActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Deletar")
                                .setMessage("Deseja realmente excluir?")
                                .setPositiveButton("Sim",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                AppDatabase.getAppDatabase(ListarTarefasActivity.this).tarefaDAO().deletar(tarefaSelecionada);
                                                atualizarLista();
                                                Snackbar.make(findViewById(R.id.layout_listar_tarefas), "Deletado com sucesso", Snackbar.LENGTH_LONG).show();
                                            }
                                        })
                                .setNegativeButton("Não", null)
                                .show();

                        return false;
                    }
                });
            }});
    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizarLista();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            Snackbar.make(findViewById(R.id.lsv_listar_tarefas), "Tarefa cadastrada com sucesso!", Snackbar.LENGTH_LONG).show();
        } else if (resultCode == RESULT_OK && requestCode == 2){
            Snackbar.make(findViewById(R.id.lsv_listar_tarefas), "Tarefa alterada com sucesso!", Snackbar.LENGTH_LONG).show();
        }
    }
}
