package com.forum.hub.controller;


import com.forum.hub.model.resposta.*;
import com.forum.hub.model.usuario.Usuario;
import com.forum.hub.model.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
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

    @PutMapping("/{id}/solucao")
    @Transactional
    public ResponseEntity<?> marcarComoSolucao(@PathVariable Long id,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        respostaService.solucao (id, userDetails);



        return ResponseEntity.ok().build();
    }
}
