package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponseDTO(
        String message,
        boolean success,
        String token,
        UsuarioDTO usuarioDTO
) {}