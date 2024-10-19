package com.game.tictactoe.game;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
@Slf4j
public class TicTacToeGame {
    private boolean first_turn = true;
    @Getter
    private final Integer[] cells = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    @Getter
    private Integer winner;

    public boolean first(int cell) {
        if (first_turn && winner == null) {
            if (cell > 9 || cell < 1 || cells[cell - 1] != 0) {
                return false;
            }
            cells[cell - 1] = 1;
            check(1);
            first_turn = false;
            return true;
        }
        return false;
    }


    public boolean second(int cell) {
        if (!first_turn && winner == null) {
            if (cell > 9 || cell < 1 || cells[cell - 1] != 0) {
                return false;
            }
            cells[cell - 1] = 2;
            check(2);
            first_turn = true;
            return true;
        }
        return false;
    }

    private void check(int player) {
        for (int i = 0; i < 9; i += 3) {
            if (cells[i] == player && Objects.equals(cells[i], cells[i + 1]) && Objects.equals(cells[i + 1], cells[i + 2])) {
                winner = player;
                return;
            }
        }
        for (int i = 0; i <= 2; i++) {
            if (cells[i] == player && Objects.equals(cells[i], cells[i + 3]) && Objects.equals(cells[i + 3], cells[i + 6])) {
                winner = player;
                return;
            }
        }
        if (cells[0] == player && Objects.equals(cells[0], cells[4]) && Objects.equals(cells[4], cells[8])) {
            winner = player;
            return;
        }
        if (cells[2] == player && Objects.equals(cells[2], cells[4]) && Objects.equals(cells[4], cells[6])) {
            winner = player;
        }
    }

    public boolean isOver() {
        return winner != null;
    }

}
