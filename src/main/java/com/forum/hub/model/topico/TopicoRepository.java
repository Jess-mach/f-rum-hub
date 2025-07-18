package com.forum.hub.model.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    @Query("SELECT t FROM Topico t WHERE " +
            "(:curso IS NULL OR t.curso = :curso) AND " +
            "(:ano IS NULL OR FUNCTION('YEAR', t.dataCriacao) = :ano)")
    Page<Topico> buscarPorCursoEAno(@Param("curso") String curso, @Param("ano") Integer ano, Pageable pageable);

    Page<Topico> findByCursoContainingIgnoreCase(String curso, Pageable pageable);
}

