package com.example.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText edtTitle, edtDescription;
    Button btnAdd, btnCancel;
    TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        edtTitle = findViewById(R.id.edtTitle);
        txtDate = findViewById(R.id.txtDate);
        edtDescription = findViewById(R.id.edtDescription);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);


        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskActivity.this, TaskActivity.this, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                if (title.isEmpty()) {
                    edtTitle.setError("Please Enter Title");
                    return;
                }
                String date = txtDate.getText().toString();
                if (date.equals("Select Date")) {
                    txtDate.setError("Please Enter Date");
                    return;
                }

                if (!edtDescription.getText().toString().isEmpty()) {
                    String description = edtDescription.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("Title", title);
                    intent.putExtra("Date", date);
                    intent.putExtra("Description", description);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("Title", title);
                    intent.putExtra("Date", date);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        String date = dayofmonth + "/" + month + "/" + year;
        txtDate.setText(date);
    }
}