package com.example.rodrigo.ligue4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
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

    private FrameLayout scoreboard;
    private GridView gridBoard;
    private int gameModeOpt;
    private ArrayList<Integer> boardPartsList;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreboard = findViewById(R.id.id_scoreboard);
        gridBoard = findViewById(R.id.id_game_board);

        //View view = LayoutInflater.from(this).inflate(R.layout.scoreboard, scoreboard,true);
        View view = LayoutInflater.from(this).inflate(R.layout.scoreboard, null);
        scoreboard.addView(view);

        //INÍCIO
        dataReceive();
        partsList();
        loadBoard();


        view.findViewById(R.id.btnNewGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partsList();
                loadBoard();
            }
        });

        view.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gridBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // TODO Criar arquivo Dimens
                try {

                    putCoinOnState(gameManager.getStackIndex(position));

                    if (gameManager.hasWinner()) {
                        String vencedor = "";
                        if (gameManager.getRound() == GameParameters.YELLOW_COIN)
                            vencedor = "AMARELO venceu";
                        if (gameManager.getRound() == GameParameters.RED_COIN)
                            vencedor = "VERMELHO venceu";

                        //TODO Abrir Dialog Personalizada
                        Toast.makeText(GameActivity.this, vencedor, Toast.LENGTH_LONG).show();
                    }

                    gameManager.changeRound();

                    //TODO Incrementar Placar
                    //gameManager.getRound();

                } catch (InvalidMoveException e) {
                    Toast.makeText(GameActivity.this, GameActivity.this.getString(R.string.message_invalid_move), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                //TODO IA

                //TODO Dicas

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntegerArrayList("boardPartsList", boardPartsList); // Salva lista de peças do Tabuleiro

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Verifica se o bundle existe, caso sim, você verifica se a lista de peças salvas
        if (savedInstanceState != null && savedInstanceState.containsKey("boardPartsList")) {

            boardPartsList = savedInstanceState.getIntegerArrayList("boardPartsList");  // Recupera o valor que estava anteriormente
            loadBoard();                                                                    // Gera o Exibe o Tabulerio após virar a tela
        }

    }

    private void dataReceive() {

        Intent intent = getIntent();
        gameModeOpt = intent.getIntExtra("gameMode", 1);

    }

    public void partsList() {

        boardPartsList = new ArrayList<>();
        for (int linha = 0; linha < GameParameters.QTD_LINE; linha++) {
            for (int coluna = 0; coluna < GameParameters.QTD_COLUMN; coluna++) {

                boardPartsList.add(0);  //matriz em formato de lista
            }
        }

    }

    private void loadBoard() {

        gameManager = new GameManager(boardPartsList);

        gridBoard.setColumnWidth(GameParameters.QTD_LINE);    //linhas
        gridBoard.setNumColumns(GameParameters.QTD_COLUMN);    //colunas
        gridBoard.setAdapter(new StateAdapter(this, boardPartsList));
        gridBoard.setBackgroundColor(getResources().getColor(R.color.board_background));

    }

    private void putCoinOnState(int position) {

        //TODO Efeito de moeda caindo
        if (gameManager.getRound() == 1) {
            boardPartsList.set(position, GameParameters.YELLOW_COIN);   // Coloca Moeda Amarela na célula
        } else {
            boardPartsList.set(position, GameParameters.RED_COIN);      // Coloca Moeda Vermelha na célula
        }

        gridBoard.setAdapter(new StateAdapter(GameActivity.this, boardPartsList));
    }

}
