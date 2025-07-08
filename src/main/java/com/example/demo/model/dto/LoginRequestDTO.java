package com.example.demo.model.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(String email, Boolean senha) {

    @NotNull
    public String getEmail() {
        return email;
    }
    @NotNull
    public Boolean getSenha() {
        return senha;
    }
}
