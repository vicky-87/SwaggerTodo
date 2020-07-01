package com.example.swaggertodo.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Todo implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("todo")
    private String todo;
    @SerializedName("done")
    private Boolean done;
    @SerializedName("user")
    private UserModel user;

    public Todo() {
    }

    public Todo(int id, String todo, Boolean done, UserModel user) {
        this.id = id;
        this.todo = todo;
        this.done = done;
        this.user = user;
    }

    public Todo(int id, String todo, Boolean done) {
        this.id = id;
        this.todo = todo;
        this.done = done;
    }

    protected Todo(Parcel in) {
        id = in.readInt();
        todo = in.readString();
        byte tmpDone = in.readByte();
        done = tmpDone == 0 ? null : tmpDone == 1;
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(todo);
        dest.writeByte((byte) (done == null ? 0 : done ? 1 : 2));
    }
}
