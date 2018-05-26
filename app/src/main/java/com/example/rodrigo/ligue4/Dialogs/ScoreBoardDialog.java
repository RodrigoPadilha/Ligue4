package com.example.rodrigo.ligue4.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rodrigo.ligue4.R;

/**
 * Created by rodrigo on 17/05/2018.
 */

public class ScoreBoardDialog extends DialogFragment{

    private static final String TESTE = "TESTE";
    private OptionsDialogIntf classCallBack;
    private int yellowWins;
    private int redWins;
    private String winner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TESTE,"onCreate");

        classCallBack = (OptionsDialogIntf) getArguments().getSerializable("classCallBack");
        yellowWins = getArguments().getInt("yellowWins(");
        redWins = getArguments().getInt("redWins");
        winner = getArguments().getString("winner");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TESTE,"onPause");
        this.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i(TESTE,"onCreateView");

        View view = inflater.inflate(R.layout.scoreboard, container);

        Button btnNewGame = view.findViewById(R.id.btnNewGame);
        Button btnBack = view.findViewById(R.id.btnBack);
        TextView yellowScore = view.findViewById(R.id.yellow_score);
        TextView redScore = view.findViewById(R.id.red_score);

        yellowScore.setText(Integer.toString(yellowWins));
        redScore.setText(Integer.toString(redWins));


        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classCallBack.dialogAnswer(0);
                dismiss();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classCallBack.dialogAnswer(1);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TESTE,"onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
            super.onAttach(context);
        Log.i(TESTE,"onAttach");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i(TESTE,"onCancel");
    }

    /*
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Log.i(TESTE,"onCreateDialog");

        //AlertDialog.Builder builder = new AlertDialog.Builder((Context) classCallBack);

        return builder.show();
    }
*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TESTE,"onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TESTE,"onDetach");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.i(TESTE,"onDismiss");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TESTE,"onSaveInstanceState");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TESTE,"onStart");

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setTitle(winner + " " + getString(R.string.message_winner));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
