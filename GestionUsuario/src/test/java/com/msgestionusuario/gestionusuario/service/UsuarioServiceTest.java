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
        rol = new Rol(0, Roles.Profesor, "Imparte clases", new ArrayList<>());
    }

    @Test
    void testCrearUsuario() {
        Usuario usuario = new Usuario(0, "Francisca", "Barrera", "Francisca@mail.com", rol);
        Usuario usuarioGuardado = new Usuario(1, "Francisca", "Barrera", "Francisca@mail.com", rol);

        when(usuarioRepository.save(usuario)).thenReturn(usuarioGuardado);

        Usuario resultado = usuarioService.crearUsuario(usuario);
        assertThat(resultado.getIdUsuario()).isEqualTo(1);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testFindByXIdUsuario() {
        Usuario usuario = new Usuario(1, "Ignacio", "Sorko", "Ignacio@mail.com", rol);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findByXIdUsuario(1);
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Ignacio");
        verify(usuarioRepository).findById(1);
    }

    @Test
    void testFindAllUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        lista.add(new Usuario(1, "Andrea", "Torres", "Andrea@mail.com", rol));

        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioService.findAllUsuarios();
        assertThat(resultado).hasSize(1);
        verify(usuarioRepository).findAll();
    }

    @Test
    void testEditUsuario() {
        Usuario existente = new Usuario(1, "Jorge", "Barrera", "Jorge@mail.com", rol);
        Usuario actualizado = new Usuario(1, "Jorge", "Barrera", "Jorge@mail.com", rol);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(actualizado);

        Usuario resultado = usuarioService.editUsuario(1, actualizado);
        assertThat(resultado.getNombre()).isEqualTo("Jorge");
        assertThat(resultado.getEmail()).isEqualTo("Jorge@mail.com");
    }

    @Test
    void testEliminarUsuario() {
        Usuario usuario = new Usuario(1, "Jorge", "Barrera", "Jorge@mail.com", rol);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> eliminado = usuarioService.eliminarUsuario(1);
        assertThat(eliminado).isPresent();
        verify(usuarioRepository).deleteById(1);
    }
}
