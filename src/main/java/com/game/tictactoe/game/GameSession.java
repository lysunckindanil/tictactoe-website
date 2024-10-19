package com.game.tictactoe.game;

import com.game.tictactoe.game.http.GameCounterHttpEntity;
import com.game.tictactoe.game.http.GameStateHttpEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class GameSession {
    TicTacToeGame game = new TicTacToeGame();
    String player1;
    String player2;
    int counter = 0;

    public GameSession(String player1) {
        this.player1 = player1;
    }


    public void connect(String player2) throws GameException {
        if (player1.equals(player2)) {
            throw new GameException("The same player connected");
        }
        this.player2 = player2;
    }


    public void move(String player, int cell) throws GameException {
        counter++;
        if (player.equals(player1)) {
            if (!game.first(cell)) throw new GameException("Invalid move");
        } else {
            if (!game.second(cell)) throw new GameException("Invalid move");
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
