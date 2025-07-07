package com.example.demo.model.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(String email, String senha) {

    @NotNull
    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
