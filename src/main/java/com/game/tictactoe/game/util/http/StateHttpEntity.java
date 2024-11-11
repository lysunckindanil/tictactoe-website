package com.game.tictactoe.game.util.http;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StateHttpEntity {
    int[] positions;
    boolean isOver;
    String winner;
}
