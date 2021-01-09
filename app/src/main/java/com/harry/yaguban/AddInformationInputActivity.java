package com.harry.yaguban;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddInformationInputActivity extends AppCompatActivity {

    Date gameDate;
    TextView dateSelectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information_input);
        dateSelectText = findViewById(R.id.textviewDateSelected);
    }

    public void SelectDateClicked(View v) {
        //show popup
        Intent popupIntent = new Intent(this, DateSelectPopupActivity.class);
        popupIntent.putExtra("title", "날짜 선택");
        startActivityForResult(popupIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                gameDate = new Date();
                gameDate.setYear(data.getIntExtra("year", 0) - 1900);
                gameDate.setMonth(data.getIntExtra("month", 0));
                gameDate.setDate(data.getIntExtra("day", 0));

                dateSelectText.setText(getDateString(gameDate));
            }
        }
    }

    private String getDateString(Date date) {
        return (date.getYear() + 1900) + "/" + (date.getMonth() + 1) + "/" + date.getDate();
    }

    public void GameStartClicked(View v) {
        CheckBox isHometeam = findViewById(R.id.checkboxIsHometeam);
        EditText edittextOpponent = findViewById(R.id.edittextOpponent);
        String homeTeam, awayTeam;

        if (edittextOpponent.getText().toString().equals("")) {
            Toast.makeText(this, "상대팀 이름을 입력해주세요!", Toast.LENGTH_LONG).show();
            return;
        } else if(gameDate == null) {
            Toast.makeText(this, "날짜를 선택해주세요!", Toast.LENGTH_LONG).show();
            return;
        }

        if (isHometeam.isChecked()) {
            homeTeam = "광운대학교";
            awayTeam = edittextOpponent.getText().toString();
        } else {
            homeTeam = edittextOpponent.getText().toString();
            awayTeam = "광운대학교";
        }

        Game newGame = new Game(homeTeam, awayTeam, gameDate);
        Intent intent = new Intent(getApplicationContext(), PlayGameActivity.class);
        intent.putExtra("objectGame", newGame);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}