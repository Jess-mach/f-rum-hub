CREATE TABLE resposta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME NOT NULL,
    solucao BOOLEAN NOT NULL,
    topico_id BIGINT,
    autor_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_resposta_topico FOREIGN KEY (topico_id) REFERENCES topico(id),
    CONSTRAINT fk_resposta_autor FOREIGN KEY (autor_id) REFERENCES usuario(id)
);


