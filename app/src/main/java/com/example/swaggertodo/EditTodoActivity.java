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
import com.example.swaggertodo.api.models.TaskModels;
import com.example.swaggertodo.api.models.Todo;
import com.example.swaggertodo.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTodoActivity extends AppCompatActivity {
    private static final String TAG = EditTodoActivity.class.getCanonicalName();
    private EditText taskEdit;
    private Todo todos;
    private ApiInterface apiInterface;
    private Session session;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        session = new Session(this);
        taskEdit = findViewById(R.id.taskEditET);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            todos = getIntent().getParcelableExtra(ReviewActivity.DATA_EDIT_KEY);
            taskEdit.setText(todos.getTodo());
        }
    }

    public void handleSubmitChangeTask(View view) {
        TaskModels taskModel = new TaskModels(taskEdit.getText().toString());
        apiInterface = ServiceHelper.createService(ApiInterface.class, "Bearer "+session.getToken());
        Call<Envelope<Todo>> call = apiInterface.editTodo(taskModel, todos.getId());
        call.enqueue(new Callback<Envelope<Todo>>() {
            @Override
            public void onResponse(Call<Envelope<Todo>> call, Response<Envelope<Todo>> response) {
                if (response.isSuccessful()){
                    intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Envelope<Todo>> call, Throwable t) {
                Toast.makeText(EditTodoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
