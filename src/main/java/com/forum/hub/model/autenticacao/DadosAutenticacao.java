package com.forum.hub.model.autenticacao;

public record DadosAutenticacao(String login, String senha) {

    public DadosAutenticacao {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser vazio");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
    }
}