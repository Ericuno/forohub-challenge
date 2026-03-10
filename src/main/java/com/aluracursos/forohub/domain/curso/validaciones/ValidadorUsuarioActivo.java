package com.aluracursos.forohub.domain.curso.validaciones;

import com.aluracursos.forohub.domain.ValidacionException;
import com.aluracursos.forohub.domain.topico.DatosRegistrarTopico;
import com.aluracursos.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuarioActivo implements ValidadorInterface {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DatosRegistrarTopico datos) {
        var usuarioOptional = usuarioRepository.findById(datos.idAutor());

        if (usuarioOptional.isEmpty()) {
            throw new ValidacionException("No existe un usuario con el id '" + datos.idAutor() + "'.");
        }

        if (!usuarioOptional.get().getActivo()) {
            throw new ValidacionException("No se puede registrar tópico de usuario inactivo.");
        }
    }
}
