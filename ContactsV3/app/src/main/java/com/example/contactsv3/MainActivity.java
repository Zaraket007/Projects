package com.example.contactsv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //method called bay Register Button
    public void registerBt(View v){
        Intent i=new Intent(MainActivity.this,SignupActivity.class);
        startActivity(i);
    }
}