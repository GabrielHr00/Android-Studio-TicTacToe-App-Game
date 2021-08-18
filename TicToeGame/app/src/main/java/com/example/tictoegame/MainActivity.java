package com.example.tictoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;

    private TextView player1;
    private TextView player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = findViewById(R.id.player1_points);
        player2 = findViewById(R.id.player2_points);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((Button) v).setText("X");
        }
        else{
            ((Button) v).setText("O");
        }
        roundCount++;
        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }
            else{
                player2Wins();
            }
        }else if(roundCount == 9){
            draw();
        }else{
            player1Turn = !player1Turn;
        }

    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Nobody wins! Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void updatePointsText() {
        player1.setText("1. Player: " + player1Points);
        player2.setText("2. Player: " + player2Points);
    }


    private boolean checkForWin(){
        String[][] fields = new String[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                fields[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(int i = 0; i < 3; i++){
            if(fields[i][0].equals(fields[i][1])
            && fields[i][0].equals(fields[i][2])
            && !fields[i][0].equals("")){
                return true;
            }
        }
        for(int i = 0; i < 3; i++){
            if(fields[0][i].equals(fields[1][i])
                    && fields[0][i].equals(fields[2][i])
                    && !fields[0][i].equals("")){
                return true;
            }
        }

        if(fields[0][0].equals(fields[1][1])
                && fields[0][0].equals(fields[2][2])
                && !fields[0][0].equals("")){
            return true;
        }
        if(fields[0][2].equals(fields[1][1])
                && fields[0][2].equals(fields[2][0])
                && !fields[0][2].equals("")){
            return true;
        }
        return false;
    }
}