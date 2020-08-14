package com.wahyu.todoapp.service;

import com.wahyu.todoapp.models.DataItem;
import com.wahyu.todoapp.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoService {
    @GET("v1/todos")
    Call<Response> getResponse();

    @Headers("Content-Type: application/json")
    @PUT("v1/todos/{id}")
    Call<Response> updateTodos(@Path("id") int id, @Body DataItem todos);

    @Headers("Content-Type: application/json")
    @POST("v1/todos")
    Call<Response> addTodos(@Body DataItem todos);

    @DELETE("v1/todos/{id}")
    Call<Response> deleteTodo(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @PUT("v1/todos/{id}")
    Call<Response> updateStatus(@Path("id") int id, @Body DataItem todos);
}
