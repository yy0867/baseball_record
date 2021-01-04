package com.harry.yaguban;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {

    LinearLayout person_list_layout;
    ArrayList<TextView> person_list;
    TextView textView;

    String name, position, backNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        person_list = new ArrayList<>();
    }

    public void buttonPersonAddClicked(View v){
        showLogin();
    }

    public void buttonPersonRemoveClicked(View v){
        person_list_layout=findViewById(R.id.personListLayout);

        int lastIndex = person_list.size()-1;
        if(lastIndex<0)return;

        person_list_layout.removeView(person_list.get(lastIndex));
        person_list.remove(lastIndex);
    }

    private void showLogin(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout loginLayout = (LinearLayout)inflater.inflate(R.layout.popup_person_info,null);

        final EditText pName=(EditText)loginLayout.findViewById(R.id.personName);
        Spinner pos=(Spinner)loginLayout.findViewById(R.id.position);
        final EditText bNum=(EditText)loginLayout.findViewById(R.id.backNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("선수정보 입력").setView(loginLayout).setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name=pName.getText().toString();
                position=pos.getSelectedItem().toString();
                backNum=bNum.getText().toString();
                addOnePerson();
            }
        }).setNegativeButton("취소",null).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addOnePerson(){
        person_list_layout=findViewById(R.id.personListLayout);
        person_list_layout.setGravity(Gravity.CENTER);

        textView = new TextView(this);
        textView.setText(name+"     "+position+"     "+backNum);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        textView.setTextColor(Color.parseColor("#FF000000"));
        Typeface face= getResources().getFont(R.font.bccardbold);
        textView.setTypeface(face);

        person_list.add(textView);
        person_list_layout.addView(textView);
    }
}