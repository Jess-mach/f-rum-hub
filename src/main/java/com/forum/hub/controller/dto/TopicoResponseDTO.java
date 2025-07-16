package com.forum.hub.controller.dto;
import com.forum.hub.model.StatusTopico;
import com.forum.hub.model.Topico;

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
    public TopicoResponseDTO(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso()
        );
    }

}
