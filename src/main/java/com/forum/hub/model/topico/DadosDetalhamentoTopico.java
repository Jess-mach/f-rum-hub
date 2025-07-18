package com.forum.hub.model.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico
        (Long id,
         String titulo,
         String mensagem,
         LocalDateTime dataCriacao,
         String status,
         String nomeAutor,
         String nomeCurso,
         boolean resolvido // ✅ novo campo
        ) {

}
