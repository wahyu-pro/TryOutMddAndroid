package com.wahyu.todoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wahyu.todoapp.R;
import com.wahyu.todoapp.databinding.ActivityTodosBinding;
import com.wahyu.todoapp.models.DataItem;
import com.wahyu.todoapp.viewmodel.TodoViewModel;

public class TodosActivity extends AppCompatActivity {

    TodoViewModel viewModel;
    ActivityTodosBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todos);

        DataItem todos = getIntent().getParcelableExtra(MainActivity.DATA_TODOS);
        binding.setTodos(todos);

        viewModel = new ViewModelProvider(TodosActivity.this, new ViewModelProvider.NewInstanceFactory()).get(TodoViewModel.class);
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todos.setTask(binding.etEdit.getText().toString());
                viewModel.updateTodos(todos);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}