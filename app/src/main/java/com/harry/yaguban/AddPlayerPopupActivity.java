package com.harry.yaguban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPlayerPopupActivity extends AppCompatActivity {
    RadioGroup selectPosition;
    ArrayList<Person> batterInfo;
    ArrayList<Person> pitcherInfo;
    ArrayAdapter<String> infoAdapter;
    ArrayList<String> batterString, pitcherString;

    Spinner batterListSpinner, battingOrderSpinner;
    Spinner pitcherListSpinner;
    LinearLayout batterListLayout, pitcherListLayout;

    final int nothingSelected = -10;
    Person addBatterName, addPitcherName;
    int addBatterOrder = nothingSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_popup);

        Intent intent = getIntent();
        selectPosition = findViewById(R.id.radiogroupSelectBatPitch);
        batterInfo = (ArrayList<Person>) intent.getSerializableExtra("batterList");
        pitcherInfo = (ArrayList<Person>) intent.getSerializableExtra("pitcherList");
        batterListSpinner = findViewById(R.id.spinnerSelectBatter);
        battingOrderSpinner = findViewById(R.id.spinnerSelectBattingOrder);
        pitcherListSpinner = findViewById(R.id.spinnerSelectPitcher);
        batterListLayout = findViewById(R.id.layoutBatterList);
        pitcherListLayout = findViewById(R.id.layoutPitcherList);
        addBatterName = null;
        addPitcherName = null;

        convertPersonListToString();
        setAdapters();

        //Select Batter / Pitcher Radio
        selectPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobuttonBatter) {
                    //show batter information list
                    pitcherListLayout.setVisibility(View.INVISIBLE);
                    batterListLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radiobuttonPitcher) {
                    //show pitcher information list
                    batterListLayout.setVisibility(View.INVISIBLE);
                    pitcherListLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        //Select Batter
        batterListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addBatterName = batterInfo.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                addBatterName = null;
            }
        });

        //Select Batting Order

        battingOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String addBatterOrderString;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addBatterOrderString = parent.getItemAtPosition(position).toString();

                if (addBatterOrderString.contains("타자"))
                    addBatterOrder = Integer.parseInt(addBatterOrderString.replace("번 타자", ""));
                else if(addBatterOrderString.equals("대타"))
                    addBatterOrder = -1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                addBatterOrder = nothingSelected;
            }
        });

        //Select Pitcher
        pitcherListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addPitcherName = pitcherInfo.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                addPitcherName = null;
            }
        });
    }

    private void setAdapters() {
        infoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, batterString);
        infoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batterListSpinner.setAdapter(infoAdapter);

        infoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pitcherString);
        infoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pitcherListSpinner.setAdapter(infoAdapter);
    }

    private void convertPersonListToString() {
        //batter
        batterString = new ArrayList<>();
        pitcherString = new ArrayList<>();

        for (Person p : batterInfo) {
            batterString.add(p.getName() + "  (" + p.getBackNum() + "번)   " + "[" + p.getPosition() + "]");
        }

        //pitcher
        for (Person p : pitcherInfo) {
            pitcherString.add(p.getName() + "  (" + p.getBackNum() + "번)   " + "[" + p.getPosition() + "]");
        }
    }

    public void buttonAddBatterClicked(View v) {
        if (addBatterName == null) {
            Toast.makeText(this, "타자를 선택해주세요!", Toast.LENGTH_SHORT).show();
            return;
        } else if (addBatterOrder == nothingSelected) {
            Toast.makeText(this, "타순을 선택해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getApplicationContext(), PlayGameActivity.class);
        intent.putExtra("isBatter", true);
        intent.putExtra("batter", addBatterName);
        intent.putExtra("batterOrder", addBatterOrder);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void buttonAddPitcherClicked(View v) {
        if (addPitcherName == null) {
            Toast.makeText(this, "투수를 선택해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getApplicationContext(), PlayGameActivity.class);
        intent.putExtra("isBatter", false);
        intent.putExtra("pitcher", addPitcherName);

        setResult(RESULT_OK, intent);
        finish();
    }
}