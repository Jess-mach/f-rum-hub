package com.forum.hub.controller;


import com.forum.hub.model.resposta.DadosCadastroResposta;
import com.forum.hub.model.resposta.DadosDetalhamentoResposta;
import com.forum.hub.model.resposta.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoResposta> responder(@RequestBody @Valid DadosCadastroResposta dados,
                                                               @AuthenticationPrincipal UserDetails userDetails) {

        DadosDetalhamentoResposta resposta = respostaService.responder(dados, userDetails);

        return ResponseEntity.created(URI.create("/respostas/" + resposta.id()))
                .body(resposta);
    }

//    @PutMapping("/{id}/solucao")
//    @Transactional
//    public ResponseEntity<?> marcarComoSolucao(@PathVariable Long id,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        Optional<Resposta> resposta = respostaRepository.findById(id);
//
//        if (resposta.isEmpty()) return ResponseEntity.notFound().build();
//
//        Resposta r = resposta.get();
//        Usuario usuario = usuarioRepository.findByLogin(userDetails.getUsername()).orElseThrow();
//
//        if (!usuario.equals(r.getTopico().getAutor()) && !usuario.getPapel().equals("PROFESSOR")) {
//            return ResponseEntity.status(403).body("Somente o autor ou um professor pode marcar como solução.");
//        }
//
//        r.setSolucao(true);
//        r.getTopico().setResolvido(true);
//
//        return ResponseEntity.ok().build();
//    }
}
