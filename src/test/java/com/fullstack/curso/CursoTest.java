package com.fullstack.curso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.fullstack.curso.model.Curso;
import com.fullstack.curso.model.dto.UsuarioDto;
import com.fullstack.curso.model.entity.CursoEntity;
import com.fullstack.curso.repository.CursoRepository;
import com.fullstack.curso.service.CursoService;

public class CursoTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    private Curso curso;
    private CursoEntity cursoEntity;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        curso = new Curso(1, "Curso_de_Cristian", "Cristian", "11.222.333-4", new java.util.Date(), new java.util.Date(), "Esto es un curso");
        cursoEntity = new CursoEntity();
        cursoEntity.setId(1);
        cursoEntity.setNombre("Curso_de_Cristian");
        cursoEntity.setNombre_docente("Cristian");
        cursoEntity.setRut_docente("11.222.333-4");
        cursoEntity.setFecha_inicio(new java.util.Date());
        cursoEntity.setFecha_fin(new java.util.Date());
        cursoEntity.setDescripcion("Esto es un curso");

        UsuarioDto usuarioMock = new UsuarioDto("11.222.333-4", "Cristian", "Apellido", "micorreo@gmail.com", 911223344, "Docente");
        when(restTemplate.getForObject(anyString(), eq(UsuarioDto.class))).thenReturn(usuarioMock);
    }

    @Test
    void testCrearCurso() {
        when(cursoRepository.existsById(curso.getId())).thenReturn(false);
        when(cursoRepository.save(any(CursoEntity.class))).thenReturn(cursoEntity);

        String resultado = cursoService.crearCurso(curso);

        assertEquals("Curso creado correctamente", resultado); 
    }

    @Test
    void testTreaerCursoporId() {
        when(cursoRepository.findById(1)).thenReturn(cursoEntity);

        Curso resultado = cursoService.obtenerCurso(1);

        assertNotNull(resultado);
        assertEquals(curso.getNombre(), resultado.getNombre());
    }

    @Test
    void testTraerCursoNoExiste() {
        when(cursoRepository.findById(2)).thenReturn(null);

        Curso resultado = cursoService.obtenerCurso(2);

        assertNull(resultado);
    }

    @Test
    void testActualizarCurso_existe() {
        when(cursoRepository.existsById(curso.getId())).thenReturn(true);
        when(cursoRepository.findById(curso.getId())).thenReturn(cursoEntity);
        when(cursoRepository.save(any(CursoEntity.class))).thenReturn(cursoEntity);

        String resultado = cursoService.modificarCurso(curso);

        assertEquals("", resultado);
        verify(cursoRepository, times(1)).save(any(CursoEntity.class));
    }

    @Test
    void testBorrarCurso() {
        when(cursoRepository.existsById(curso.getId())).thenReturn(true);
        doNothing().when(cursoRepository).deleteById(curso.getId());

        String resultado = cursoService.borrarCurso(curso.getId());

        assertEquals("Curso eliminado correctamente", resultado);
        verify(cursoRepository, times(1)).deleteById(curso.getId());
    }

}
