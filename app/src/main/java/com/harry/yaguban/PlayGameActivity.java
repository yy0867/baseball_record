package com.harry.yaguban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PlayGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Intent intent = getIntent();

        TextView textviewHomeTeam = findViewById(R.id.textviewHomeTeam);
        TextView textviewAwayTeam = findViewById(R.id.textviewAwayTeam);
        TextView textviewDate = findViewById(R.id.textviewTime);

        Game newGame = (Game)intent.getSerializableExtra("objectGame");

        textviewHomeTeam.setText(newGame.getHomeTeam());
        textviewAwayTeam.setText(newGame.getAwayTeam());
        textviewDate.setText(newGame.getDateString());
    }
}