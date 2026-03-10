package com.aluracursos.forohub.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findByNombre(String nombre);

    List<Curso> findByActivoAndNombre(boolean activo, String nombre);
}
