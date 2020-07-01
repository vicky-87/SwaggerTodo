package com.example.swaggertodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swaggertodo.api.helper.ServiceHelper;
import com.example.swaggertodo.api.helper.Session;
import com.example.swaggertodo.api.models.LoginRequest;
import com.example.swaggertodo.api.models.LoginResponse;
import com.example.swaggertodo.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Intent intent;
    private EditText username;
    private EditText password;
    private ApiInterface apiInterface;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(this);
        username = findViewById(R.id.usernameET);
        password = findViewById(R.id.passwordET);
    }

    public void handleLogin(View view) {
        apiInterface = ServiceHelper.createService(ApiInterface.class);
        String usernm = username.getText().toString();
        String passwd = password.getText().toString();
        LoginRequest loginRequest = new LoginRequest(usernm, passwd);
        Call<LoginResponse> call = apiInterface.doLogin(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // logic jika sukses login
                if (response.isSuccessful()){
                    session.setToken(response.body().getToken());
                    session.setIsLogin(true);
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //logic jika gagal login
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handleRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
