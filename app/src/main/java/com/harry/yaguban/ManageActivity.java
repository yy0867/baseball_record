package com.harry.yaguban;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class ManageActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME=2000;
    private long backPressedTime=0;

    TableLayout person_list_layout;
    ArrayList<TableRow> person_list;
    ArrayList<String> person_info;
    final String listRepository="managePersonList";
    String name, position, backNum;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        person_list = new ArrayList<>();
        person_info = new ArrayList<>();

        initializeToolBar();
        getPersonList();
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

        for(int i=0;i<3;++i){
            person_info.remove(person_info.size()-1);
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
                savePersonList();
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
                name=pName.getText().toString();
                position=pos.getSelectedItem().toString();
                backNum=bNum.getText().toString();
                person_info.add(name); person_info.add(position); person_info.add(backNum);
                addOnePerson();
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

        for (int i = 0; i < 3; ++i) {
            TextView textView = new TextView(this);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(Color.parseColor("#7A4F4F"));
            textView.setGravity(Gravity.LEFT);
            textView.setPadding(15, 30, 10, 30);
            Typeface face = getResources().getFont(R.font.bccardbold);
            textView.setTypeface(face);

            switch (i) {
                case 0: textView.setText(name); break;
                case 1: textView.setText(position); break;
                case 2: textView.setText(backNum); break;
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

    private void savePersonList(){
        FileOutputStream fos=null;
        try{
            fos=openFileOutput(listRepository,Context.MODE_PRIVATE);
            if(person_info.isEmpty())fos.write("".getBytes());
            else{
                for(int i=0;i<person_info.size();++i)
                {
                    String temp=person_info.get(i)+"\n";
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
    private void getPersonList(){
        String data = null;
        FileInputStream fis = null;
        try{
            fis = openFileInput(listRepository);
            BufferedReader iReader = new BufferedReader(new InputStreamReader(fis));

            data = iReader.readLine();
            while(data!=null){
                person_info.add(data);
                data=iReader.readLine();
            }
            iReader.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<person_info.size();i+=3){
            name=person_info.get(i);
            position=person_info.get(i+1);
            backNum=person_info.get(i+2);
            addOnePerson();
        }
    }

    @Override
    public void onBackPressed(){

        long tempTime = System.currentTimeMillis();
        long intervalTime=tempTime-backPressedTime;

        if(0<=intervalTime && FINISH_INTERVAL_TIME>=intervalTime)
        {
            savePersonList();
            finish();
        }
        else
        {
            backPressedTime=tempTime;
            Toast.makeText(getApplicationContext(),"한번 더 누르면 저장 후 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }
}