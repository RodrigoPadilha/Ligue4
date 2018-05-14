package com.example.rodrigo.ligue4;

/**
 * Created by rodrigo on 30/04/2018.
 */

public class InvalidMoveException extends GameException {

    public InvalidMoveException() {
        super("Movimento inválido. Posição já foi selecionada.");
    }

}
