package com.forum.hub.model.topico;

import jakarta.validation.constraints.NotBlank;

public record TopicoCreateDTO(
        @NotBlank(message = "Título  é obrigatório")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem,

        @NotBlank(message = "Autor é obrigatório")
        String autor,

        @NotBlank(message = "Curso é obrigatório")
        String curso
) {
}
