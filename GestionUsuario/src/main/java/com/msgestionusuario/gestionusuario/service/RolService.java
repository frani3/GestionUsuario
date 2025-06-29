package com.msgestionusuario.gestionusuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;
import com.msgestionusuario.gestionusuario.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol findxNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(Roles.valueOf(nombreRol));
    }

    public Rol findxIdRol(int idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }

    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    public Rol eliminarRol(int idRol) {
        Optional<Rol> rolOpt = rolRepository.findById(idRol);
        if (rolOpt.isPresent()) {
            Rol rol = rolOpt.get();
            if (rol.getUsuarios() == null || rol.getUsuarios().isEmpty()) {
                rolRepository.deleteById(idRol);
                return rol;
            }
        }
        return null;
    }

}
