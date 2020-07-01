package com.example.swaggertodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.swaggertodo.adapter.TodoAdapter;
import com.example.swaggertodo.api.helper.ServiceHelper;
import com.example.swaggertodo.api.helper.Session;
import com.example.swaggertodo.api.models.Envelope;
import com.example.swaggertodo.api.models.Todo;
import com.example.swaggertodo.api.services.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MainActivity extends AppCompatActivity implements TodoAdapter.todoListener  {
    public final static int VIEW_REQUEST = 1;
    public final static String KEY_DATA = "KEY_DATA";

    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<Todo> todos;
    private ApiInterface apiInterface;
    private com.example.swaggertodo.api.helper.Session session;
    private String token;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);
        token = session.getToken();
        recyclerView = findViewById(R.id.todosRV);
        todos = new ArrayList<>();
        setRecyclerView();
        getTodos();
        onClickFab();
    }

    private void onClickFab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setRecyclerView(){
        todoAdapter = new TodoAdapter(todos, this);
        recyclerView.setAdapter(todoAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        todos.clear();
        todoAdapter.notifyDataSetChanged();
    }

    private void getTodos(){
        apiInterface = ServiceHelper.createService(ApiInterface.class, "Bearer "+token);
        Call<Envelope<List<Todo>>> call = apiInterface.getTodos();
        call.enqueue(new Callback<Envelope<List<Todo>>>() {
            @Override
            public void onResponse(Call<Envelope<List<Todo>>> call, Response<Envelope<List<Todo>>> response) {
                if (response.isSuccessful()){
                    //menghapus
                    todos.clear();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        int id = response.body().getData().get(i).getId();
                        String task = response.body().getData().get(i).getTodo();
                        Boolean isDone = response.body().getData().get(i).getDone();
                        todos.add(new Todo(id, task, isDone));
                        todoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Envelope<List<Todo>>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "Sukses", Toast.LENGTH_LONG).show();
                getTodos();
            }
        });

    }

    @Override
    public void onClickedTodo(int index, Todo todo) {
        intent = new Intent(this, ReviewActivity.class);
        intent.putExtra(KEY_DATA, todo);
        startActivityForResult(intent, VIEW_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            getTodos();
            return;
        }
        if (resultCode == RESULT_OK){
            getTodos();
        }
    }
}