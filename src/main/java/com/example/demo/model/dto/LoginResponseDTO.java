package com.example.demo.model.dto;

public record LoginResponseDTO(
        String message,  // Mensagem de feedback (ex: "Sucesso!", "Credenciais incorretas.")
        boolean success  // Status do login (true para sucesso, false para falha)
) {
    // Sem campos de email/senha aqui.
    // Sem anotações de validação, pois você está construindo esses valores.
}
