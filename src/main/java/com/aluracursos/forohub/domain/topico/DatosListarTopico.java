package com.aluracursos.forohub.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DatosListarTopico(
        Long id,
        String titulo,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime fechaCreacion,
        EstadoTopico estado,
        String autor,
        String curso
) {
    public DatosListarTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }
}