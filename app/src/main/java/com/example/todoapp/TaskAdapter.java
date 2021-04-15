package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    deleteTask deleteTask;

    TaskAdapter(Context context) {
        this.context = context;
    }

    private List<Task> allTasks = new ArrayList<Task>();

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task currentTask = allTasks.get(position);
        holder.txtTitle.setText(currentTask.title);
        if (currentTask.getDescription() == null) {

        } else {
            holder.txtDescription.setVisibility(View.VISIBLE);
            holder.txtDescription.setText(currentTask.description);
        }

        holder.txtDoneOn.setText("Task Date: " + currentTask.date);
        if (holder.checkBox.isChecked()) {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                deleteTask = (deleteTask) context;
                deleteTask.delete(currentTask);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask = (deleteTask) context;
                deleteTask.delete(currentTask);
            }
        });
    }

    public void updateTask(ArrayList<Task> newTask) {
        allTasks.clear();
        allTasks.addAll(newTask);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle, txtDescription, txtDoneOn;
        private CheckBox checkBox;
        private ImageView delete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDoneOn = itemView.findViewById(R.id.txtCompletionDate);
            checkBox = itemView.findViewById(R.id.checkbox);
            delete = itemView.findViewById(R.id.delete);
        }
    }

}

interface deleteTask {
    void delete(Task task);
}
