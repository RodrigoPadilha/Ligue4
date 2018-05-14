package com.example.rodrigo.ligue4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.rodrigo.ligue4.R;

public class MainActivity extends AppCompatActivity {

    private static final String TESTE = "TESTE";

    private RadioButton rb_vs_machine;
    private RadioButton rb_vs_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rb_vs_friend = findViewById(R.id.rb_vs_friend);
        rb_vs_machine = findViewById(R.id.rb_vs_machine);

    }

    public void playLigue4(View v) {

        int gameModeOpt = 0;
        if(rb_vs_machine.isChecked()){
            gameModeOpt = 1;
        }else if (rb_vs_friend.isChecked()) {
            gameModeOpt = 2;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("gameMode", gameModeOpt);
        startActivity(intent);

    }
}
