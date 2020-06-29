package com.example.listadetarefas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listadetarefas.DAO.AppDatabase;
import com.example.listadetarefas.DAO.UserDao;
import com.example.listadetarefas.Model.User;

public class RegistrationActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;

    private Button btCancel;
    private Button btRegister;

    private UserDao userDao;

    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);


        edtName = findViewById(R.id.edtName);
        edtLastName = findViewById(R.id.edtLastname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btCancel = findViewById(R.id.btnCancelar);
        btRegister = findViewById(R.id.btnRegistrar);

        userDao = Room.databaseBuilder(this, AppDatabase.class, "db_tarefas")
                .allowMainThreadQueries()
                .build()
                .getUserDao();

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                finish();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {

                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(edtName.getText().toString(), edtLastName.getText().toString(),
                                    edtEmail.getText().toString(), edtPassword.getText().toString());
                                if(userDao.getCount(user.getEmail()) == 0){
                                userDao.insert(user);
                                progressDialog.dismiss();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                        }else{
                                    progressDialog.dismiss();
                               Toast.makeText(RegistrationActivity.this, "Usuário existente", Toast.LENGTH_SHORT).show();

                                }}
                    }, 1000);

                } else {
                    Toast.makeText(RegistrationActivity.this, "Campos Vazios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) ||
                TextUtils.isEmpty(edtPassword.getText().toString()) ||
                TextUtils.isEmpty(edtName.getText().toString()) ||
                TextUtils.isEmpty(edtLastName.getText().toString())
        ) {
            return true;
        } else {
            return false;
        }
    }
}