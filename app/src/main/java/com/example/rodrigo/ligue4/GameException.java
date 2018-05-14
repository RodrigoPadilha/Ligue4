package com.example.rodrigo.ligue4;

/**
 * Created by rodrigo on 30/04/2018.
 */

public class GameException extends Exception {

    private String msg;

    public GameException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }

}



