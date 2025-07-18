

ALTER TABLE topico ADD COLUMN autor_id bigint NOT NULL default 1;

ALTER TABLE topico
ADD CONSTRAINT fk_topico_autor_id
FOREIGN KEY (autor_id)
REFERENCES usuario(id);
