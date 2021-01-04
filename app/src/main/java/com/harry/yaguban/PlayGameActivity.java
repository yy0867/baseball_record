package com.harry.yaguban;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PlayGameActivity extends AppCompatActivity {
    Game newGame;
    SwitchCompat switchBatPitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Intent intent = getIntent();

        TextView opponentTeamName = findViewById(R.id.textviewVS);
        switchBatPitch = findViewById(R.id.switchBatPitch);

        newGame = (Game)intent.getSerializableExtra("objectGame");

        //away team batting first
        changeStatus(!newGame.getAwayTeam().equals(Game.ourTeam));

        String vsTeamName = "vs " + newGame.getOpponentTeam();
        opponentTeamName.setText(vsTeamName);
    }

    //change Bat / Pitch
    void changeStatus(boolean isPitching) {
        if (isPitching) {
            switchBatPitch.setChecked(false);
            switchBatPitch.setText("타자");
        } else {
            switchBatPitch.setChecked(true);
            switchBatPitch.setText("투수");
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

    }
}