package com.fullstack.curso.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private String rut;
    private String nombre;
    private String apellido_paterno;
    private String correo;
    private String telefono;
    private String rol;

}
