package com.aluracursos.forohub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Long countByTituloAndMensaje(String titulo, String mensaje);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("""
           SELECT t FROM Topico t
           WHERE t.curso.nombre = :nombreCurso
           AND YEAR(t.fechaCreacion) = :fecha
           AND t.activo = true
           """)
    Page<Topico> findByCursoYFecha(String nombreCurso, int fecha, Pageable paginacion);

    Page<Topico> findAllByActivoTrue(Pageable paginacion);
}