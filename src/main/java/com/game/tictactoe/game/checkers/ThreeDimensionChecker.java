package com.game.tictactoe.game.checkers;

import com.game.tictactoe.game.TicTacToeGameWinnerChecker;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ThreeDimensionChecker implements TicTacToeGameWinnerChecker {
    @Override
    public boolean checkWinner(Integer[] positions, Integer player) {
        for (int i = 0; i < 9; i += 3) {
            if (Objects.equals(positions[i], player) && Objects.equals(positions[i], positions[i + 1]) && Objects.equals(positions[i + 1], positions[i + 2])) {
                return true;
            }
        }
        for (int i = 0; i <= 2; i++) {
            if (Objects.equals(positions[i], player) && Objects.equals(positions[i], positions[i + 3]) && Objects.equals(positions[i + 3], positions[i + 6])) {
                return true;
            }
        }
        if (Objects.equals(positions[0], player) && Objects.equals(positions[0], positions[4]) && Objects.equals(positions[4], positions[8])) {
            return true;
        }
        return Objects.equals(positions[2], player) && Objects.equals(positions[2], positions[4]) && Objects.equals(positions[4], positions[6]);
    }
}
