package com.fullstack.curso.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fullstack.curso.model.entity.CursoEntity;
import com.fullstack.curso.model.Curso;
import com.fullstack.curso.model.dto.UsuarioDto;
import com.fullstack.curso.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RestTemplate restTemplate;


    public CursoService(){

    }

    public List<Curso> getAllCursos() {
        try {
            List<CursoEntity> listaCurso = (List<CursoEntity>) cursoRepository.findAll();

            if (listaCurso.isEmpty()) {
                throw new IllegalArgumentException("No hay cursos disponibles");
            }

            List<Curso> cursos = new ArrayList<>();
            for (CursoEntity curso : listaCurso) {
                Curso nuevoCurso = new Curso();
                nuevoCurso.setId(curso.getId());
                nuevoCurso.setNombre(curso.getNombre());
                nuevoCurso.setNombre_docente(curso.getNombre_docente());
                nuevoCurso.setRut_docente(curso.getRut_docente());
                nuevoCurso.setFecha_inicio(curso.getFecha_inicio());
                nuevoCurso.setFecha_fin(curso.getFecha_fin());
                nuevoCurso.setDescripcion(curso.getDescripcion());
                cursos.add(nuevoCurso);
            }
            return cursos;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
        
    }

    public Curso obtenerCurso(int id){
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("El ID del curso no puede ser menor o igual a cero");
            }
            CursoEntity cursoEntity = cursoRepository.findById(id);

            if (cursoEntity == null) {
                throw new IllegalArgumentException("El curso con ID " + id + " no existe");
            }

            Curso curso = new Curso();
            curso.setId(cursoEntity.getId());
            curso.setNombre(cursoEntity.getNombre());
            curso.setNombre_docente(cursoEntity.getNombre_docente());
            curso.setRut_docente(cursoEntity.getRut_docente());
            curso.setFecha_inicio(cursoEntity.getFecha_inicio());
            curso.setFecha_fin(cursoEntity.getFecha_fin());
            curso.setDescripcion(cursoEntity.getDescripcion());
            return curso;

        }catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public String crearCurso(Curso curso) {
        try {
            Boolean estado = cursoRepository.existsById(curso.getId());
            if (estado != true) {
                String usuarioUrl = "http://54.88.178.51:8080/obtenerUsuario/" + curso.getRut_docente();
                UsuarioDto usuario = restTemplate.getForObject(usuarioUrl, UsuarioDto.class);
                System.out.println(usuario);
                if (usuario == null || !usuario.getRol().equals("Docente")) {
                    System.out.println("El usuario con RUT " + curso.getRut_docente() + " no existe o no es un docente, ROL: " + usuario.getRol());
                    return null;
                }

                CursoEntity cursoNuevo = new CursoEntity();
                //cursoNuevo.setId(curso.getId());
                cursoNuevo.setNombre(curso.getNombre());
                cursoNuevo.setNombre_docente(usuario.getNombre());
                cursoNuevo.setRut_docente(usuario.getRut());
                cursoNuevo.setFecha_inicio(curso.getFecha_inicio());
                cursoNuevo.setFecha_fin(curso.getFecha_fin());
                cursoNuevo.setDescripcion(curso.getDescripcion());
                cursoRepository.save(cursoNuevo);
                return "Curso creado correctamente";
            }
            else {
                System.out.println("El curso con ID " + curso.getId() + " ya existe");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public String borrarCurso(int id) {
        try {
            Boolean estado = cursoRepository.existsById(id);
            if (estado == true) {
                cursoRepository.deleteById(id);
                return "Curso eliminado correctamente";
            } else {
                System.out.println("El curso con ID especificado no existe");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public String modificarCurso(Curso curso) {
        try {
            if (cursoRepository.existsById(curso.getId())) {
                CursoEntity cursoExistente = cursoRepository.findById(curso.getId());
                if (cursoExistente != null) {
                    cursoExistente.setNombre(curso.getNombre());
                    //cursoExistente.setNombre_docente(curso.getNombre_docente());
                    cursoExistente.setFecha_inicio(curso.getFecha_inicio());
                    cursoExistente.setFecha_fin(curso.getFecha_fin());
                    cursoExistente.setDescripcion(curso.getDescripcion());
                    cursoRepository.save(cursoExistente);
                    System.out.println("Curso modificado correctamente");
                    return "";
                } else {
                    System.out.println("El curso no existe");
                    return null;
                }
            } else {
                System.out.println("El curso con ID especificado no existe");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al editar curso: " + e.getMessage());
            return null;
        }
    }

}
