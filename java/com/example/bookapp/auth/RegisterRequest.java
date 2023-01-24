package com.example.bookapp.auth;

import com.example.bookapp.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "firstName should not be blank")
    private String firstName;

    @NotBlank(message = "lastName should not be blank")
    private String lastName;

    @NotBlank(message = "email should not be blank")
    @Email(message = "invalid email address")
    private String email;

    @NotBlank(message = "password should not be blank")
    private String password;

    @NotNull(message = "role should not be null")
    private Role role;
}
