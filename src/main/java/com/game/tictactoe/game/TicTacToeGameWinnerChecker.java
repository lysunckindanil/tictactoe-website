package com.game.tictactoe.game;

public interface TicTacToeGameWinnerChecker {
    boolean checkWinner(Integer[] positions, Integer player);
}
