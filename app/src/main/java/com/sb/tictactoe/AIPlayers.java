package com.sb.tictactoe;

public abstract class AIPlayers {
    protected BoardActivity board;

    public AIPlayers(BoardActivity board){
        this.board = board ;
    }

    protected abstract int move() ;
}
