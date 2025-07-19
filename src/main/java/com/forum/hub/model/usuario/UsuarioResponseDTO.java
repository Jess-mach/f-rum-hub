package com.forum.hub.model.usuario;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String email,
        String perfis
) {
}
