package com.game.tictactoe.controllers;

import com.game.tictactoe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping
    public String index(Model model, Principal principal) {
        model.addAttribute("user", userService.getUser(principal.getName()));
        return "home/home_authorized";
    }
}
