package com.game.tictactoe.game.modes;

import com.game.tictactoe.game.TicTacToeGameWinnerChecker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreeDimensionCheckerTest {

    static TicTacToeGameWinnerChecker checker;

    @BeforeAll
    static void setUp() {
        checker = TicTacToeGames.resolve(3).getTicTacToeGameWinnerChecker();
    }

    @Test
    void checkWinnerMethodIfWinner() {
        assertTrue(checker.checkWinner(new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0}, 1));
        assertTrue(checker.checkWinner(new int[]{0, 0, 0, 1, 1, 1, 0, 0, 0}, 1));
        assertTrue(checker.checkWinner(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1}, 1));
        assertTrue(checker.checkWinner(new int[]{1, 0, 0, 1, 0, 0, 1, 0, 0}, 1));
        assertTrue(checker.checkWinner(new int[]{0, 1, 0, 0, 1, 0, 0, 1, 0}, 1));
        assertTrue(checker.checkWinner(new int[]{0, 0, 1, 0, 0, 1, 0, 0, 1}, 1));
        assertTrue(checker.checkWinner(new int[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, 1));
        assertTrue(checker.checkWinner(new int[]{0, 0, 1, 0, 1, 0, 1, 0, 0}, 1));

        assertTrue(checker.checkWinner(new int[]{2, 2, 2, 0, 0, 0, 0, 0, 0}, 2));
        assertTrue(checker.checkWinner(new int[]{0, 0, 0, 2, 2, 2, 0, 0, 0}, 2));
        assertTrue(checker.checkWinner(new int[]{0, 0, 0, 0, 0, 0, 2, 2, 2}, 2));
        assertTrue(checker.checkWinner(new int[]{2, 0, 0, 2, 0, 0, 2, 0, 0}, 2));
        assertTrue(checker.checkWinner(new int[]{0, 2, 0, 0, 2, 0, 0, 2, 0}, 2));
        assertTrue(checker.checkWinner(new int[]{0, 0, 2, 0, 0, 2, 0, 0, 2}, 2));
        assertTrue(checker.checkWinner(new int[]{2, 0, 0, 0, 2, 0, 0, 0, 2}, 2));
        assertTrue(checker.checkWinner(new int[]{0, 0, 2, 0, 2, 0, 2, 0, 0}, 2));
    }

    @Test
    void checkWinnerMethodIfNotWinner() {
        assertFalse(checker.checkWinner(new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0}, 2));
        assertFalse(checker.checkWinner(new int[]{1, 1, 0, 0, 0, 0, 0, 0, 0}, 1));
        assertFalse(checker.checkWinner(new int[]{0, 1, 0, 1, 0, 0, 0, 1, 0}, 1));
        assertFalse(checker.checkWinner(new int[]{0, 0, 1, 0, 0, 1, 0, 0, 0}, 1));
        assertFalse(checker.checkWinner(new int[]{1, 0, 0, 0, 1, 0, 0, 0, 2}, 1));
        assertFalse(checker.checkWinner(new int[]{0, 0, 1, 0, 0, 0, 1, 0, 0}, 1));

        assertFalse(checker.checkWinner(new int[]{2, 2, 0, 0, 0, 0, 0, 2, 1}, 2));
        assertFalse(checker.checkWinner(new int[]{0, 0, 0, 2, 2, 1, 0, 2, 0}, 2));
        assertFalse(checker.checkWinner(new int[]{0, 0, 2, 0, 0, 0, 2, 1, 2}, 2));
        assertFalse(checker.checkWinner(new int[]{2, 1, 1, 1, 1, 0, 2, 0, 2}, 2));

    }
}