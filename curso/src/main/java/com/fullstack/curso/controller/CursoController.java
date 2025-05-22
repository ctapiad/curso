package com.fullstack.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.curso.model.Curso;
import com.fullstack.curso.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
public class CursoController {

    @Autowired
    private CursoService cursoservice1;


    @Operation(summary = "Mostrar todos los cursos")
    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> mostrarCursos() {
        List<Curso> cursos = cursoservice1.getAllCursos();
        if (cursos == null || cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cursos);
    }


    @Operation(summary = "Mostrar un curso por ID")
    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable int id){
        if (cursoservice1.obtenerCurso(id) != null) {
            return ResponseEntity.ok(cursoservice1.obtenerCurso(id));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo curso")
    @PostMapping("/cursos")
    public ResponseEntity<String> crearCurso(@RequestBody Curso curso){
        String resultado = cursoservice1.crearCurso(curso);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Eliminar un curso por ID")
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<String> borrarCurso(@PathVariable int id){
        if (cursoservice1.borrarCurso(id) != null) {
            return ResponseEntity.ok(cursoservice1.borrarCurso(id));
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Modificar un curso")
    @PutMapping("/cursos")
    public ResponseEntity<String> modificarCurso(@RequestBody Curso curso) {
        if (cursoservice1.modificarCurso(curso) != null) {
            return ResponseEntity.ok(cursoservice1.modificarCurso(curso));
        }
        return ResponseEntity.notFound().build();
    }


}
