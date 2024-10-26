package com.game.tictactoe.game.checkers;

import com.game.tictactoe.game.TicTacToeGameWinnerChecker;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ThreeDimensionChecker implements TicTacToeGameWinnerChecker {
    @Override
    public boolean checkWinner(int[] positions, int player) {
        for (int i = 0; i < 9; i += 3) {
            if (positions[i] == player && positions[i] == positions[i + 1] && positions[i + 1] == positions[i + 2]) {
                return true;
            }
        }
        for (int i = 0; i <= 2; i++) {
            if (positions[i] == player && positions[i] == positions[i + 3] && positions[i + 3] == positions[i + 6]) {
                return true;
            }
        }
        if (positions[0] == player && positions[0] == positions[4] && positions[4] == positions[8]) {
            return true;
        }
        return positions[2] == player && positions[2] == positions[4] && positions[4] == positions[6];
    }
}
