CREATE TABLE cursos
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    activo    TINYINT(1) NOT NULL DEFAULT 1,

    PRIMARY KEY (id)
);
