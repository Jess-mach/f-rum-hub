package com.forum.hub.model.resposta;

import com.forum.hub.exception.UserUnauthorizedException;
import com.forum.hub.model.topico.Topico;
import com.forum.hub.model.topico.TopicoRepository;
import com.forum.hub.model.usuario.Usuario;
import com.forum.hub.model.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.forum.hub.model.topico.StatusTopico.SOLUCIONADO;

@Service
public class RespostaService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    public DadosDetalhamentoResposta responder(@Valid DadosCadastroResposta dados, UserDetails userDetails) {

        Usuario autor = Optional.of((Usuario) userDetails)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Topico topico = topicoRepository.findById(dados.idTopico())
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        Resposta resposta = new Resposta(dados.mensagem(), topico, autor);

        Resposta save = respostaRepository.save(resposta);

        return new DadosDetalhamentoResposta(
                save.getId(),
                save.getMensagem(),
                save.getAutor().getUsername(),
                save.getDataCriacao(),
                save.isSolucao()
        );
    }


    public void solucao (Long id, UserDetails userDetails) {

        Resposta resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada"));

        Usuario usuario = Optional.of((Usuario) userDetails)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!usuario.equals(resposta.getTopico().getAutor()) && !usuario.getPerfis().equals("INSTRUTOR")) {

            throw new UserUnauthorizedException("Somente o autor ou um professor pode marcar como solução.");
        }

        resposta.setSolucao(true);
        resposta.getTopico().setStatus(SOLUCIONADO);

        respostaRepository.save(resposta);

    }
}
