package com.forum.hub.model.resposta;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        String autor,
        LocalDateTime dataCriacao,
        boolean solucao
) {
    public DadosDetalhamentoResposta(Resposta resposta) {
        this
           (resposta.getId(),
            resposta.getMensagem(),
            resposta.getAutor().getUsername(),
            resposta.getDataCriacao(),
            resposta.isSolucao()
           );
    }
}
