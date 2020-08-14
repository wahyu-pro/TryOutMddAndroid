package com.wahyu.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wahyu.todoapp.R;
import com.wahyu.todoapp.databinding.TodoItemsBinding;
import com.wahyu.todoapp.models.DataItem;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<DataItem> listTodos = new ArrayList<>();

    public void setListTodos(List<DataItem> listTodos) {
        this.listTodos = listTodos;
        notifyDataSetChanged();
    }

    public interface TodosListener {
        void onUpdate(DataItem todos);

        void onDelete(DataItem todos);

        void onUpdateStatus(DataItem todos, Boolean status);
    }

    private TodosListener listener;
    public void setListener(TodosListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoItemsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.todo_items,
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        holder.bindData(listTodos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (listTodos != null){
            return listTodos.size();
        }else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TodoItemsBinding binding;
        DateTimeFormatter formatter, localFormatter;
        public ViewHolder(@NonNull TodoItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(DataItem todos, TodosListener listener){
            binding.setTodos(todos);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter
                        .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .withZone(ZoneId.of("UTC"));
                LocalDateTime date = LocalDateTime
                        .parse(todos.getUpdatedAt(), formatter);
                localFormatter  = DateTimeFormatter
                        .ofPattern(
                                "EEEE, dd MMMM yyyy",
                                new Locale("id", "ID")
                        );
                String localDate = date.format(localFormatter);
                binding.setDate(localDate);
            }
            binding.setStatus(todos.isStatus());

            binding.ivStatus.setOnClickListener(view -> listener.onUpdateStatus(todos, todos.isStatus()));
            binding.btnUpdate.setOnClickListener(view -> listener.onUpdate(todos));
            binding.btnDelete.setOnClickListener(view -> listener.onDelete(todos));


        }
    }
}
