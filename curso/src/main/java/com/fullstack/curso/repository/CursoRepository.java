package com.fullstack.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.curso.model.entity.CursoEntity;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Integer> {

    CursoEntity findById(int id);
    Boolean existsByNombre(String nombre);
    void deleteById(int id);
    Boolean findByNombreAndId(String nombre, int id);
    Boolean existsById(int id);
    Boolean existsByNombreAndId(String nombre, int id);
    
    


}
