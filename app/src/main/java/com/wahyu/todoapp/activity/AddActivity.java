package com.wahyu.todoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wahyu.todoapp.R;
import com.wahyu.todoapp.databinding.ActivityAddBinding;
import com.wahyu.todoapp.models.DataItem;
import com.wahyu.todoapp.viewmodel.TodoViewModel;

public class AddActivity extends AppCompatActivity {

    TodoViewModel viewModel;
    ActivityAddBinding binding;
    DataItem todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        todos = new DataItem();
        viewModel = new ViewModelProvider(AddActivity.this, new ViewModelProvider.NewInstanceFactory()).get(TodoViewModel.class);
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todos.setTask(binding.etEdit.getText().toString());
                viewModel.addData(todos);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}