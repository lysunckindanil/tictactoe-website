package com.game.tictactoe.game;

import com.game.tictactoe.game.util.exceptions.GameException;
import com.game.tictactoe.game.util.http.StateHttpEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GameSession {
    private final TicTacToeGame game;
    private final String player1;
    private String player2;
    private int counter;

    public GameSession(String player1, TicTacToeGame game) {
        this.player1 = player1;
        this.game = game;
    }


    public void connect(String player2) throws GameException {
        if (player1.equals(player2)) {
            throw new GameException("You can't connect to the game you've created yourself");
        } else if (this.player2 != null) {
            throw new GameException("You can't connect to the game");
        } else {
            this.player2 = player2;
        }
    }


    public void move(String player, int cell) throws GameException {
        counter++;
        if (player.equals(player1)) {
            game.first(cell);
        } else if (player.equals(player2)) {
            game.second(cell);
        } else {
            throw new GameException("The player doesn't exist");
        }

    }

    public StateHttpEntity getGameState() {
        String winner = null;
        if (game.isOver()) {
            if (game.getWinner() == 1) winner = player1;
            if (game.getWinner() == 2) winner = player2;
        }
        return new StateHttpEntity(game.getCells(), game.isOver(), winner);
    }

}
