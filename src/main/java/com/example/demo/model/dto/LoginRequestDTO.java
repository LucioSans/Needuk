package com.example.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(String email, Boolean senha) {

    @NotNull
    @NotBlank
    public String getEmail() {
        return email;
    }
    @NotNull
    @NotBlank
    public Boolean getSenha() {
        return senha;
    }
}
