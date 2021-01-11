package com.harry.yaguban;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class PlayGameActivity extends AppCompatActivity {
    Game newGame;
    boolean isDefense;
    TextView textBatPitch;
    ImageView outCounts;
    SwitchCompat switchBatPitch;
    Typeface font, boldfont;
    TableRow rowInnings, rowHomeTeam, rowAwayTeam;
    LinearLayout batterLayout, pitcherLayout;
    TableLayout batterListLayout, pitcherListLayout;

    ArrayList<Person> batterInfo;
    ArrayList<Person> pitcherInfo;

    static final String gameRepository = "manageGameList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Intent intent = getIntent();
        batterInfo = new ArrayList<>();
        pitcherInfo = new ArrayList<>();

        TextView opponentTeamName = findViewById(R.id.textviewVS);
        switchBatPitch = findViewById(R.id.switchBatPitch);
        textBatPitch = findViewById(R.id.textviewBatPitch);
        outCounts = findViewById(R.id.imageviewOuts);
        batterLayout = findViewById(R.id.layoutBatter);
        pitcherLayout = findViewById(R.id.layoutPitcher);
        batterListLayout = findViewById(R.id.tablelayoutBatter);
        pitcherListLayout = findViewById(R.id.tablelayoutPitcher);

        newGame = (Game)intent.getSerializableExtra("objectGame");
        font = Typeface.createFromAsset(getAssets(), "font/bccardlight.ttf");
        boldfont = Typeface.createFromAsset(getAssets(), "font/bccardbold.ttf");

        //first defense -> first pitcher
        isDefense = newGame.getHomeTeam().equals(Game.ourTeam);
        switchBatPitch.setChecked(isDefense);
        changeBatPitchView();

        //print Opponent Team Name
        String vsTeamName = "vs " + newGame.getOpponentTeam();
        opponentTeamName.setText(vsTeamName);

        //set batter & pitcher list
        getPersonInfo();

        //print Scoreboard
        rowInnings = findViewById(R.id.tablerowInning);
        rowHomeTeam = findViewById(R.id.tablerowHomeTeam);
        rowAwayTeam = findViewById(R.id.tablerowAwayTeam);
        setScoreBoards();
    }

    private void changeBatPitchView() {
        if (isDefense) {
            textBatPitch.setText("투수");
            batterLayout.setVisibility(View.INVISIBLE);
            pitcherLayout.setVisibility(View.VISIBLE);
        } else {
            textBatPitch.setText("타자");
            batterLayout.setVisibility(View.VISIBLE);
            pitcherLayout.setVisibility(View.INVISIBLE);
        }
    }

    //Load Person information from file
    private void getPersonInfo() {
        String name, position, backNumber;
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(ManageActivity.listRepository);
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));

            name = inputReader.readLine();
            position = inputReader.readLine();
            backNumber = inputReader.readLine();

            while(name != null && position != null && backNumber != null) {
                if (position.equals("투수"))
                    pitcherInfo.add(new Person(name, position, backNumber));
                else
                    batterInfo.add(new Person(name, position, backNumber));

                name = inputReader.readLine();
                position = inputReader.readLine();
                backNumber = inputReader.readLine();
            }
            inputReader.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not Found!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Read Error!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setScoreBoards() {
        setRowInnings();
        setRowScore(rowAwayTeam, Game.away);
        setRowScore(rowHomeTeam, Game.home);

        TableLayout.LayoutParams rowParams = (TableLayout.LayoutParams) rowInnings.getLayoutParams();
        rowParams.bottomMargin = 20;

        rowInnings.setLayoutParams(rowParams);
        rowAwayTeam.setLayoutParams(rowParams);
        rowHomeTeam.setLayoutParams(rowParams);
    }

    //set row Innings
    private void setRowInnings() {
        rowInnings.setWeightSum(9);
        rowInnings.addView(setTableText("이닝", 1.8f, true, false));

        for (int i = 0; i < Game.inning; i++) {
            rowInnings.addView(setTableText(Integer.toString(i + 1), 0.8f, true, false));
        }
    }

    private void reloadScoreBoard() {
        rowAwayTeam.removeAllViews();
        rowHomeTeam.removeAllViews();
        setRowScore(rowAwayTeam, Game.away);
        setRowScore(rowHomeTeam, Game.home);
    }

    private void setRowScore(TableRow rowWhichTeam, boolean isHomeTeam) {
        String teamName;

        if(isHomeTeam) teamName = newGame.getHomeTeam().replaceAll("대학교", "");
        else teamName = newGame.getAwayTeam().replaceAll("대학교", "");;

        rowWhichTeam.setBackgroundColor(getResources().getColor(R.color.background_color));
        rowWhichTeam.addView(setTableText(teamName, 1.8f, false, false));

        for (int i = 0; i < Game.inning; i++) {
            rowWhichTeam.addView(setTableText(Integer.toString(newGame.getTeamScore(isHomeTeam, i)),
                    0.8f, false, i == newGame.getInning()));
        }
    }

    private TextView setTableText(String value, float weight, boolean bold, boolean highlight) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));

        if (bold) text.setTypeface(boldfont);
        else text.setTypeface(font);

        if(highlight) text.setBackgroundColor(getResources().getColor(R.color.table_highlight_color));

        text.setTextSize(20);

        return text;
    }

    // -------------------------- For Batters ---------------------------------

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
        if(newGame.getCurAttackTeam() == Game.away) {
            newGame.setScoreAway();
        } else {
            newGame.setScoreHome();
        }
        reloadScoreBoard();

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
        newGame.increaseOut();

        if(newGame.getCurOut() == 0)
            outCounts.setImageResource(R.drawable.out_0);
        else if(newGame.getCurOut() == 1)
            outCounts.setImageResource(R.drawable.out_1);
        else if(newGame.getCurOut() == 2)
            outCounts.setImageResource(R.drawable.out_2);
        else if(newGame.getCurOut() == 3)
            outCounts.setImageResource(R.drawable.out_3);
        reloadScoreBoard();

        Toast.makeText(this, "아웃!", Toast.LENGTH_SHORT).show();
    }
    // --------------------------------- for Batters ------------------------------------
    // --------------------------------- for Pitchers ------------------------------------

    //삼진
    public void strikeoutPitcherClicked(View v) {

    }

    //볼넷
    public void ball4PitcherClicked(View v) {

    }

    //피안타
    public void hittedClicked(View v) {

    }

    //수비 실책
    public void defErrorClicked(View v) {

    }

    //아웃
    public void outPitcherClicked(View v) {

    }

    //실점
    public void losePointClicked(View v) {

    }

    //투구 수
    public void numPitchesClicked(View v) {

    }
    // --------------------------------- for Pitchers ------------------------------------

    //선수 추가
    public void addPlayerClicked(View v) {
        //show popup
        Intent popupIntent = new Intent(this, AddPlayerPopupActivity.class);
        startActivityForResult(popupIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
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
*/

    //투타 변경
    public void batPitchSwitchClicked(View v) {
        isDefense = !isDefense;
        switchBatPitch.setChecked(isDefense);
        newGame.changeCurAttackTeam();
        changeBatPitchView();
    }
}