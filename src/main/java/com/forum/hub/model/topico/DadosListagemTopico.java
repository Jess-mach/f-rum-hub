package com.forum.hub.model.topico;


import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        String nomeAutor,
        String nomeCurso,
        LocalDateTime dataCriacao,
        boolean resolvido // ✅ novo campo
) {

}