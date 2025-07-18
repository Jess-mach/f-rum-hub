package com.forum.hub.model.resposta;

import com.forum.hub.model.topico.Topico;
import com.forum.hub.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private boolean solucao = false;

    @ManyToOne
    private Topico topico;

    @ManyToOne
    private Usuario autor;

    public Resposta(@NotBlank String mensagem, Topico topico, Usuario autor) {
        this.mensagem = mensagem;
        this.topico = topico;
        this.autor = autor;
    }
}