package com.game.tictactoe.game;

import com.game.tictactoe.game.http.GameStateHttpEntity;
import com.game.tictactoe.game.util.GameException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class GameSession {
    TicTacToeGame game;
    String player1;
    String player2;
    int counter = 0;

    public GameSession(String player1, TicTacToeGame game) {
        this.player1 = player1;
        this.game = game;
    }


    public void connect(String player2) throws GameException {
        if (player1.equals(player2)) {
            throw new GameException("You can't connect to the game you've created yourself");
        }
        this.player2 = player2;
    }


    public void move(String player, int cell) throws GameException {
        counter++;
        if (player.equals(player1)) {
            game.first(cell);
        } else {
            game.second(cell);
        }
    }

    public GameStateHttpEntity getGameState() {
        String winner = null;
        if (game.getWinner() != null) {
            if (game.getWinner() == 1) winner = player1;
            if (game.getWinner() == 2) winner = player2;
        }
        return new GameStateHttpEntity(game.getCells(), game.isOver(), winner);
    }

    public Integer getGameCounter() {
        return counter;
    }


}
