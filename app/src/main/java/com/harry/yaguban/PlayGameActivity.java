package com.harry.yaguban;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class PlayGameActivity extends AppCompatActivity {
    Game newGame;
    boolean isDefense;
    TextView textBatPitch;
    SwitchCompat switchBatPitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Intent intent = getIntent();

        TextView opponentTeamName = findViewById(R.id.textviewVS);
        switchBatPitch = findViewById(R.id.switchBatPitch);
        textBatPitch = findViewById(R.id.textviewBatPitch);

        newGame = (Game)intent.getSerializableExtra("objectGame");

        //first defense -> first pitcher
        isDefense = newGame.getHomeTeam().equals(Game.ourTeam);
        switchBatPitch.setChecked(isDefense);
        changeBatPitchText();

        //print Opponent Team Name
        String vsTeamName = "vs " + newGame.getOpponentTeam();
        opponentTeamName.setText(vsTeamName);

        //print Scoreboard
    }

    private void changeBatPitchText() {
        if (isDefense) {
            textBatPitch.setText("투수");
        } else {
            textBatPitch.setText("타자");
        }
    }

    //안타
    public void hit1Clicked(View v) {
        Toast.makeText(this, "안타!", Toast.LENGTH_SHORT).show();
    }

    //2루타
    public void hit2Clicked(View v) {
        Toast.makeText(this, "2루타!", Toast.LENGTH_SHORT).show();
    }

    //3루타
    public void hit3Clicked(View v) {
        Toast.makeText(this, "3루타!", Toast.LENGTH_SHORT).show();
    }

    //홈런
    public void homerunClicked(View v) {
        Toast.makeText(this, "홈런!", Toast.LENGTH_SHORT).show();
    }

    //볼넷
    public void ball4Clicked(View v) {
        Toast.makeText(this, "볼넷!", Toast.LENGTH_SHORT).show();
    }

    //삼진
    public void strikeoutClicked(View v) {
        Toast.makeText(this, "삼진!", Toast.LENGTH_SHORT).show();
    }

    //타점
    public void RBIClicked(View v) {
        Toast.makeText(this, "타점!", Toast.LENGTH_SHORT).show();
    }

    //아웃
    public void outClicked(View v) {
        Toast.makeText(this, "아웃!", Toast.LENGTH_SHORT).show();
    }

    //투타 변경
    public void batPitchSwitchClicked(View v) {
        isDefense = !isDefense;
        switchBatPitch.setChecked(isDefense);
        newGame.changeCurAttackTeam();
        changeBatPitchText();
    }
}