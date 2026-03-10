package com.aluracursos.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarUsuario(
        @NotBlank String login,
        @NotBlank String clave,
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotNull Boolean activo
) {}
