package com.example.todoapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class TaskRepo {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTask;

    TaskRepo(Application application) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTask = mTaskDao.getAllTasks();
    }

    LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

    void insert(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    void delete(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.delete(task);
        });
    }

}
