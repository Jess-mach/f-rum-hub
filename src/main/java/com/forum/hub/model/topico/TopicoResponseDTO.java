package com.forum.hub.model.topico;

import com.forum.hub.model.resposta.DadosDetalhamentoResposta;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso,
        List<DadosDetalhamentoResposta>respostas

) {

}
