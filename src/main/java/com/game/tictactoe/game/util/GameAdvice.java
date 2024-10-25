package com.game.tictactoe.game.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GameAdvice {

    @ExceptionHandler(GameException.class)
    public ResponseEntity<GameExceptionResponse> gameException(GameException ex) {
        GameExceptionResponse response = new GameExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
