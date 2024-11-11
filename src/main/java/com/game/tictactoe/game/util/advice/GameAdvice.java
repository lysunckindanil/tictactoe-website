package com.game.tictactoe.game.util.advice;

import com.game.tictactoe.game.util.exceptions.GameException;
import com.game.tictactoe.game.util.http.ExceptionMessageResponseHttpEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GameAdvice {

    @ExceptionHandler(GameException.class)
    public ResponseEntity<ExceptionMessageResponseHttpEntity> gameException(GameException ex) {
        ExceptionMessageResponseHttpEntity response = new ExceptionMessageResponseHttpEntity(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
