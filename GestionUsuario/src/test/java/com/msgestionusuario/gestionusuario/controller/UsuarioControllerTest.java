package com.msgestionusuario.gestionusuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;
import com.msgestionusuario.gestionusuario.model.Usuario;
import com.msgestionusuario.gestionusuario.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol(1, Roles.Profesor, "Imparte clases", new ArrayList<>());
    }

    @Test
    void testPostUsuario_creaNuevo() throws Exception {
        Usuario nuevo = new Usuario(null, "Ana", "Lopez", "ana@mail.com", rol);
        Usuario guardado = new Usuario(1, "Ana", "Lopez", "ana@mail.com", rol);

        when(usuarioService.findByXIdUsuario(null)).thenReturn(Optional.empty());
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    void testGetAllUsuarios_conDatos() throws Exception {
        List<Usuario> usuarios = List.of(new Usuario(1, "Pedro", "Torres", "pedro@mail.com", rol));
        when(usuarioService.findAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetUsuarioById_encontrado() throws Exception {
        Usuario usuario = new Usuario(1, "Sofía", "Rojas", "sofia@mail.com", rol);
        when(usuarioService.findByXIdUsuario(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("sofia@mail.com"));
    }

    @Test
    void testPutUsuario_actualizaCorrecto() throws Exception {
        Usuario actualizado = new Usuario(1, "Elena", "Vera", "elena@mail.com", rol);

        when(usuarioService.editUsuario(eq(1), any(Usuario.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("elena@mail.com"));
    }

    @Test
    void testDeleteUsuario_encontrado() throws Exception {
        Usuario usuario = new Usuario(1, "Marco", "Silva", "marco@mail.com", rol);
        when(usuarioService.eliminarUsuario(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    void testPostUsuario_yaExiste() throws Exception {
        Usuario existente = new Usuario(1, "Ana", "Lopez", "ana@mail.com", rol);
        when(usuarioService.findByXIdUsuario(1)).thenReturn(Optional.of(existente));

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existente)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAllUsuarios_listaVacia() throws Exception {
        when(usuarioService.findAllUsuarios()).thenReturn(List.of());

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetUsuarioById_noEncontrado() throws Exception {
        when(usuarioService.findByXIdUsuario(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuario/99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPutUsuario_noEncontrado() throws Exception {
        Usuario actualizado = new Usuario(1, "Ana", "Lopez", "nueva@mail.com", rol);
        when(usuarioService.editUsuario(eq(1), any(Usuario.class))).thenReturn(null);

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUsuario_noEncontrado() throws Exception {
        when(usuarioService.eliminarUsuario(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isNotFound());
    }

}
