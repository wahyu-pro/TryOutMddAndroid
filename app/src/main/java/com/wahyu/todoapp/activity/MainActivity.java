package com.wahyu.todoapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.wahyu.todoapp.R;
import com.wahyu.todoapp.adapter.TodoAdapter;
import com.wahyu.todoapp.databinding.ActivityMainBinding;
import com.wahyu.todoapp.models.DataItem;
import com.wahyu.todoapp.util.ViewUtil;
import com.wahyu.todoapp.viewmodel.TodoViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    TodoViewModel viewModel;
    TodoAdapter adapter;
    ActivityMainBinding binding;

    public static final String DATA_TODOS = "DATA_TODOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setIsLoading(true);
        adapter = new TodoAdapter();
        viewModel = new ViewModelProvider(MainActivity.this, new ViewModelProvider.NewInstanceFactory()).get(TodoViewModel.class);
        viewModel.setListTodos();
        getAllTodos();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.searchTodos(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                ViewUtil.hideKeyboard(textView);
                return true;
            }

            return false;
        });

        adapter.setListener(new TodoAdapter.TodosListener() {
            @Override
            public void onUpdate(DataItem todos) {
                startActivity(new Intent(
                        MainActivity.this,
                        TodosActivity.class).putExtra(DATA_TODOS, todos)
                );
            }

            @Override
            public void onDelete(DataItem todos) {
                viewModel.deleteTodos(todos);
            }

            @Override
            public void onUpdateStatus(DataItem todos, Boolean status) {
                viewModel.updateStatus(todos, status);
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }

    private void getAllTodos() {
        viewModel.getListTodos().observe(MainActivity.this, new Observer<List<DataItem>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<DataItem> dataItems) {
                adapter.setListTodos(dataItems);
                binding.setIsLoading(false);
            }
        });
    }
}