package com.example.andreykochetkov.rk.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.andreykochetkov.rk.R;

import ru.mail.weather.lib.Storage;

public class SettingsActivity extends AppCompatActivity {
    Button btnAuto;
    Button btnIt;
    Button btnHealth;
    TextView tvOut;
    private Storage mStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        btnIt = (Button) findViewById(R.id.btnIt);
        btnAuto = (Button) findViewById(R.id.btnAuto);
        btnHealth = (Button) findViewById(R.id.btnHealth);
        tvOut = (TextView) findViewById(R.id.tvOut);
        mStorage = Storage.getInstance(this);
        OnClickListener oclBtn = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnIt:
                        // кнопка ОК
                        mStorage.saveCurrentTopic("It");
                        tvOut.setText("IT");
                        break;
                    case R.id.btnHealth:
                        // кнопка Cancel
                        mStorage.saveCurrentTopic("Health");
                        tvOut.setText("health");
                        break;
                    case R.id.btnAuto:
                        // кнопка Cancel
                        mStorage.saveCurrentTopic("Auto");
                        tvOut.setText("auto");
                        break;
                }

            }
        };
        btnIt.setOnClickListener(oclBtn);
        btnAuto.setOnClickListener(oclBtn);
        btnHealth.setOnClickListener(oclBtn);


    }




}
