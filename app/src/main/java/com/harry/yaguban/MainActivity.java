package com.harry.yaguban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, LoadingActivity.class));
    }

    public void buttonTotalClicked(View v) {
        startActivity(new Intent(this, TotalActivity.class));
    }

    public void buttonAddClicked(View v) {
        startActivity(new Intent(this, AddInformationInputActivity.class));
    }

    public void buttonEachClicked(View v) {
        startActivity(new Intent(this, EachActivity.class));
    }

    public void buttonManageClicked(View v) {
        startActivity(new Intent(this, ManageActivity.class));
    }
}