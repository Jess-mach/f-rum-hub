package com.forum.hub.model.resposta;

import java.time.LocalDateTime;

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
