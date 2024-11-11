package com.game.tictactoe.game.modes;

import com.game.tictactoe.game.TicTacToeGame;

public class TicTacToeGames {
    private static TicTacToeGame getThreeDimensionTicTacToeGame() {
        return new TicTacToeGame(3, new ThreeDimensionChecker());
    }

    private static TicTacToeGame getTenDimensionTicTacToeGame() {
        return new TicTacToeGame(10, new TenDimensionCheckerNoWinner());
    }

    private static TicTacToeGame getAnyDimensionTicTacToeGame(int dimension) {
        return new TicTacToeGame(dimension, (positions, player) -> false);
    }

    public static TicTacToeGame getGameByDimension(int dimension) {
        if (dimension == 3) {
            return getThreeDimensionTicTacToeGame();
        }
        if (dimension == 10) {
            return getTenDimensionTicTacToeGame();
        }
        return getAnyDimensionTicTacToeGame(dimension);
    }
}
