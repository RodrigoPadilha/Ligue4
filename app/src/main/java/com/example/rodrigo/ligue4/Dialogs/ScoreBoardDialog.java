package com.example.rodrigo.ligue4.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rodrigo.ligue4.R;

import java.io.Serializable;

/**
 * Created by rodrigo on 16/05/2018.
 */

public class ScoreBoardDialog implements Serializable {

    private final AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View view;

    public ScoreBoardDialog(final OptionsDialogIntf classCallBack, int yellowWins, int redWins){

        view = LayoutInflater.from((Context) classCallBack).inflate(R.layout.scoreboard, null);
        builder = new AlertDialog.Builder((Context) classCallBack);

        Button btnNewGame = view.findViewById(R.id.btnNewGame);
        Button btnBack = view.findViewById(R.id.btnBack);
        TextView yellowScore = view.findViewById(R.id.yellow_score);
        TextView redScore = view.findViewById(R.id.red_score);

        yellowScore.setText(Integer.toString(yellowWins));
        redScore.setText(Integer.toString(redWins));

        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();

        openDialog();

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classCallBack.dialogAnswer(0);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classCallBack.dialogAnswer(1);
            }
        });

    }

    public void openDialog(){

        dialog.show();
    }

    public void closeDialog(){

        dialog.dismiss();
    }

}
