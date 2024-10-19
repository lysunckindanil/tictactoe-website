package com.game.tictactoe.services;

import com.game.tictactoe.game.GameException;
import com.game.tictactoe.game.GameSession;
import com.game.tictactoe.game.http.GameCounterHttpEntity;
import com.game.tictactoe.game.http.GameStateHttpEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class GameService {
    private final Map<Integer, GameSession> game_sessions = new HashMap<>();
    private final Map<String, Integer> player_target = new HashMap<>();

    public Integer getTargetByUsername(String username) {
        return player_target.get(username);
    }

    public Integer createSession(String player1) {
        Integer target = Objects.hash(player1);
        game_sessions.put(target, new GameSession(player1));
        player_target.put(player1, target);
        return target;
    }

    public void connect(Integer target, String player2) {
        try {
            game_sessions.get(target).connect(player2);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
        player_target.put(player2, target);
    }

    public void close(Integer target) {
        if (game_sessions.containsKey(target)) {
            player_target.remove(game_sessions.get(target).getPlayer1());
            player_target.remove(game_sessions.get(target).getPlayer2());
            game_sessions.remove(target);
        }
    }

    public void makeMove(String player, Integer cell) {
        try {
            game_sessions.get(getTargetByUsername(player)).move(player, cell);
        } catch (GameException e) {
            log.warn(e.getMessage());
        }
    }

    public GameStateHttpEntity getGameState(Integer target) {
        return game_sessions.get(target).getGameState();
    }

    public Integer getGameCounter(String username) {
        return game_sessions.get(player_target.get(username)).getGameCounter();
    }

}
