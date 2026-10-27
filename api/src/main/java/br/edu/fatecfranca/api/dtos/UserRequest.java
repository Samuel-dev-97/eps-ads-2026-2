package br.edu.fatecfranca.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
   @NotBlank(message = "Full name is required")
   String fullname,

   @NotBlank(message = "Username is required")
   @Pattern(
      regexp = "^[A-Za-z0-9._-]+$",
      message = "Username must contain only letters, numbers, dot, hyphen or underscore, without accents"
   )
   String username,

   @NotBlank(message = "Email is required")
   @Email(message = "Email must be valid")
   String email,

   @NotBlank(message = "Password is required")
   @Size(min = 8, message = "Password must have at least 8 characters")
   String password,

   @NotNull(message = "Admin flag is required")
   Boolean isAdmin
) {}
