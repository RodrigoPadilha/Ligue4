package com.example.rodrigo.ligue4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.rodrigo.ligue4.Adapters.StateAdapter;
import com.example.rodrigo.ligue4.GameManager;
import com.example.rodrigo.ligue4.GameParameters;
import com.example.rodrigo.ligue4.InvalidMoveException;
import com.example.rodrigo.ligue4.R;

import java.util.ArrayList;

/**
 * Created by rodrigo on 26/04/2018.
 */

public class GameActivity extends AppCompatActivity {

    //private static final String TESTE = "TESTE";

    private GridView gridBoard;
    private int gameModeOpt;
    private ArrayList<Integer> boardPartsList;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridBoard = findViewById(R.id.id_tabuleiro);

        //INÍCIO
        dataReceive();
        loadBoard();

        gridBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // TODO Criar arquivo Dimens
                try {

                    putCoinOnState(gameManager.getStackIndex(position));

                    //TODO Analisar
                    if(gameManager.hasWinner()) {
                        String vencedor = "";
                        if (gameManager.getRound() == GameParameters.YELLOW_COIN)
                            vencedor = "AMARELO venceu";
                        if (gameManager.getRound() == GameParameters.RED_COIN)
                            vencedor = "VERMELHO venceu";
                        Toast.makeText(GameActivity.this, vencedor, Toast.LENGTH_LONG).show();
                    }

                    gameManager.changeRound();

                    //TODO Incrementar Placar
                    //gameManager.getRound();

                } catch (InvalidMoveException e) {
                    Toast.makeText(GameActivity.this, GameActivity.this.getString(R.string.message_invalid_move), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                //TODO IA

                //TODO Dicas

            }
        });

    }

    private void dataReceive() {

        Intent intent = getIntent();
        gameModeOpt = intent.getIntExtra("gameMode", 1);

    }

    private void loadBoard() {

        boardPartsList = new ArrayList<>();
        for (int linha = 0; linha < GameParameters.QTD_LINE; linha++) {
            for (int coluna = 0; coluna < GameParameters.QTD_COLUMN; coluna++) {
                //matriz em formato de lista
                boardPartsList.add(0);
            }
        }
        gridBoard.setColumnWidth(GameParameters.QTD_LINE);    //linhas
        gridBoard.setNumColumns(GameParameters.QTD_COLUMN);    //colunas
        gridBoard.setAdapter(new StateAdapter(this, boardPartsList));
        gridBoard.setBackgroundColor(getResources().getColor(R.color.board_background));

        gameManager = new GameManager(boardPartsList);

    }

    private void putCoinOnState(int position) {

        //TODO Efeito de moeda caindo
        if (gameManager.getRound() == 1) {
            boardPartsList.set(position, GameParameters.YELLOW_COIN);   // Coloca Moeda Amarela na célula
//            gameManager.setRound(gameManager.getRound() + 1);       // Passa a vez
        } else {
            boardPartsList.set(position, GameParameters.RED_COIN);      // Coloca Moeda Vermelha na célula
//            gameManager.setRound(gameManager.getRound() - 1);       // Passa a vez
        }

        gridBoard.setAdapter(new StateAdapter(GameActivity.this, boardPartsList));
    }
}
