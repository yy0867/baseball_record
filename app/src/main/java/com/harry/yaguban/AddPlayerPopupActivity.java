package com.harry.yaguban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddPlayerPopupActivity extends AppCompatActivity {
    RadioGroup selectPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_popup);

        selectPosition = findViewById(R.id.radiogroupSelectBatPitch);

        selectPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //should make xml list, set visible & invisible
                if (checkedId == R.id.radiobuttonBatter) {
                    //show batter information list
                } else if (checkedId == R.id.radiobuttonPitcher) {
                    //show pitcher information list
                }
            }
        });
    }
}