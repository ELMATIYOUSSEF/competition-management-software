package com.aftasapi.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


public record AppUserDto(
                         Long id,
                         @NotBlank(message = "First name cannot be blank")
                         String firstName,
                         @NotBlank(message = "Last name cannot be blank")
                         String lastName,
                         @NotBlank(message = "Email cannot be blank")
                         @Email(message = "Email should be valid")
                         String email,
                         @NotBlank(message = "Password cannot be blank")
                         String password,
                         List<String> authorities) implements Serializable {

}
