package com.game.tictactoe.game;

import com.game.tictactoe.game.util.exceptions.GameException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicTacToeGame {
    private boolean first_turn = true;
    @Getter
    private final TicTacToeGameWinnerChecker ticTacToeGameWinnerChecker;
    @Getter
    private final int[] cells;
    @Getter
    private int winner;
    @Getter
    private final int dimension;

    public TicTacToeGame(int dimension, TicTacToeGameWinnerChecker ticTacToeGameWinnerChecker) {
        this.ticTacToeGameWinnerChecker = ticTacToeGameWinnerChecker;
        this.dimension = dimension;
        cells = new int[dimension * dimension];
    }

    // cells array actually starts from zero, but we pass
    // cell's number as the array starts from 1
    public void first(int cell) throws GameException {
        if (first_turn && !isOver()) {
            int cell_in_array = cell - 1;
            if (cell_in_array > cells.length - 1 || cell_in_array < 0 || cells[cell_in_array] != 0) {
                throw new GameException("You can't play here");
            }
            cells[cell_in_array] = 1;
            if (ticTacToeGameWinnerChecker.checkWinner(cells, 1)) winner = 1;
            first_turn = false;
        } else {
            throw new GameException("It's not your turn");
        }
    }


    public void second(int cell) throws GameException {
        if (!first_turn && !isOver()) {
            int cell_in_array = cell - 1;
            if (cell_in_array > cells.length - 1 || cell_in_array < 0 || cells[cell_in_array] != 0) {
                throw new GameException("You can't play here");
            }
            cells[cell_in_array] = 2;
            if (ticTacToeGameWinnerChecker.checkWinner(cells, 2)) winner = 2;
            first_turn = true;
        } else {
            throw new GameException("It's not your turn");
        }

    }

    public boolean isOver() {
        return winner != 0;
    }

}
