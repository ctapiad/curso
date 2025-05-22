package com.fullstack.curso.model;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

    private int id;
    private String nombre;
    private String nombre_docente;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String descripcion;


}
