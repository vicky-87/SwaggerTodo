package com.example.swaggertodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swaggertodo.api.helper.ServiceHelper;
import com.example.swaggertodo.api.helper.Session;
import com.example.swaggertodo.api.models.Envelope;
import com.example.swaggertodo.api.models.TaskModels;
import com.example.swaggertodo.api.models.Todo;
import com.example.swaggertodo.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTodoActivity extends AppCompatActivity {
    private Intent intent;
    private EditText task;
    private Button btnTask;
    private ApiInterface apiInterface;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        task = findViewById(R.id.taskName);
        session = new Session(this);
    }

    public void handleSubmitTodo(View view) {
        TaskModels taskModel = new TaskModels(task.getText().toString());
        String token = session.getToken();
        apiInterface = ServiceHelper.createService(ApiInterface.class, "Bearer "+token);
        Call<Envelope<Todo>> call = apiInterface.createTodo(taskModel);
        call.enqueue(new Callback<Envelope<Todo>>() {
            @Override
            public void onResponse(Call<Envelope<Todo>> call, Response<Envelope<Todo>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddTodoActivity.this, "Sukses", Toast.LENGTH_LONG).show();
                    intent = new Intent(AddTodoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Envelope<Todo>> call, Throwable t) {
                Toast.makeText(AddTodoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
