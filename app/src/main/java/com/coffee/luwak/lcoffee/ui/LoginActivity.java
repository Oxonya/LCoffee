package com.coffee.luwak.lcoffee.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coffee.luwak.lcoffee.App;
import com.coffee.luwak.lcoffee.R;
import com.coffee.luwak.lcoffee.model.Role;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends MasterActivity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";

    Button btnCreate, btnLogin;
    EditText etEmail, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnCreate = findViewById(R.id.btnCreateAcc);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);

        btnCreate.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateAcc:
                createAccount(etEmail.getText().toString(), etPass.getText().toString());
                break;
            case R.id.btnLogin:
                signIn(etEmail.getText().toString(), etPass.getText().toString());
                break;
            default:
                break;
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgress();

        App.fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            relaunch();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            toast("Не удалось авторизоваться");

                            hideProgress();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgress();

        App.fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgress();

                        if (task.isSuccessful()) {
                            // Добро пожаловать, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            relaunch();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            toast("Не удалось авторизоваться");

                            hideProgress();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Обязательно");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        String password = etPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPass.setError("Обязательно");
            valid = false;
        } else if(password.length() < 6) {
            valid = false;
            etPass.setError("Не менее 6 символов");
        } else {
            etPass.setError(null);
        }

        return valid;
    }

    /**
     * Здесь мы связываемся с Firebase Realtime Database и получаем ящики админов и барист
     * Возможно, это не самый лучший похдох, но тем не менее.
     * Сравниваем текущий ящик с тем, что есть в БД
     */
    @Override
    public void relaunch() {
        if (App.fbAuth.getCurrentUser() != null) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // посмотрим всех админов
                    for (DataSnapshot ds : dataSnapshot.child("admins").getChildren()) {
                        String str = ds.getValue(String.class);
                        if (str != null && App.fbAuth.getCurrentUser() != null) {
                            if (str.equals(App.fbAuth.getCurrentUser().getEmail())) {
                                App.setCurrentRole(Role.admin);
                                hideProgress();
                                LoginActivity.super.relaunch();
                                return;
                            }
                        }
                    }

                    // посмотрим всех барист
                    for (DataSnapshot ds : dataSnapshot.child("baristas").getChildren()) {
                        String str = ds.getValue(String.class);
                        if (str != null && App.fbAuth.getCurrentUser() != null) {
                            if (str.equals(App.fbAuth.getCurrentUser().getEmail())) {
                                App.setCurrentRole(Role.barista);
                                hideProgress();
                                LoginActivity.super.relaunch();
                                return;
                            }
                        }
                    }

                    hideProgress();
                    LoginActivity.super.relaunch();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // stub
                }
            });
        }
    }
}
