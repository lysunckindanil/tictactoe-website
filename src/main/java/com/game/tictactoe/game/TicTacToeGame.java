package com.game.tictactoe.game;

import com.game.tictactoe.game.util.GameException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TicTacToeGame {
    private boolean first_turn = true;
    private final TicTacToeGameWinnerChecker ticTacToeGameWinnerChecker;

    @Getter
    private final Integer[] cells;
    @Getter
    private Integer winner;
    @Getter
    private final int dimension;

    public TicTacToeGame(int dimension, TicTacToeGameWinnerChecker ticTacToeGameWinnerChecker) {
        this.ticTacToeGameWinnerChecker = ticTacToeGameWinnerChecker;
        this.dimension = dimension;
        cells = new Integer[dimension * dimension];
        Arrays.fill(cells, 0);
    }

    public void first(int cell) throws GameException {
        if (first_turn && winner == null) {
            if (cell > cells.length || cell < 1 || cells[cell - 1] != 0) {
                throw new GameException("You can't play here");
            }
            cells[cell - 1] = 1;
            if (ticTacToeGameWinnerChecker.checkWinner(cells, 1)) winner = 1;
            first_turn = false;
        } else {
            throw new GameException("It's not your turn");
        }
    }


    public void second(int cell) throws GameException {
        if (!first_turn && winner == null) {
            if (cell > cells.length || cell < 1 || cells[cell - 1] != 0) {
                throw new GameException("You can't play here");
            }
            cells[cell - 1] = 2;
            if (ticTacToeGameWinnerChecker.checkWinner(cells, 2)) winner = 2;
            first_turn = true;
        } else {
            throw new GameException("It's not your turn");
        }

    }

    public boolean isOver() {
        return winner != null;
    }

}
