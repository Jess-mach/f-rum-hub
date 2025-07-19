package com.forum.hub.model.topico;

import com.forum.hub.model.resposta.DadosDetalhamentoResposta;
import com.forum.hub.model.usuario.Usuario;
import com.forum.hub.model.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TopicoResponseDTO cadastrar(TopicoCreateDTO request, UserDetails userDetails) {
        Usuario autor = Optional.of((Usuario) userDetails)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (repository.existsByTituloAndMensagem(request.titulo(), request.mensagem())) {
            throw new IllegalArgumentException("Tópico duplicado.");
        }

        Topico topico = Topico.builder()
                .titulo(request.titulo())
                .mensagem(request.mensagem())
                .autor(autor)
                .curso(request.curso())
                .dataCriacao(LocalDateTime.now())
                .status(StatusTopico.NAO_RESPONDIDO)
                .build();

        return toResponse(repository.save(topico));
    }

    public Page<TopicoResponseDTO> listar(Pageable pageable, String curso) {
        Page<Topico> pagina = (curso == null || curso.isBlank())
                ? repository.findAll(pageable)
                : repository.findByCursoContainingIgnoreCase(curso, pageable);

        return pagina.map(this::toResponse);
    }

    public Optional<TopicoResponseDTO> detalhar(Long id) {
        return repository.findById(id).map(this::toResponseResposta);
    }

    public TopicoResponseDTO atualizar(Long id, TopicoCreateDTO request) {
        Optional<Topico> byId = repository.findById(id);

        if (byId.isPresent()) {
            Topico topico = byId.get();

            topico.setTitulo(request.titulo());
            topico.setMensagem(request.mensagem());
            topico.setCurso(request.curso());

            return toResponse(repository.save(topico));
        }

        throw new EntityNotFoundException("Registro não encontrado");

    }

    public void excluir(Long id) {
        Optional<Topico> byId = repository.findById(id);

        if (byId.isPresent()) {
            repository.deleteById(id);
        } else
            throw new EntityNotFoundException("Registro não encontrado");

        if(false) {
            repository.findById(id)
                    .ifPresentOrElse(r -> repository.deleteById(r.getId()),
                            () -> {
                                throw new EntityNotFoundException("Registro não encontrado");
                            });
        }
    }

    private TopicoResponseDTO toResponseResposta(Topico topico) {
        return TopicoResponseDTO.builder()
                .id(topico.getId())
                .titulo(topico.getTitulo())
                .mensagem(topico.getMensagem())
                .dataCriacao(topico.getDataCriacao())
                .status(topico.getStatus())
                .autor(topico.getAutor().getUsername())
                .curso(topico.getCurso())
                .respostas(topico.getRespostas().stream()
                        .map(resposta -> DadosDetalhamentoResposta.builder()
                                .id(resposta.getId())
                                .mensagem(resposta.getMensagem())
                                .dataCriacao(resposta.getDataCriacao())
                                .autor(resposta.getAutor().getUsername())
                                .solucao(resposta.isSolucao())
                                .build())
                        .toList())
                .build();

    }

    private TopicoResponseDTO toResponse(Topico topico) {
        return TopicoResponseDTO.builder()
                .id(topico.getId())
                .titulo(topico.getTitulo())
                .mensagem(topico.getMensagem())
                .dataCriacao(topico.getDataCriacao())
                .status(topico.getStatus())
                .autor(topico.getAutor().getUsername())
                .curso(topico.getCurso())
                .build();

    }
}
