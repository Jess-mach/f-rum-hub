package com.forum.hub.model.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map( usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getUsername(),
                        usuario.getPerfis().name()))
                .toList();
    }

    public UsuarioResponseDTO save(UsuarioCreateDTO usuario) {
        Usuario newUsuario = new Usuario(
                null,
                usuario.nome(),
                usuario.email(),
                usuario.senha(),//TODO BCRIPT
                usuario.perfis().equals("INSTRUTOR") ? Perfis.INSTRUTOR : Perfis.ALUNO
        );

        newUsuario = usuarioRepository.save(newUsuario);

        return new UsuarioResponseDTO(
                newUsuario.getId(),
                newUsuario.getNome(),
                newUsuario.getUsername(),
                newUsuario.getPerfis().name()
        );
    }

    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDTO update(UsuarioCreateDTO usuario, Long id) {
        Usuario existingUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existingUsuario.setNome(usuario.nome());
        existingUsuario.setLogin(usuario.email());
        existingUsuario.setPerfis(usuario.perfis().equals("INSTRUTOR") ? Perfis.INSTRUTOR : Perfis.ALUNO);

        usuarioRepository.save(existingUsuario);

        return new UsuarioResponseDTO(
                existingUsuario.getId(),
                existingUsuario.getNome(),
                existingUsuario.getUsername(),
                existingUsuario.getPerfis().name());
    }
}
