package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.curso.CursoRepository;
import com.aluracursos.forohub.domain.curso.validaciones.ValidadorInterface;
import com.aluracursos.forohub.domain.topico.*;
import com.aluracursos.forohub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private final TopicoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final List<ValidadorInterface> validadores;

    public TopicoController(TopicoRepository repository, UsuarioRepository usuarioRepository,
                            CursoRepository cursoRepository, List<ValidadorInterface> validadores) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.validadores = validadores;
}

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriBuilder) {

        validadores.forEach(v -> v.validar(datos));

        var autor = usuarioRepository.findByNombre(datos.autor());
        if (autor == null) {
            return ResponseEntity.badRequest().body("Error: No existe un usuario con el nombre '" + datos.autor() + "'.");
        }

        if (!usuarioRepository.existsById(datos.idAutor())) {
            return ResponseEntity.badRequest().body("Error: No existe un usuario con el id '" + datos.idAutor() + "'.");
        }

        var curso = cursoRepository.findByNombre(datos.curso());
        if (curso == null) {
            return ResponseEntity.badRequest().body("Error: No existe un curso con el nombre '" + datos.curso() + "'.");
        }

        var topico = new Topico(datos, autor, curso);
        repository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarTopico>> listarTopicos(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer fecha,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {

        Page<Topico> pagina;

        if (curso != null && fecha != null) {
            pagina = repository.findByCursoYFecha(curso, fecha, paginacion);
        } else {
            pagina = repository.findAllByActivoTrue(paginacion);
        }

        return ResponseEntity.ok(pagina.map(DatosListarTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detallar(@PathVariable Long id) {
        return repository.findById(id)
                .map(topico -> ResponseEntity.ok(new DatosDetalleTopico(topico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var topico = topicoOptional.get();

        if (!topico.getTitulo().equals(datos.titulo()) || !topico.getMensaje().equals(datos.mensaje())) {
            if (repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
                return ResponseEntity.badRequest().body("Error: Ya existe otro tópico con el mismo título y mensaje.");
            }
        }

        topico.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        var topico = repository.getReferenceById(id);
        topico.eliminar();

        return ResponseEntity.noContent().build();
    }
}