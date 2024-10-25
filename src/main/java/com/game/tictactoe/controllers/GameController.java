package com.game.tictactoe.controllers;

import com.game.tictactoe.game.http.GameCounterHttpEntity;
import com.game.tictactoe.game.http.GameDimensionHttpEntity;
import com.game.tictactoe.game.http.GameStateHttpEntity;
import com.game.tictactoe.game.http.GameTargetHttpEntity;
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

    @PostMapping("/move")
    @ResponseBody
    public void makeMove(@RequestParam Integer cell, Principal principal) throws GameException {
        gameService.makeMove(principal.getName(), cell);
    }

    @PostMapping("/create")
    @ResponseBody
    public GameTargetHttpEntity createSession(Principal principal, @RequestParam String dimension) throws GameException {
        return new GameTargetHttpEntity(gameService.createSession(principal.getName(), Integer.parseInt(dimension)));
    }


    @PostMapping("/connect")
    @ResponseBody
    public GameTargetHttpEntity connectSession(@RequestParam String target, Principal principal) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            gameService.connect(target_int, principal.getName());
            return new GameTargetHttpEntity(target_int);
        } catch (NumberFormatException e) {
            throw new GameException("invalid_target_format");
        }
    }

    @GetMapping("/session")
    @ResponseBody
    public GameTargetHttpEntity getSession(Principal principal) {
        if (principal == null) return new GameTargetHttpEntity(0);
        Integer target = gameService.getTargetByUsername(principal.getName());
        if (target == null) return new GameTargetHttpEntity(0);
        return new GameTargetHttpEntity(target);
    }

    @GetMapping("/state")
    @ResponseBody
    public GameStateHttpEntity getState(@RequestParam Integer target) throws GameException {
        return gameService.getGameState(target);
    }


    @GetMapping("/counter")
    @ResponseBody
    public GameCounterHttpEntity getCounter(@RequestParam String target) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            return new GameCounterHttpEntity(gameService.getGameCounter(target_int));
        } catch (NumberFormatException e) {
            throw new GameException("invalid_target_format");
        }

    }


    @GetMapping("/dimension")
    @ResponseBody
    public GameDimensionHttpEntity getDimension(@RequestParam String target) throws GameException {
        try {
            Integer target_int = Integer.parseInt(target);
            return new GameDimensionHttpEntity(gameService.getDimension(target_int));
        } catch (NumberFormatException e) {
            throw new GameException("invalid_target_format");
        }
    }


    @PostMapping("/close")
    @ResponseBody
    public void closeSession(Principal principal) throws GameException {
        gameService.close(gameService.getTargetByUsername(principal.getName()));
    }


}
