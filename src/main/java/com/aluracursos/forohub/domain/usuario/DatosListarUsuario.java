package com.aluracursos.forohub.domain.usuario;

public record DatosListarUsuario(Long id, String nombre, String email) {
    public DatosListarUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}