package com.fullstack.curso.model.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String nombre_docente;
    private String rut_docente;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String descripcion;

}
