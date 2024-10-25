package com.game.tictactoe.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotNull
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 6, max = 20, message = "Username should be between 6 and 20 letters")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should be between 6 and 20 letters")
    private String password;

    @ManyToMany
    private List<Role> roles = new ArrayList<>();
}
