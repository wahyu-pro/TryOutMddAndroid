package com.wahyu.todoapp.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wahyu.todoapp.activity.MainActivity;
import com.wahyu.todoapp.client.ApiClient;
import com.wahyu.todoapp.databinding.ActivityMainBinding;
import com.wahyu.todoapp.models.DataItem;
import com.wahyu.todoapp.models.Response;
import com.wahyu.todoapp.service.TodoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TodoViewModel extends ViewModel {

    MutableLiveData<List<DataItem>> listTodos = new MutableLiveData<>();

    public LiveData<List<DataItem>> getListTodos() {
        return listTodos;
    }

    private List<DataItem> temp = new ArrayList<>();

    private final String BASE_URL = "https://online-course-todo.herokuapp.com/api/";

    public void setListTodos() {
        ApiClient.client(TodoService.class, BASE_URL)
                .getResponse().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                listTodos.setValue(response.body().getData());
                temp = listTodos.getValue();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void searchTodos(CharSequence s){
        List<DataItem> todosModel = new ArrayList<>();

        if (listTodos.getValue() != null){
            for (DataItem todo : temp){
                if (todo.getTask().toLowerCase().contains(s)){
                    todosModel.add(todo);
                }
            }
            listTodos.setValue(todosModel);
        }
    }

    public void updateTodos(DataItem todos){
        ApiClient.client(TodoService.class, BASE_URL).updateTodos(todos.getId(), todos).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void deleteTodos(DataItem todos){
        ApiClient.client(TodoService.class, BASE_URL).deleteTodo(todos.getId()).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    public void updateStatus(DataItem todos, Boolean status){
        if (status == true){
            todos.setStatus(false);
        }else {
            todos.setStatus(true);
        }
        ApiClient.client(TodoService.class, BASE_URL).updateStatus(todos.getId(), todos).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    public void addData(DataItem todos){
        ApiClient.client(TodoService.class, BASE_URL).addTodos(todos).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

}
