package com.harry.yaguban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSelectPopupActivity extends AppCompatActivity {

    int yearInput, monthInput, dayInput;
    public CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select_popup);

        calendar = findViewById(R.id.calendarviewInputDate);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                yearInput = year;
                monthInput = month;
                dayInput = dayOfMonth;
            }
        });
    }

    public void ChooseDateClicked(View v) {
        Intent intent = new Intent();
        intent.putExtra("year", yearInput);
        intent.putExtra("month", monthInput);
        intent.putExtra("day", dayInput);

        setResult(RESULT_OK, intent);
        finish();
    }
}