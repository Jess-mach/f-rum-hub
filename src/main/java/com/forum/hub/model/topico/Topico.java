package com.forum.hub.model.topico;

import com.forum.hub.model.resposta.Resposta;
import com.forum.hub.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

    @NotBlank
    private String curso;

    @ManyToOne
    private Usuario autor;

    @OneToMany(mappedBy = "topico")
    private List<Resposta> respostas = new ArrayList<>();

}