package co.edu.unal.nlarranagac.reto3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.unal.nlarranagac.reto3.logic.TicTacToeGame;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    private TicTacToeGame mGame;
    private Button []mBoardButtons;
    private TextView mInfoTextView;
    private boolean mGameOver;
    private TextView mPlayerWinsCounterTextView;
    private TextView mComputerWinsCounterTextView;
    private TextView mTiesCounterTextView;
    private int mPlayerWinsCounter;
    private int mComputerWinsCounter;
    private int mTiesCounter;

    private void startNewGame(){
        mGame.clearBoard();

        for(int i = 0; i < mBoardButtons.length; i++){
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mInfoTextView.setText(R.string.player_goes_first);

        }
        mGameOver = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);
        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);
        mPlayerWinsCounter = mComputerWinsCounter = mTiesCounter = 0;

        mInfoTextView = (TextView) findViewById(R.id.tv_info);
        mPlayerWinsCounterTextView = (TextView) findViewById(R.id.tv_player_counter);
        mComputerWinsCounterTextView = (TextView) findViewById(R.id.tv_computer_counter);
        mTiesCounterTextView = (TextView) findViewById(R.id.tv_tie_counter);
        mGame = new TicTacToeGame();
        startNewGame();
    }

    private void setMove(char player, int location){
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player == TicTacToeGame.HUMAN_PLAYER){
            mBoardButtons[location].setTextColor(Color.rgb(0,200,0));
        }
        else{
            mBoardButtons[location].setTextColor(Color.rgb(200,0,0));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_new_game){
            startNewGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ButtonClickListener implements View.OnClickListener{
        int location;
        public ButtonClickListener(int location){
            this.location = location;
        }

        @Override
        public void onClick(View view) {
            if(mBoardButtons[location].isEnabled() && !mGameOver){
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                int status = mGame.checkForWinner();
                if(status == TicTacToeGame.NO_WINNER_OR_TIE_YET){
                    mInfoTextView.setText(R.string.android_turn);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    status = mGame.checkForWinner();
                }

                if(status == TicTacToeGame.NO_WINNER_OR_TIE_YET){
                    mInfoTextView.setText(R.string.player_turn);
                }
                else if(status == TicTacToeGame.TIE){
                    mInfoTextView.setText(R.string.tie);
                    mGameOver = true;
                    mTiesCounter++;
                    mTiesCounterTextView.setText(Integer.toString(mTiesCounter));
                }
                else if(status == TicTacToeGame.PLAYER_WON){
                    mInfoTextView.setText(R.string.player_won);
                    mGameOver = true;
                    mPlayerWinsCounter++;
                    mPlayerWinsCounterTextView.setText(Integer.toString(mPlayerWinsCounter));
                }
                else if(status == TicTacToeGame.COMPUTER_WON){
                    mInfoTextView.setText(R.string.android_won);
                    mGameOver = true;
                    mComputerWinsCounter++;
                    mComputerWinsCounterTextView.setText(Integer.toString(mComputerWinsCounter));
                }
            }
        }

    }
}
