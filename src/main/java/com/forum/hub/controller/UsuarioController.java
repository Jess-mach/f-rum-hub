package com.forum.hub.controller;

import com.forum.hub.model.usuario.Usuario;
import com.forum.hub.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {

        return repository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}


