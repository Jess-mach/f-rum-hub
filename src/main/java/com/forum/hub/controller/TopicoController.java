package com.forum.hub.controller;

import com.forum.hub.dto.TopicoCreateDTO;
import com.forum.hub.dto.TopicoResponseDTO;
import com.forum.hub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> cadastrar(@RequestBody @Valid TopicoCreateDTO request) {
        TopicoResponseDTO response = service.cadastrar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String curso,
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(pageable, curso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> detalhar(@PathVariable Long id) {
        return service.detalhar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoCreateDTO request) {
        return service.atualizar(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return service.excluir(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
