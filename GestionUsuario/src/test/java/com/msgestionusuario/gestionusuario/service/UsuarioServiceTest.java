package com.msgestionusuario.gestionusuario.service;

import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;
import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Rol rol;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rol = new Rol(1, Roles.Profesor, "Imparte clases", new ArrayList<>());
    }

    @Test
    void testCrearUsuario() {
        Usuario nuevo = new Usuario(null, "Ana", "López", "ana@mail.com", rol);
        Usuario guardado = new Usuario(1, "Ana", "López", "ana@mail.com", rol);

        when(usuarioRepository.save(nuevo)).thenReturn(guardado);

        Usuario resultado = usuarioService.crearUsuario(nuevo);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdUsuario()).isEqualTo(1);
        verify(usuarioRepository).save(nuevo);
    }

    @Test
    void testFindByXIdUsuario_encontrado() {
        Usuario usuario = new Usuario(1, "Pedro", "Mora", "pedro@mail.com", rol);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findByXIdUsuario(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Pedro");
        verify(usuarioRepository).findById(1);
    }

    @Test
    void testFindAllUsuarios() {
        List<Usuario> lista = List.of(new Usuario(1, "Luis", "Soto", "luis@mail.com", rol));
        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioService.findAllUsuarios();

        assertThat(resultado).hasSize(1);
        verify(usuarioRepository).findAll();
    }

    @Test
    void testEditUsuario_encontrado() {
        Usuario existente = new Usuario(1, "Laura", "Muñoz", "laura@mail.com", rol);
        Usuario actualizado = new Usuario(1, "Laura", "Muñoz", "laura.nueva@mail.com", rol);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(actualizado);

        Usuario resultado = usuarioService.editUsuario(1, actualizado);

        assertThat(resultado.getEmail()).isEqualTo("laura.nueva@mail.com");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario_encontrado() {
        Usuario usuario = new Usuario(1, "Carlos", "Reyes", "carlos@mail.com", rol);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> eliminado = usuarioService.eliminarUsuario(1);

        assertThat(eliminado).isPresent();
        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void testEditUsuario_noEncontrado() {
        Usuario actualizado = new Usuario(1, "Nombre", "Apellido", "mail@mail.com", rol);
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.editUsuario(1, actualizado);

        assertThat(resultado).isNull();
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testEliminarUsuario_noEncontrado() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.eliminarUsuario(1);

        assertThat(resultado).isNotPresent();
        verify(usuarioRepository, never()).deleteById(anyInt());
    }

}
