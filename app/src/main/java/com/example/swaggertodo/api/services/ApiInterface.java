package com.example.swaggertodo.api.services;

import com.example.swaggertodo.api.models.Envelope;
import com.example.swaggertodo.api.models.LoginRequest;
import com.example.swaggertodo.api.models.LoginResponse;
import com.example.swaggertodo.api.models.RegisterRequest;
import com.example.swaggertodo.api.models.RegisterResponse;
import com.example.swaggertodo.api.models.TaskModels;
import com.example.swaggertodo.api.models.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiInterface {
    //fungsi buat login
    //Login
    @POST("auth/token")
    public Call<LoginResponse> doLogin(@Body LoginRequest loginRequest) {
        return null;
    }

    //register
    @POST("users")
    public Call<Envelope<RegisterResponse>> doRegister(@Body RegisterRequest registerRequest) {
        return null;
    }

    //Fungsi buat semua fungsi to-do
    //Melihat semua list to-do
    @GET("todos")
    public Call<Envelope<List<Todo>>> getTodos() {
        return null;
    }

    //melihat to-do berdasarkan id
    @GET("todos/{id}")
    public Call<Envelope<Todo>> getTodosById(@Path("id") int id) {
        return null;
    }

    //Membuat to-do
    @POST("todos")
    public Call<Envelope<Todo>> createTodo(@Body TaskModels taskModel) {
        return null;
    }

    //mengubah to-do berdasarkan id
    @PUT("todos/{id}")
    public Call<Envelope<Todo>> editTodo(@Body TaskModels taskModel, @Path("id") int id) {
        return null;
    }

    //Mengubah status menjadi done
    @POST("todos/{id}/done")
    public Call<Envelope<Todo>> doneTodo(@Path("id") int id) {
        return null;
    }

    //mengubah status menjadi undone
    @POST("todos/{id}/undone")
    public Call<Envelope<Todo>> undoneTodo(@Path("id") int id) {
        return null;
    }

    //Menghapus todo berdasarkan id
    @DELETE("todos/{id}")
    public Call<Todo> deleteTodo(@Path("id") int id) {
        return null;
    }

}
