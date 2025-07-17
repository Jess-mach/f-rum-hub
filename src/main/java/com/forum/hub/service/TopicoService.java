package com.forum.hub.service;

import com.forum.hub.dto.TopicoCreateDTO;
import com.forum.hub.dto.TopicoResponseDTO;
import com.forum.hub.model.Topico;
import com.forum.hub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    public TopicoResponseDTO cadastrar(TopicoCreateDTO request) {
        if (repository.existsByTituloAndMensagem(request.titulo(), request.mensagem())) {
            throw new IllegalArgumentException("Tópico duplicado.");
        }

        Topico topico = Topico.builder()
                .titulo(request.titulo())
                .mensagem(request.mensagem())
                .autor(request.autor())
                .curso(request.curso())
                .build();

        return toResponse(repository.save(topico));
    }

    public Page<TopicoResponseDTO> listar(Pageable pageable, String curso) {
        Page<Topico> pagina = (curso == null || curso.isBlank())
                ? repository.findAll(pageable)
                : new PageImpl<>(repository.findByCursoContainingIgnoreCase(curso), pageable, 0);

        return pagina.map(this::toResponse);
    }

    public Optional<TopicoResponseDTO> detalhar(Long id) {
        return repository.findById(id).map(this::toResponse);
    }

    public Optional<TopicoResponseDTO> atualizar(Long id, TopicoCreateDTO request) {
        return repository.findById(id).map(topico -> {
            topico.setTitulo(request.titulo());
            topico.setMensagem(request.mensagem());
            topico.setAutor(request.autor());
            topico.setCurso(request.curso());
            return toResponse(repository.save(topico));
        });
    }

    public boolean excluir(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private TopicoResponseDTO toResponse(Topico topico) {
        return new TopicoResponseDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
    }
}
