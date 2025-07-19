package com.forum.hub.model.usuario;

public record UsuarioCreateDTO(

        String nome,
        String email,
        String senha,
        String perfis
) {
}
