package com.harry.yaguban;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity{

    private final long FINISH_INTERVAL_TIME=2000;
    private long backPressedTime=0;

    TableLayout person_list_layout;
    ArrayList<TableRow> person_list;
    ArrayList<Person> person_info;
    ArrayList<CheckBox> delete_checks;
    final String listRepository="managePersonList";
    Person person;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        person_list = new ArrayList<>();
        person_info = new ArrayList<>();
        delete_checks = new ArrayList<>();

        initializeToolBar();
        getPersonList();
    }

    public void buttonPersonAddClicked(View v){
        showLogin();
    }

    public void buttonPersonRemoveClicked(View v){
        person_list_layout=findViewById(R.id.personListLayout);

        for(int i=0;i<delete_checks.size();++i){
            if(delete_checks.get(i).isChecked()){
                person_list_layout.removeView(person_list.get(i));
                person_list.remove(i);
                person_info.remove(i);
                delete_checks.remove(i);
                savePersonList();
                --i;
            }
        }
        for(int i=0;i<person_list.size();++i){
            switch(i%2)
            {
                case 0: person_list.get(i).setBackgroundResource(R.drawable.person_table_row_design_odd); break;
                case 1: person_list.get(i).setBackgroundResource(R.drawable.person_table_row_design); break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_manage_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.optionHelp: break;
            case android.R.id.home:
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
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
                String name=pName.getText().toString();
                String position=pos.getSelectedItem().toString();
                String backNum=bNum.getText().toString();
                person = new Person(name,position,backNum);
                person_info.add(person);
                addOnePerson();
                savePersonList();
            }
        }).setNegativeButton("취소",null).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addOnePerson(){
        person_list_layout = findViewById(R.id.personListLayout);
        TableRow tableRow = new TableRow(this);
        person_list.add(tableRow);

        switch(person_list.size()%2)
        {
            case 0: tableRow.setBackgroundResource(R.drawable.person_table_row_design); break;
            case 1: tableRow.setBackgroundResource(R.drawable.person_table_row_design_odd); break;
        }

        CheckBox deleteCheck = new CheckBox(this);
        delete_checks.add(deleteCheck);
        deleteCheck.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#711F1F")));
        tableRow.addView(deleteCheck);

        for (int i = 0; i < 3; ++i) {
            TextView textView = new TextView(this);
            textView.setWidth(120); textView.setHeight(120);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(Color.parseColor("#7A4F4F"));
            textView.setGravity(Gravity.LEFT);
            textView.setPadding(15, 30, 10, 30);
            Typeface face = getResources().getFont(R.font.bccardbold);
            textView.setTypeface(face);

            switch (i) {
                case 0: textView.setText(person.getName()); break;
                case 1: textView.setText(person.getPosition()); break;
                case 2: textView.setText(person.getBackNum()); break;
            }
            tableRow.addView(textView);
        }
        person_list_layout.addView(tableRow);
    }

    private void initializeToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbarManage);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void savePersonList(){
        FileOutputStream fos=null;
        try{
            fos=openFileOutput(listRepository,Context.MODE_PRIVATE);
            if(person_info.isEmpty())fos.write("".getBytes());
            else{
                for(int i=0;i<person_info.size();++i)
                {
                    String temp=person_info.get(i).convertSaveType();
                    fos.write(temp.getBytes());
                }
            }
            fos.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getPersonList(){
        String name = null,pos=null,bNum=null;
        FileInputStream fis = null;
        try{
            fis = openFileInput(listRepository);
            BufferedReader iReader = new BufferedReader(new InputStreamReader(fis));

            name=iReader.readLine();
            pos=iReader.readLine();
            bNum=iReader.readLine();

            while(name!=null && pos!=null && bNum!=null){
                person_info.add(new Person(name,pos,bNum));
                person=new Person(name,pos,bNum);
                addOnePerson();
                name=iReader.readLine();
                pos=iReader.readLine();
                bNum=iReader.readLine();
            }
            iReader.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){

        long tempTime = System.currentTimeMillis();
        long intervalTime=tempTime-backPressedTime;

        if(0<=intervalTime && FINISH_INTERVAL_TIME>=intervalTime)
        {
            finish();
        }
        else
        {
            backPressedTime=tempTime;
            Toast.makeText(getApplicationContext(),"한번 더 누르면 저장 후 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }
}