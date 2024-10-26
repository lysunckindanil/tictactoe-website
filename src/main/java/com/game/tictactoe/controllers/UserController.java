package com.game.tictactoe.controllers;

import com.game.tictactoe.entities.User;
import com.game.tictactoe.services.FileService;
import com.game.tictactoe.services.UserService;
import com.game.tictactoe.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final FileService fileService;

    @PostMapping("upload_pic")
    public String uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        String username = principal.getName();
        fileService.saveUserFile(file, username);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginUserForm() {
        return "security/login";
    }


    @GetMapping("/register")
    public String registerUserForm(Model model) {
        model.addAttribute("user", new User());
        return "security/register";
    }

    @PostMapping("/create")
    public String createUserPost(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "security/register";
        }
        userService.createUser(user);
        return "redirect:/";
    }
}
