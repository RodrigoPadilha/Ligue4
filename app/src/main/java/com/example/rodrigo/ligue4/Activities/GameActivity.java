package com.example.rodrigo.ligue4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigo.ligue4.Adapters.StateAdapter;
import com.example.rodrigo.ligue4.Dialogs.OptionsDialogIntf;
import com.example.rodrigo.ligue4.Dialogs.TestDialog;
import com.example.rodrigo.ligue4.GameManager;
import com.example.rodrigo.ligue4.GameParameters;
import com.example.rodrigo.ligue4.InvalidMoveException;
import com.example.rodrigo.ligue4.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rodrigo on 26/04/2018.
 */

public class GameActivity extends AppCompatActivity implements OptionsDialogIntf, Serializable {

    //private static final String TESTE = "TESTE";

    private FrameLayout scoreboard;
    private TextView yellowScore;
    private TextView redScore;
    private GridView gridBoard;

    private int gameModeOpt;
    private ArrayList<Integer> boardPartsList;
    private GameManager gameManager;
    private TestDialog testDialog;
    //private ScoreBoardDialog scoreBoardDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //View view = LayoutInflater.from(this).inflate(R.layout.scoreboard, scoreboard,true);
        final View view = LayoutInflater.from(this).inflate(R.layout.scoreboard, null);

        scoreboard = findViewById(R.id.id_scoreboard);
        gridBoard = findViewById(R.id.id_game_board);
        yellowScore = view.findViewById(R.id.yellow_score);
        redScore = view.findViewById(R.id.red_score);

        scoreboard.addView(view);

        //INÍCIO
        dataReceive();
        newGame();

        view.findViewById(R.id.btnNewGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
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

                        gridBoard.setEnabled(false);
                        // Incrementa Placar
                        gameManager.addScore();
                        yellowScore.setText(Integer.toString(gameManager.getYellowWins()));
                        redScore.setText(Integer.toString(gameManager.getRedWins()));
                        Toast.makeText(GameActivity.this, vencedor, Toast.LENGTH_LONG).show();

                        //TODO Abrir Dialog Personalizada
                        //scoreBoardDialog = new ScoreBoardDialog(GameActivity.this, gameManager.getYellowWins(), gameManager.getRedWins());

//                        TestDialog testDialog = TestDialog.newInstance(GameActivity.this, gameManager.getYellowWins(), gameManager.getRedWins());
//                        testDialog.show(getSupportFragmentManager(),"RODRIGO");

                        openScoreBoardDialog();

                    }

                    gameManager.changeRound();

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
        outState.putSerializable("gameManager", gameManager); // Salva controlador do game

//        if(testDialog != null) {
//            outState.putSerializable("testDialog", (Serializable) getSupportFragmentManager().findFragmentByTag("RODRIGO")); // Salva controlador do game
//        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Verifica se o bundle existe, caso sim, você verifica se a lista de peças salvas
        if (savedInstanceState != null) {

            if(savedInstanceState.containsKey("boardPartsList")) {
                boardPartsList = savedInstanceState.getIntegerArrayList("boardPartsList");  // Recupera o valor que estava anteriormente
                gameManager = (GameManager) savedInstanceState.getSerializable("gameManager");  // Recupera o valor que estava anteriormente
                loadBoard();                                                                    // Gera o Exibe o Tabulerio após virar a tela
            }
//            if(savedInstanceState.containsKey("testDialog")){
//                testDialog = (TestDialog) savedInstanceState.getSerializable("testDialog");
//            }
        }

    }

    private void dataReceive() {

        Intent intent = getIntent();
        gameModeOpt = intent.getIntExtra("gameMode", 1);

    }

    private void newGame(){

        partsList();
        loadBoard();

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

        if(gameManager == null)
            gameManager = new GameManager(boardPartsList);
        else
            gameManager.setBoardPartsList(boardPartsList);

        yellowScore.setText(Integer.toString(gameManager.getYellowWins()));
        redScore.setText(Integer.toString(gameManager.getRedWins()));

        gridBoard.setColumnWidth(GameParameters.QTD_LINE);    //linhas
        gridBoard.setNumColumns(GameParameters.QTD_COLUMN);    //colunas
        gridBoard.setAdapter(new StateAdapter(this, boardPartsList));
        gridBoard.setBackgroundColor(getResources().getColor(R.color.board_background));
        gridBoard.setEnabled(true);

    }

    private void
    putCoinOnState(int position) {

        //TODO Efeito de moeda caindo
        if (gameManager.getRound() == 1) {
            boardPartsList.set(position, GameParameters.YELLOW_COIN);   // Coloca Moeda Amarela na célula
        } else {
            boardPartsList.set(position, GameParameters.RED_COIN);      // Coloca Moeda Vermelha na célula
        }

        gridBoard.setAdapter(new StateAdapter(GameActivity.this, boardPartsList));
    }

    private void openScoreBoardDialog(){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        testDialog = new TestDialog();

        Bundle args = new Bundle();

        args.putSerializable("classCallBack", GameActivity.this);
        args.putInt("yellowWins(", gameManager.getYellowWins());
        args.putInt("redWins", gameManager.getRedWins());

        testDialog.setArguments(args);
        testDialog.show(ft,"RODRIGO");
        Log.i("TESTE","RODRIGO");

    }

    private void closeScoreBoardDialog(){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        testDialog = (TestDialog) getSupportFragmentManager().findFragmentByTag("RODRIGO");
        if(testDialog != null) {
            testDialog.dismiss();
            ft.remove(testDialog);
        }

    }

    @Override
    public void dialogAnswer(Integer response) {

        if(response == 0) {
            newGame();
        } else{
            finish();
        }

        closeScoreBoardDialog();
    }
}
