package com.example.rodrigo.ligue4;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by rodrigo on 28/04/2018.
 */

public class GameManager {

    private static final String TESTE = "TESTE";

    private int round;
    private ArrayList<Integer> boardPartsList;
    private Integer[][] matrixBidirecional;

    public GameManager(ArrayList<Integer> boardPartsList) {

        this.boardPartsList = boardPartsList;
        matrixBidirecional = new Integer[GameParameters.QTD_LINE][GameParameters.QTD_COLUMN];
        Random randomGenerator = new Random();
        round = randomGenerator.nextInt(1) + 1;

    }

    public int getStackIndex(int position) throws InvalidMoveException {


        if (boardPartsList.get(position) != 0)
            throw new InvalidMoveException();

        int index = position;
        while (boardPartsList.get(index) == 0) {

            index += GameParameters.QTD_COLUMN;
            if (index >= boardPartsList.size() || (boardPartsList.get(index) != 0)) {
                return position;
            }
            position = index;
        }

        return position;
    }

    public boolean hasWinner() {

        boolean winner = false;

        convertListToMatrix();

        winner = winner || searchWinner(GameParameters.HORIZONTAL);
        winner = winner || searchWinner(GameParameters.VERTICAL);
        winner = winner || searchWinner(GameParameters.DIAGONAL_RIGTH);
        winner = winner || searchWinner(GameParameters.DIAGONAL_LEFT);

        return winner;

    }

    public void convertListToMatrix() {

        int index = 0;
        for (int linha = 0; linha < GameParameters.QTD_LINE; linha++) {
            for (int coluna = 0; coluna < GameParameters.QTD_COLUMN; coluna++) {
                matrixBidirecional[linha][coluna] = boardPartsList.get(index);
                index++;
            }
        }

    }

    private boolean searchWinner(int orientation) {

        int n;
        if (orientation > GameParameters.VERTICAL) {
            n = orientation - 2;
        } else {
            n = orientation;
        }

        int limI = (n * GameParameters.QTD_COLUMN + (1 - n) * GameParameters.QTD_LINE);
        int limJ = (n * GameParameters.QTD_LINE + (1 - n) * GameParameters.QTD_COLUMN);
        for (int i = 0; i < limI; i++) {
            for (int j = 0; j < limJ; j++) {

                int line = n * j + (1 - n) * i;
                int column = n * i + (1 - n) * j;
                if(matrixBidirecional[line][column] == round) { // Verifica apenas as peças do jogador que fez último movimento
                    int lig = 0;
                    for (int hit = 0; hit < 4; hit++) {
                        boolean a = (j + hit) < limJ;           // Se a posição é menor que largura
                        boolean b = true;                       // Se existe possibilidade de ligar 4

                        if (orientation == GameParameters.HORIZONTAL) {
                            line = i;
                            column = j + hit;
                        } else if (orientation == GameParameters.VERTICAL) {
                            line = j + hit;
                            column = i;
                        } else if (orientation == GameParameters.DIAGONAL_RIGTH) {
                            b = (i + hit) < limI;
                            line = i + hit;
                            column = j + hit;
                        } else if (orientation == GameParameters.DIAGONAL_LEFT) {
                            a = (j - hit) >= 0;
                            b = (i + hit) < limI;
                            line = j - hit;
                            column = i + hit;
                        }

                        if (a && b && matrixBidirecional[line][column] == round) {
                            lig++;
                            if (lig >= 4)
                                return true;
                        } else {
                            break;
                        }

                    } // FIM for de busca 4
                } // If é peça do último que jogou
            } // End for j
        } // End for i

        return false;
    }

    public void changeRound() {

        if (round == 1) {
            round += 1;       // Passa a vez
        } else {
            round -= 1;       // Passa a vez
        }
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }


}