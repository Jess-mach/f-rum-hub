package com.forum.hub.controller;

import com.forum.hub.model.topico.TopicoCreateDTO;
import com.forum.hub.model.topico.TopicoResponseDTO;
import com.forum.hub.model.topico.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> cadastrar(
            @RequestBody @Valid TopicoCreateDTO request,
                                                       @AuthenticationPrincipal UserDetails userDetails
    ) {
        TopicoResponseDTO response = service.cadastrar(request, userDetails);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String curso,
            @PageableDefault(size = 20, sort = "dataCriacao") Pageable pageable
    ) {
        Page<TopicoResponseDTO> listar = service.listar(pageable, curso);
        return ResponseEntity.ok(listar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> detalhar(@PathVariable Long id) {
        return service.detalhar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoCreateDTO request) {
        TopicoResponseDTO atualizar = service.atualizar(id, request);
        return ResponseEntity.ok(atualizar);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
