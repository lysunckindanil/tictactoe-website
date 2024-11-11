package com.game.tictactoe.game.util.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StateHttpEntity {
    int[] positions;
    boolean isOver;
    String winner;
}
