package com.forum.hub.model.resposta;

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


}
