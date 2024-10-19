package com.game.tictactoe.game.http;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JacksonXmlRootElement
@AllArgsConstructor
public class GameTargetHttpEntity {
    Integer target;
}
