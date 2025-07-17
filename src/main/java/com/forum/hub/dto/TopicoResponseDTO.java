package com.forum.hub.dto;
import com.forum.hub.model.StatusTopico;

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
