package co.edu.unal.nlarranagac.reto3.logic;

import java.util.Random;

/**
 * Created by angee on 24/08/2017.
 */

public class TicTacToeGame {
    private char mBoard[];
    public final static int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char EMPTY_SPACE = ' ';
    public static final int NO_WINNER_OR_TIE_YET = 0;
    public static final int TIE = 1;
    public static final int PLAYER_WON = 2;
    public static final int COMPUTER_WON = 3;

    private Random mRand;

    public TicTacToeGame() {
        mBoard = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            mBoard[i] = EMPTY_SPACE;
        mRand = new Random();
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++){
            mBoard[i] = EMPTY_SPACE;
        }
    }

    public void setMove(char player, int location){
        mBoard[location] = player;
    }

    public int getComputerMove(){
        return findBestMove(mBoard);
    }

    private boolean movesLeft(char [] board){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int pos = i*3 + j;
                if(board[pos] == EMPTY_SPACE)
                    return true;
            }
        }
        return false;
    }
    int minimax(char [] board, int depth, boolean isMax){
        int score = evaluate(board);
        if(score == 10)
            return score;

        if(score == -10)
            return score;

        if(!movesLeft(board))
            return 0;
        if(isMax){
            int best = -1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    int pos = i*3 + j;
                    if(board[pos] == EMPTY_SPACE){
                        board[pos] = COMPUTER_PLAYER;
                        best = Math.max(best,
                                minimax(board, depth+ 1, !isMax));
                        board[pos] = EMPTY_SPACE;
                    }
                }
            }
            return best;
        }
        else{
            int best = 1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    int pos = i*3 + j;
                    if(board[pos] == EMPTY_SPACE){
                        board[pos] = HUMAN_PLAYER;
                        best = Math.min(best,
                                minimax(board, depth + 1, !isMax));
                        board[pos] = EMPTY_SPACE;
                    }
                }
            }
            return best;
        }
    }

    int findBestMove(char [] board){
        int bestVal = -1000;
        int bestMove = -1;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int pos = i*3 + j;
                if(board[pos] == EMPTY_SPACE){
                    board[pos] = COMPUTER_PLAYER;
                    int moveVal = minimax(board, 0, false);
                    board[pos] = EMPTY_SPACE;
                    if(moveVal > bestVal){
                        bestMove = pos;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }


    public int evaluate(char[] board) {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (board[i] == HUMAN_PLAYER &&
                    board[i+1] == HUMAN_PLAYER &&
                    board[i+2]== HUMAN_PLAYER)
                return -10;
            if (board[i] == COMPUTER_PLAYER &&
                    board[i+1]== COMPUTER_PLAYER &&
                    board[i+2] == COMPUTER_PLAYER)
                return +10;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (board[i] == HUMAN_PLAYER &&
                    board[i+3] == HUMAN_PLAYER &&
                    board[i+6]== HUMAN_PLAYER)
                return -10;
            if (board[i] == COMPUTER_PLAYER &&
                    board[i+3] == COMPUTER_PLAYER &&
                    board[i+6]== COMPUTER_PLAYER)
                return +10;
        }

        // Check for diagonal wins
        if ((board[0] == HUMAN_PLAYER &&
                board[4] == HUMAN_PLAYER &&
                board[8] == HUMAN_PLAYER) ||
                (board[2] == HUMAN_PLAYER &&
                        board[4] == HUMAN_PLAYER &&
                        board[6] == HUMAN_PLAYER))
            return -10;
        if ((board[0] == COMPUTER_PLAYER &&
                board[4] == COMPUTER_PLAYER &&
                board[8] == COMPUTER_PLAYER) ||
                (board[2] == COMPUTER_PLAYER &&
                        board[4] == COMPUTER_PLAYER &&
                        board[6] == COMPUTER_PLAYER))
            return +10;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (board[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }
        // If we make it through the previous loop, all places are taken, so it's a tie
        return 0;
    }
    // Check for a winner.  Return
    //  0 if no winner or tie yet
    //  1 if it's a tie
    //  2 if X won
    //  3 if O won
    public int checkForWinner() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2]== HUMAN_PLAYER)
                return PLAYER_WON;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+1]== COMPUTER_PLAYER &&
                    mBoard[i+2] == COMPUTER_PLAYER)
                return COMPUTER_WON;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6]== HUMAN_PLAYER)
                return PLAYER_WON;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+3] == COMPUTER_PLAYER &&
                    mBoard[i+6]== COMPUTER_PLAYER)
                return COMPUTER_WON;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return PLAYER_WON;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return COMPUTER_WON;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return NO_WINNER_OR_TIE_YET;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return TIE;
    }
}
