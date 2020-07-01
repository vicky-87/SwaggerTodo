package com.example.swaggertodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swaggertodo.api.helper.ServiceHelper;
import com.example.swaggertodo.api.helper.Session;
import com.example.swaggertodo.api.models.Envelope;
import com.example.swaggertodo.api.models.Todo;
import com.example.swaggertodo.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    public final static String DATA_EDIT_KEY = "EDIT";
    public final static int KEY_EDIT = 2;
    private TextView taskName;
    private TextView isDoingName;
    private Todo todo;
    private ApiInterface apiInterface;
    private Session session;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        session = new Session(this);
        taskName = findViewById(R.id.taskTV);
        isDoingName = findViewById(R.id.statusTV);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Todo todod = getIntent().getParcelableExtra(MainActivity.KEY_DATA);
            fetchDatas(todod.getId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == KEY_EDIT) {
                fetchDatas(todo.getId());
            }
        }
    }

    private void fetchDatas(int id) {
        apiInterface = ServiceHelper.createService(ApiInterface.class, "Bearer " + session.getToken());
        Call<Envelope<Todo>> call = apiInterface.getTodosById(id);
        call.enqueue(new Callback<Envelope<Todo>>() {
            @Override
            public void onResponse(Call<Envelope<Todo>> call, Response<Envelope<Todo>> response) {
                if (response.isSuccessful()) {
                    int id = response.body().getData().getId();
                    String task = response.body().getData().getTodo();
                    Boolean isDone = response.body().getData().getDone();
                    todo = new Todo(id, task, isDone);
                    taskName.setText(todo.getTodo());
                    if (todo.getDone()) {
                        isDoingName.setText("Done");
                    } else {
                        isDoingName.setText("Doing");
                    }
                }
            }

            @Override
            public void onFailure(Call<Envelope<Todo>> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handleChangeStatus(View view) {
        if (todo.getDone()){
            apiInterface = ServiceHelper.createService(ApiInterface.class, "Bearer "+session.getToken());
            Call<Envelope<Todo>> call = apiInterface.undoneTodo(todo.getId());
            call.enqueue(new Callback<Envelope<Todo>>() {
                @Override
                public void onResponse(Call<Envelope<Todo>> call, Response<Envelope<Todo>> response) {
                    if (response.isSuccessful()){
                        String pesan = response.body().getData().getTodo()+" Diubah menjadi belum selesai";
                        Toast.makeText(ReviewActivity.this, pesan, Toast.LENGTH_LONG).show();
                        isDoingName.setText("Doing");
                    }
                }

                @Override
                public void onFailure(Call<Envelope<Todo>> call, Throwable t) {
                    Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            apiInterface = ServiceHelper.createService(ApiInterface.class, "Bearer "+session.getToken());
            Call<Envelope<Todo>> call = apiInterface.doneTodo(todo.getId());
            call.enqueue(new Callback<Envelope<Todo>>() {
                @Override
                public void onResponse(Call<Envelope<Todo>> call, Response<Envelope<Todo>> response) {
                    if (response.isSuccessful()){
                        String pesan = response.body().getData().getTodo()+" Diubah menjadi selesai";
                        Toast.makeText(ReviewActivity.this, pesan, Toast.LENGTH_LONG).show();
                        isDoingName.setText("Done");
                    }
                }

                @Override
                public void onFailure(Call<Envelope<Todo>> call, Throwable t) {
                    Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onEditTodo(View view) {
        intent = new Intent(this, EditTodoActivity.class);
        intent.putExtra(DATA_EDIT_KEY, todo);
        startActivityForResult(intent, KEY_EDIT);
    }

    public void handleDeleteTodo(View view) {
        apiInterface = ServiceHelper.createService(ApiInterface.class, "Bearer "+session.getToken());
        Call<Todo> call =  apiInterface.deleteTodo(todo.getId());
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()){
                    String pesan = response.body().getTodo()+" Berhasil di hapus";
                    Toast.makeText(ReviewActivity.this, pesan , Toast.LENGTH_LONG).show();
                    intent = new Intent();
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
