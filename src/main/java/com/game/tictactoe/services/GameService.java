package com.game.tictactoe.services;

import com.game.tictactoe.game.GameSession;
import com.game.tictactoe.game.http.PlayersHttpEntity;
import com.game.tictactoe.game.http.StateHttpEntity;
import com.game.tictactoe.game.modes.GameModes;
import com.game.tictactoe.game.util.GameException;
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

    public Integer createSession(String username, int dimension) throws GameException {
        Integer target = getTarget(username);
        if (game_sessions.containsKey(target))
            throw new GameException("You should close last game before creating new one");
        game_sessions.put(target, new GameSession(username, GameModes.resolve(dimension)));
        player_target.put(username, target);
        return target;
    }

    public void connect(Integer target, String username) throws GameException {
        if (game_sessions.containsKey(target)) {
            game_sessions.get(target).connect(username);
            player_target.put(username, target);
        } else {
            throw new GameException("Session with this id doesn't exist");
        }

    }

    public void close(Integer target) throws GameException {
        if (game_sessions.containsKey(target)) {
            player_target.remove(game_sessions.get(target).getPlayer1());
            player_target.remove(game_sessions.get(target).getPlayer2());
            game_sessions.remove(target);
        } else {
            throw new GameException("Session with this id doesn't exist");
        }
    }

    public void makeMove(String player, Integer cell) throws GameException {
        game_sessions.get(getTargetByUsername(player)).move(player, cell);
    }

    public StateHttpEntity getGameState(Integer target) throws GameException {
        if (!game_sessions.containsKey(target)) throw new GameException("Session with this id doesn't exist");
        return game_sessions.get(target).getGameState();
    }

    public Integer getGameCounter(Integer target) throws GameException {
        if (!game_sessions.containsKey(target)) throw new GameException("Session with this id doesn't exist");
        return game_sessions.get(target).getGameCounter();
    }

    public Integer getDimension(Integer target) throws GameException {
        if (!game_sessions.containsKey(target)) throw new GameException("Session with this id doesn't exist");
        return game_sessions.get(target).getGame().getDimension();
    }

    public int getTarget(String username) {
        int target = Math.abs(Objects.hash(username)) % 10000;
        while (game_sessions.containsKey(target)) {
            target = target * 2 % 10000;
        }
        return target;
    }

    public PlayersHttpEntity getPlayers(Integer target) throws GameException {
        if (!game_sessions.containsKey(target)) throw new GameException("Session with this id doesn't exist");
        GameSession gameSession = game_sessions.get(target);
        return new PlayersHttpEntity(gameSession.getPlayer1(), gameSession.getPlayer2());
    }

}
