package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements deleteTask {

    private TaskAdapter adapter;
    private TaskViewModel taskViewModel;
    private TextView txtAddTask;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        txtAddTask = findViewById(R.id.txtAddTask);
        initRecyclerView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        taskViewModel = new ViewModelProvider(MainActivity.this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(MainActivity.this, tasks -> {
            adapter.updateTask((ArrayList<Task>) tasks);

            if (adapter.getItemCount() != 0){
                recyclerView.setVisibility(View.VISIBLE);
                txtAddTask.setVisibility(View.GONE);
            }else{
                recyclerView.setVisibility(View.GONE);
                txtAddTask.setVisibility(View.VISIBLE);
            }
        });

    }

    public void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TaskAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("Title");
            String date = data.getStringExtra("Date");
            String description = data.getStringExtra("Description");

            Task task = new Task();
            task.setTitle(title);
            task.setDate(date);
            task.setDescription(description);
            taskViewModel.insert(task);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.analysis) {
            Intent intent = new Intent(MainActivity.this, AnalysisActivity.class);
            int pendingTask = adapter.getItemCount();
            int completedTask = adapter.completedTasks;
            intent.putExtra("CompletedTask", completedTask);
            intent.putExtra("PendingTask", pendingTask);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void delete(Task task) {
        taskViewModel.delete(task);
    }

}