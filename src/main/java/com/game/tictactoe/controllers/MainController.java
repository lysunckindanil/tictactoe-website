package com.game.tictactoe.controllers;

import com.game.tictactoe.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping
    public String index(Model model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("user", new User());
        model.addAttribute("cells", new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        return "game/index";
    }
}
