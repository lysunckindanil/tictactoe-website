package com.game.tictactoe.util;

import com.game.tictactoe.entities.User;
import com.game.tictactoe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@SuppressWarnings("NullableProblems")
@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null)
            errors.rejectValue("username", "", "User with this username already exists");
    }
}
