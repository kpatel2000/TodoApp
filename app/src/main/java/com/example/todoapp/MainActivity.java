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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements deleteTask {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TextView txtNoTask;
    private TaskAdapter adapter;
    int completedTask = 0;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TaskAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
        });

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
        switch (item.getItemId()) {
            case R.id.analysis:
                Intent intent = new Intent(MainActivity.this, AnalysisActivity.class);
                int pendingTask = adapter.getItemCount();
                intent.putExtra("CompletedTask", completedTask);
                intent.putExtra("PendingTask", pendingTask);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void delete(Task task) {
        completedTask++;
        taskViewModel.delete(task);
    }
}