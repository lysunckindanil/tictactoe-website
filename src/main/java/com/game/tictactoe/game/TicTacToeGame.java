package com.game.tictactoe.game;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TicTacToeGame {
    private boolean first_turn = true;
    private final Integer[] cells = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    @Getter
    private Integer won;

    public boolean first(int cell) {
        if (first_turn && won == null) {
            if (cell > 9 || cell < 1 || cells[cell] == 0) {
                return false;
            }
            cells[cell] = 1;
            check(1);
            first_turn = false;
            return true;
        }
        return false;
    }


    public boolean second(int cell) {
        if (!first_turn && won == null) {
            if (cell > 9 || cell < 1 || cells[cell] == 0) {
                return false;
            }
            cells[cell] = 2;
            check(2);
            first_turn = true;
            return true;
        }
        return false;
    }

    private void check(int player) {
        for (int i = 1; i < 10; i += 3) {
            if (cells[i] == player && Objects.equals(cells[i], cells[i + 1]) && Objects.equals(cells[i + 1], cells[i + 2])) {
                won = player;
                return;
            }
        }
        for (int i = 1; i <= 3; i++) {
            if (cells[i] == player && Objects.equals(cells[i], cells[i + 3]) && Objects.equals(cells[i + 3], cells[i + 6])) {
                won = player;
                return;
            }
        }
        if (cells[1] == player && Objects.equals(cells[1], cells[5]) && Objects.equals(cells[5], cells[9])) {
            won = player;
            return;
        }
        if (cells[3] == player && Objects.equals(cells[3], cells[5]) && Objects.equals(cells[5], cells[7])) {
            won = player;
        }
    }

}
