package com.game.tictactoe.game;

import com.game.tictactoe.game.util.http.StateHttpEntity;
import com.game.tictactoe.game.modes.TicTacToeGames;
import com.game.tictactoe.game.util.exceptions.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameSessionTest {
    GameSession gameSession;

    @BeforeEach
    void setUp() {
        gameSession = new GameSession("first", TicTacToeGames.getGameByDimension(3));
    }

    @Test
    void connectMethodOrdinary() throws GameException {
        gameSession.connect("second");
        assertEquals("second", gameSession.getPlayer2());
    }

    @Test
    void connectMethodIfSecondPlayerExists() throws GameException {
        gameSession.connect("second");
        assertThrows(GameException.class, () -> gameSession.connect("second-not-allowed"));
    }

    @Test
    void connectMethodIfEqualsFirstPlayer() {
        assertThrows(GameException.class, () -> gameSession.connect("first"));
    }

    @Test
    void connectMethodIfSecondPlayerExistsAlready() throws GameException {
        gameSession.connect("second");
        assertThrows(GameException.class, () -> gameSession.connect("second-not-allowed"));
    }

    @Test
    void getGameStateIfNotOver() {
        assertEquals(new StateHttpEntity(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}
                , false, null), gameSession.getGameState());
    }

    @Test
    void getGameStateIfFirstWins() throws GameException {
        gameSession.connect("second");
        gameSession.move("first", 1);
        gameSession.move("second", 4);
        gameSession.move("first", 2);
        gameSession.move("second", 5);
        gameSession.move("first", 3);
        assertEquals(new StateHttpEntity(new int[]{1, 1, 1, 2, 2, 0, 0, 0, 0}
                , true, "first"), gameSession.getGameState());
    }

    @Test
    void getGameStateIfSecondWins() throws GameException {
        gameSession.connect("second");
        gameSession.move("first", 1);
        gameSession.move("second", 4);
        gameSession.move("first", 2);
        gameSession.move("second", 5);
        gameSession.move("first", 9);
        gameSession.move("second", 6);
        assertEquals(new StateHttpEntity(new int[]{1, 1, 0, 2, 2, 2, 0, 0, 1}
                , true, "second"), gameSession.getGameState());
    }


    @Test
    void getPlayer1() {
        assertEquals("first", gameSession.getPlayer1());
    }

    @Test
    void getPlayer2() throws GameException {
        gameSession.connect("second");
        assertEquals("second", gameSession.getPlayer2());
    }

    @Test
    void getCounterBeforeTurn() {
        assertEquals(0, gameSession.getCounter());
    }

    @Test
    void getCounterAfterTurn() throws GameException {
        gameSession.move("first", 1);
        assertEquals(1, gameSession.getCounter());
    }

    @Test
    void getCounterAfterTwoTurns() throws GameException {
        gameSession.connect("second");
        gameSession.move("first", 1);
        gameSession.move("second", 2);
        assertEquals(2, gameSession.getCounter());
    }
}