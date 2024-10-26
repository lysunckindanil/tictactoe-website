package com.game.tictactoe.game.modes;

import com.game.tictactoe.game.TicTacToeGame;
import com.game.tictactoe.game.checkers.TenDimensionChecker;
import com.game.tictactoe.game.checkers.ThreeDimensionChecker;

public class GameModes {
    public static TicTacToeGame getThreeDimensionTicTacToeGame() {
        return new TicTacToeGame(3, new ThreeDimensionChecker());
    }

    public static TicTacToeGame getTenDimensionTicTacToeGame() {
        return new TicTacToeGame(10, new TenDimensionChecker());
    }

    public static TicTacToeGame getAnyDimensionTicTacToeGame(int dimension) {
        return new TicTacToeGame(dimension, (positions, player) -> false);
    }

    public static TicTacToeGame resolve(int dimension) {
        if (dimension == 3) {
            return getThreeDimensionTicTacToeGame();
        }
        if (dimension == 10) {
            return getTenDimensionTicTacToeGame();
        }
        return getAnyDimensionTicTacToeGame(dimension);
    }
}
