package com.game.tictactoe.game;

import com.game.tictactoe.game.modes.TicTacToeGames;
import com.game.tictactoe.game.util.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeGameTest {

    TicTacToeGame game;
    int dimension;

    @BeforeEach
    void setUp() {
        Random rand = new Random();
        dimension = rand.nextInt(3, 100);
        game = TicTacToeGames.resolve(dimension);
    }

    // we test cells as though array starts from 1 and ends dimension*dimension
    // but actual array inside stats from 1 and ends dimension*dimension-1
    // first
    @Test
    void firstMethod() throws GameException {
        game.first(1);
        assertEquals(game.getCells()[0], 1);
    }

    @Test
    void firstMethodTurnBeforeSecond() throws GameException {
        game.first(1);
        assertThrows(GameException.class, () -> game.first(2));
    }

    @Test
    void firstMethodCellLowerThanOne() {
        assertThrows(GameException.class, () -> game.first(0));
    }

    @Test
    void firstMethodCellGreaterThanValid() {
        assertThrows(GameException.class, () -> game.first(dimension * dimension + 1));
    }

    @Test
    void firstMethodFirstCellIsNotEmpty() {
        game.getCells()[0] = 1;
        assertThrows(GameException.class, () -> game.first(1));
    }


    @Test
    void firstMethodLastCellIsNotEmpty() {
        game.getCells()[dimension * dimension - 1] = 1;
        assertThrows(GameException.class, () -> game.first(dimension * dimension));
    }

    @Test
    void firstMethodCellOccupiedBySecond() {
        game.getCells()[dimension * dimension - 1] = 2;
        assertThrows(GameException.class, () -> game.first(dimension * dimension));
    }

    @Test
    void firstMethodIfGameEnded() throws GameException {
        TicTacToeGame game = TicTacToeGames.resolve(3);
        makeSecondWin(game);
        assertThrows(GameException.class, () -> game.first(3));
    }

    // second
    @Test
    void secondMethod() throws GameException {
        game.first(1);
        game.second(2);
        assertEquals(game.getCells()[1], 2);
    }

    @Test
    void secondMethodTurnBeforeFirst() {
        assertThrows(GameException.class, () -> game.second(1));
    }

    @Test
    void secondMethodCellGreaterThanValid() throws GameException {
        game.first(1);
        assertThrows(GameException.class, () -> game.second(dimension * dimension + 1));
    }

    @Test
    void secondMethodCellLowerThanOne() throws GameException {
        game.first(1);
        assertThrows(GameException.class, () -> game.second(0));
    }

    @Test
    void secondMethodFirstCellIsNotEmpty() throws GameException {
        game.first(2);
        game.getCells()[0] = 2;
        assertThrows(GameException.class, () -> game.second(1));
    }


    @Test
    void secondMethodLastCellIsNotEmpty() throws GameException {
        game.first(1);
        game.getCells()[dimension * dimension - 1] = 2;
        assertThrows(GameException.class, () -> game.second(dimension * dimension));
    }

    @Test
    void secondMethodCellOccupiedByFirst() throws GameException {
        game.first(1);
        game.getCells()[dimension * dimension - 1] = 1;
        assertThrows(GameException.class, () -> game.second(dimension * dimension));
    }

    @Test
    void secondMethodIfGameEnded() throws GameException {
        TicTacToeGame game = TicTacToeGames.resolve(3);
        makeFirstWin(game);
        assertThrows(GameException.class, () -> game.second(9));
    }

    // isOver method

    @Test
    void isOverMethodIfNobodyWins() {
        assertFalse(game.isOver());
    }

    @Test
    void isOverMethodIfNobodyWinsWithTurns() throws GameException {
        game.first(1);
        game.second(2);
        assertFalse(game.isOver());
    }

    @Test
    void isOverMethodIfFirstWins() throws GameException {
        TicTacToeGame game = TicTacToeGames.resolve(3);
        makeFirstWin(game);
        assertTrue(game.isOver());
    }

    @Test
    void isOverMethodIfSecondWins() throws GameException {
        TicTacToeGame game = TicTacToeGames.resolve(3);
        makeSecondWin(game);
        assertTrue(game.isOver());
    }

    // getWinner
    @Test
    void getWinnerIfFirstWins() throws GameException {
        TicTacToeGame game = TicTacToeGames.resolve(3);
        makeFirstWin(game);
        assertEquals(1, game.getWinner());
    }

    @Test
    void getWinnerIfSecondWins() throws GameException {
        TicTacToeGame game = TicTacToeGames.resolve(3);
        makeSecondWin(game);
        assertEquals(2, game.getWinner());
    }


    void makeFirstWin(TicTacToeGame game) throws GameException {
        game.first(1);
        game.second(7);
        game.first(2);
        game.second(8);
        game.first(3);
    }

    void makeSecondWin(TicTacToeGame game) throws GameException {
        game.first(1);
        game.second(7);
        game.first(2);
        game.second(8);
        game.first(4);
        game.second(9);
    }
}