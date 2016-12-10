package com.example.tonghung.storagesdemo;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et;
    private Button btnSaveSharePref, btnReadSharePref, btnSaveInternal, btnReadInternal, btnSaveSD, btnReadSD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.editText);
        btnSaveSharePref = (Button) findViewById(R.id.buttonSaveSharePref);
        btnReadSharePref = (Button) findViewById(R.id.buttonReadSharePref);
        btnSaveInternal = (Button) findViewById(R.id.buttonSaveInternal);
        btnReadInternal = (Button) findViewById(R.id.buttonReadInternal);
        btnSaveSD = (Button) findViewById(R.id.buttonSaveSD);
        btnReadSD = (Button) findViewById(R.id.buttonReadSD);

        btnSaveSharePref.setOnClickListener(this);
        btnReadSharePref.setOnClickListener(this);
        btnSaveInternal.setOnClickListener(this);
        btnReadInternal.setOnClickListener(this);
        btnSaveSD.setOnClickListener(this);
        btnReadSD.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSaveSharePref){
            SharedPreferences sharePref = getSharedPreferences("sharePref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePref.edit();

            String value = et.getText().toString();
            editor.putString("value", value);
            editor.commit();
            Toast.makeText(this, "Save to SharePreferences successful", Toast.LENGTH_SHORT).show();
        }else if(view == btnReadSharePref){
            SharedPreferences sharedPreferences = getSharedPreferences("sharePref", MODE_PRIVATE);
            String value = sharedPreferences.getString("value", "");
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        } else if(view == btnSaveInternal){
            String value = et.getText().toString();
            try {
                FileOutputStream fos = openFileOutput("myFile", MODE_PRIVATE);
                fos.write(value.getBytes());
                Toast.makeText(this, "Data has wrote to storage", Toast.LENGTH_SHORT).show();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(view == btnReadInternal){
            try {
                FileInputStream fis = openFileInput("myFile");
                String temp = "";
                int c;
                while((c = fis.read()) != -1){
                    temp+= Character.toString((char) c);
                }
                Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(view == btnSaveSD){
            String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/file1.txt";
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(sdcard));
                writer.write(et.getText().toString());
                Toast.makeText(this, "File was wrote successful", Toast.LENGTH_SHORT).show();
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(view == btnReadSD){
            String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/file1.txt";
            Scanner scan = null;
            try {
                scan = new Scanner(new File(sdcard));
                String str = "";
                while (scan.hasNext()){
                    str+=scan.nextLine() + "\n";
                }
                scan.close();
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
