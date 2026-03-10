package com.aluracursos.forohub.domain.curso.validaciones;

import com.aluracursos.forohub.domain.ValidacionException;
import com.aluracursos.forohub.domain.curso.Curso;
import com.aluracursos.forohub.domain.curso.CursoRepository;
import com.aluracursos.forohub.domain.topico.DatosRegistrarTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidadorCursoExistente implements ValidadorInterface {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(DatosRegistrarTopico datos) {
        List<Curso> cursos = cursoRepository.findByActivoAndNombre(true, datos.curso());
        if(cursos.isEmpty()) {
            throw new ValidacionException("No se puede registrar tópico de curso no registrado o inactivo.");
        }
    }

}
