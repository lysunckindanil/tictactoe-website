package com.game.tictactoe.controllers;

import com.game.tictactoe.game.http.GameCounterHttpEntity;
import com.game.tictactoe.game.http.GameStateHttpEntity;
import com.game.tictactoe.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public void makeMove(@RequestParam Integer cell, Principal principal) {
        gameService.makeMove(principal.getName(), cell);
    }

    @GetMapping("/state")
    @ResponseBody
    public GameStateHttpEntity getState(@RequestParam Integer target) {
        return gameService.getGameState(target);
    }


    @GetMapping("/counter")
    @ResponseBody
    public Integer getCounter(Principal principal) {
        return gameService.getGameCounter(principal.getName());
    }

    @PostMapping("/create")
    @ResponseBody
    public Integer createSession(Principal principal) {
        return gameService.createSession(principal.getName());
    }


    @PostMapping("/connect")
    @ResponseBody
    public Integer connectSession(@RequestParam Integer target, Principal principal) {
        gameService.connect(target, principal.getName());
        return target;
    }

    @GetMapping("/session")
    @ResponseBody
    public Integer getSession(Principal principal) {
        if (principal == null) return 0;
        Integer target = gameService.getTargetByUsername(principal.getName());
        if (target == null) return 0;
        return target;
    }

    @PostMapping("/close")
    @ResponseBody
    public void closeSession(Principal principal) {
        gameService.close(gameService.getTargetByUsername(principal.getName()));
    }
}
