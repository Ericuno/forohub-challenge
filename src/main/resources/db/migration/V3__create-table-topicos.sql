CREATE TABLE topicos
(
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    titulo         VARCHAR(200) NOT NULL,
    mensaje        TEXT         NOT NULL,
    fecha_creacion DATETIME     NOT NULL,
    estado         VARCHAR(100) NOT NULL,
    activo         TINYINT(1) NOT NULL DEFAULT 1,
    autor_id       BIGINT       NOT NULL,
    curso_id       BIGINT       NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_topicos_autor FOREIGN KEY (autor_id) REFERENCES usuarios (id),
    CONSTRAINT fk_topicos_curso FOREIGN KEY (curso_id) REFERENCES cursos (id)
);
