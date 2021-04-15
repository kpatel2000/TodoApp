package com.example.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Intent getIntent = getIntent();
        int CompletedTask = getIntent.getIntExtra("CompletedTask", 0);
        int PendingTask = getIntent.getIntExtra("PendingTask", 0);
        PieChart pieChart = findViewById(R.id.pieChart);

        TextView txtTotalTask = findViewById(R.id.txtTotalTasks);
        txtTotalTask.setText("Total Task: " + (CompletedTask + PendingTask));

        ArrayList<PieEntry> tasks = new ArrayList<>();

        if (CompletedTask != 0) {
            tasks.add(new PieEntry(CompletedTask, "Completed"));
        }
        if (PendingTask != 0) {
            tasks.add(new PieEntry(PendingTask, "Pending"));
        }

        PieDataSet pieDataSet = new PieDataSet(tasks, "Task");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(25f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Task Analysis");
        pieChart.animate();
    }
}