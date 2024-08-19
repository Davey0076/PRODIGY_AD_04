package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 0 - Empty, 1 - X, 2 - O
    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    // Winning positions
    int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}              // diagonals
    };

    int currentPlayer = 1; // 1 - X, 2 - O
    boolean gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetGame(null); // Initialize the game on creation
    }

    public void playerTap(View view) {
        Button tappedButton = (Button) view;
        int tappedPosition = Integer.parseInt(tappedButton.getTag().toString());

        if (gameState[tappedPosition] == 0 && gameActive) {
            // Update game state and UI
            gameState[tappedPosition] = currentPlayer;
            tappedButton.setText(currentPlayer == 1 ? "X" : "O");

            // Check for a win or draw
            checkGameStatus();

            // Change player
            currentPlayer = currentPlayer == 1 ? 2 : 1;
        }
    }

    private void checkGameStatus() {
        TextView status = findViewById(R.id.gameTitle);

        // Check for a win
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 0) {

                // We have a winner
                gameActive = false;
                String winner = currentPlayer == 1 ? "X" : "O";
                status.setText(winner + " has won!");
                return;
            }
        }

        // Check for a draw
        boolean draw = true;
        for (int state : gameState) {
            if (state == 0) {
                draw = false;
                break;
            }
        }

        if (draw && gameActive) {
            gameActive = false;
            status.setText("It's a Draw!");
        } else if (gameActive) {
            status.setText("Player " + (currentPlayer == 1 ? "O's" : "X's") + " Turn");
        }
    }

    public void resetGame(View view) {
        gameActive = true;
        currentPlayer = 1;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 0;
        }

        GridLayout gridLayout = findViewById(R.id.layoutTAC);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
        }

        TextView status = findViewById(R.id.gameTitle);
        status.setText("Player X's Turn");
    }
}
