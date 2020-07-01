package com.example.swaggertodo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swaggertodo.R;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{
    public interface todoListener{
        void onClickedTodo(int index, Todo todo);
    }
    private List<Todo> todos;
    private TodoAdapter.todoListener todoListener;

    public TodoAdapter(List<Todo> todos, TodoAdapter.todoListener todoListener) {
        this.todos = todos;
        this.todoListener = todoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.bind(position, todo);
    }

    @Override
    public int getItemCount() {
        return (todos != null)?todos.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todoText;
        TextView isDoneText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todoText);
            isDoneText = itemView.findViewById(R.id.isDoneTxt);
        }
        public void bind(final int index, final Todo todo){
            String isDone;
            todoText.setText(todo.getTodo());
            if (todo.getDone()){
                isDone = "Done";
            }else {
                isDone = "Doing";
            }
            isDoneText.setText(isDone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoListener.onClickedTodo(index, todo);
                }
            });
    }
}
