package com.harry.yaguban;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {

    TableLayout tableLayout;
    ArrayList<TableRow> tableRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        tableRows = new ArrayList<>();
    }

    public void buttonPersonAddClicked(View v){
        tableLayout=findViewById(R.id.tableLayout);
        TableRow tableRow = new TableRow(this);
        tableRows.add(tableRow);

        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(this);
        textView.setText("Hello");
        tableRow.addView(textView);

        tableLayout.addView(tableRow);
    }

    public void buttonPersonRemoveClicked(View v){
        tableLayout=findViewById(R.id.tableLayout);

        int lastIndex = tableRows.size()-1;
        if(lastIndex<0)return;

        tableLayout.removeView(tableRows.get(lastIndex));
        tableRows.remove(lastIndex);
    }
}