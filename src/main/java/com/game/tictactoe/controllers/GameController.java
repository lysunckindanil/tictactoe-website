package com.game.tictactoe.controllers;

import com.game.tictactoe.game.http.*;
import com.game.tictactoe.game.util.GameException;
import com.game.tictactoe.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/game")
@Controller
public class GameController {
    private final GameService gameService;

    @PostMapping("/create")
    @ResponseBody
    public TargetHttpEntity createSession(Principal principal, @RequestParam String dimension) throws GameException {
        return new TargetHttpEntity(gameService.createSession(principal.getName(), Integer.parseInt(dimension)));
    }


    @PostMapping("/connect")
    @ResponseBody
    public TargetHttpEntity connectSession(@RequestParam String target, Principal principal) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            gameService.connect(target_int, principal.getName());
            return new TargetHttpEntity(target_int);
        } catch (NumberFormatException e) {
            throw new GameException("Session id format is incorrect");
        }
    }


    @PostMapping("/close")
    @ResponseBody
    public void closeSession(Principal principal) throws GameException {
        gameService.close(gameService.getTargetByUsername(principal.getName()));
    }

    @GetMapping("/session")
    @ResponseBody
    public TargetHttpEntity getSession(Principal principal) {
        if (principal == null) return new TargetHttpEntity(0);
        Integer target = gameService.getTargetByUsername(principal.getName());
        if (target == null) return new TargetHttpEntity(0);
        return new TargetHttpEntity(target);
    }

    @PostMapping("/move")
    @ResponseBody
    public MoveStatusHttpEntity makeMove(@RequestParam Integer cell, Principal principal) throws GameException {
        gameService.makeMove(principal.getName(), cell);
        return new MoveStatusHttpEntity("Move successful");
    }


    @GetMapping("/state")
    @ResponseBody
    public StateHttpEntity getState(@RequestParam String target) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            return gameService.getGameState(target_int);
        } catch (NumberFormatException e) {
            throw new GameException("Session id format is incorrect");
        }
    }


    @GetMapping("/counter")
    @ResponseBody
    public CounterHttpEntity getCounter(@RequestParam String target) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            return new CounterHttpEntity(gameService.getGameCounter(target_int));
        } catch (NumberFormatException e) {
            throw new GameException("Session id format is incorrect");
        }

    }


    @GetMapping("/dimension")
    @ResponseBody
    public DimensionHttpEntity getDimension(@RequestParam String target) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            return new DimensionHttpEntity(gameService.getDimension(target_int));
        } catch (NumberFormatException e) {
            throw new GameException("Session id format is incorrect");
        }
    }

    @GetMapping("/players")
    @ResponseBody
    public PlayersHttpEntity getPlayers(@RequestParam String target) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            return gameService.getPlayers(target_int);
        } catch (NumberFormatException e) {
            throw new GameException("Session id format is incorrect");
        }
    }

}
