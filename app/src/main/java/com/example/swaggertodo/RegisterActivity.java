package com.example.swaggertodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swaggertodo.api.helper.ServiceHelper;
import com.example.swaggertodo.api.helper.Session;
import com.example.swaggertodo.api.models.Envelope;
import com.example.swaggertodo.api.models.LoginRequest;
import com.example.swaggertodo.api.models.LoginResponse;
import com.example.swaggertodo.api.models.RegisterRequest;
import com.example.swaggertodo.api.models.RegisterResponse;
import com.example.swaggertodo.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Intent intent;
    private EditText username;
    private EditText name;
    private EditText password;
    private ApiInterface apiInterface;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        session = new Session(this);
        username = findViewById(R.id.usernameET);
        password = findViewById(R.id.passwordET);
        name = findViewById(R.id.nameET);
    }

    public void handleRegisterAction(View view) {
        apiInterface = ServiceHelper.createService(ApiInterface.class);
        final String usernm = username.getText().toString();
        final String passwd = password.getText().toString();
        String nama = name.getText().toString();
        RegisterRequest registerRequest = new RegisterRequest(usernm, nama, passwd);
        Call<Envelope<RegisterResponse>> call = apiInterface.doRegister(registerRequest);
        call.enqueue(new Callback<Envelope<RegisterResponse>>() {
            @Override
            public void onResponse(Call<Envelope<RegisterResponse>> call, Response<Envelope<RegisterResponse>> response) {
                if (response.isSuccessful()){
                    doLoginAfterRegister(usernm, passwd);
                }
            }

            @Override
            public void onFailure(Call<Envelope<RegisterResponse>> call, Throwable t) {
                //logic saat gagal register akun
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void doLoginAfterRegister(String username, String password){
        apiInterface = ServiceHelper.createService(ApiInterface.class);
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> call = apiInterface.doLogin(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // logic jika sukses login
                if (response.isSuccessful()){
                    session.setToken(response.body().getToken());
                    session.setIsLogin(true);
                    intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //logic jika gagal login
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
