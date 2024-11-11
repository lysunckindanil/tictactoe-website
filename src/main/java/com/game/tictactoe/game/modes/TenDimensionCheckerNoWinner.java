package com.game.tictactoe.game.modes;

import com.game.tictactoe.game.TicTacToeGameWinnerChecker;

class TenDimensionCheckerNoWinner implements TicTacToeGameWinnerChecker {
    @Override
    public boolean checkWinner(int[] positions, int player) {
        return false;
    }
}
