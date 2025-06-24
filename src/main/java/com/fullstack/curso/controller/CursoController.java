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
            return ResponseEntity.status(201).body(resultado);
        }
        return ResponseEntity.badRequest().body("Error al crear el curso. Verifica los datos proporcionados.");
    }


    @Operation(summary = "Eliminar un curso por ID")
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<String> borrarCurso(@PathVariable int id){
        String resultado = cursoservice1.borrarCurso(id);
            if (resultado == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(resultado);
    }


    @Operation(summary = "Modificar un curso")
    @PutMapping("/cursos")
    public ResponseEntity<String> modificarCurso(@RequestBody Curso curso) {
        if (cursoservice1.modificarCurso(curso) != null) {
            return ResponseEntity.ok(cursoservice1.modificarCurso(curso));
        }
        return ResponseEntity.badRequest().body("Error al modificar el curso. Verifica los datos proporcionados.");
    }


}
