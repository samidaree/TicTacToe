package com.sb.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class HardLevelAI extends AIPlayers{
    public HardLevelAI(BoardActivity board){
        super(board);
    }

    @Override
    protected int move(){
        int []result = minimax(8, 2);
        return result[1] ;
    }

    protected int [] minimax(int depth, int playerTurn){

        List<Integer> moves = generateMoves();
        int bestScore = (playerTurn==1)? Integer.MIN_VALUE : Integer.MAX_VALUE ;
        int currentScore;
        int bestBox = -1 ;

        if (moves.isEmpty() || depth == 0){
            bestScore=evaluate();
        }

        else {
            for (Integer i : moves){
                board.setBoxPositions(i,playerTurn);
                if (playerTurn==2){
                    currentScore=minimax(depth-1, 1)[0];
                    if (currentScore > bestScore){
                        bestScore = currentScore ;
                        bestBox = i ;
                    }
                }

                else {
                    currentScore = minimax(depth-1, 2)[0];
                    if (currentScore < bestScore){
                        bestScore = currentScore;
                        bestBox = i ;
                    }
                }
                board.setBoxPositions(i,0);
            }
        }
        return new int[] {bestScore, bestBox};




    }

    protected List<Integer> generateMoves(){
        List <Integer> possibleMoves = new ArrayList<>() ;

        if (this.board.checkResults() || this.board.getTotalSelectedBoxes() == 9){
            return possibleMoves;
        }
        for (int i = 0; i<board.getBoxPositions().length; i++){
            if(board.isBoxSelectable(i))
                possibleMoves.add(i);
        }
        return possibleMoves ;
    }

    protected int evaluate(){
        if (board.checkResults() && board.getPlayerTurn()==2){
            return 1;
        }
        else if (board.checkResults() && board.getPlayerTurn() == 1)
            return - 1 ;
        return 0 ;
    }



}
