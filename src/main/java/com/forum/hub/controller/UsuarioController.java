package com.forum.hub.controller;

import com.forum.hub.model.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return service.findAll();
    }

    @PostMapping
    public UsuarioResponseDTO cadastrar(@RequestBody UsuarioCreateDTO usuario) {
        return service.save(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable Long id, @RequestBody UsuarioCreateDTO usuario) {

        return service.update(usuario, id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deleteById(id);
    }
}


