package com.example.biji;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setDayMode();
    }
    public void setDayMode(){
        setTheme(R.style.DayTheme);
    }
}
