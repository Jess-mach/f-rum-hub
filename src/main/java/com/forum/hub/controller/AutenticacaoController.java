package com.forum.hub.controller;

import com.forum.hub.model.autenticacao.DadosAutenticacao;
import com.forum.hub.model.usuario.Usuario;
import com.forum.hub.security.DadosTokenJWT;
import com.forum.hub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> autenticar(@RequestBody @Valid DadosAutenticacao dados) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                dados.login(), dados.senha());
        Authentication authentication = manager.authenticate(token);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
