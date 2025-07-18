package com.forum.hub.model.topico;

import java.time.LocalDateTime;

public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico estado,
        String autor,
        String curso

) {

}
