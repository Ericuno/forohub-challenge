package com.aluracursos.forohub.domain.curso.validaciones;

import com.aluracursos.forohub.domain.ValidacionException;
import com.aluracursos.forohub.domain.topico.DatosRegistrarTopico;
import com.aluracursos.forohub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoRepetido implements ValidadorInterface {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosRegistrarTopico datos) {
        var count = topicoRepository.countByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if(count > 0) {
            throw new ValidacionException("No se puede registrar tópico duplicado, debe cambiar título o mensaje.");
        }
    }
}
