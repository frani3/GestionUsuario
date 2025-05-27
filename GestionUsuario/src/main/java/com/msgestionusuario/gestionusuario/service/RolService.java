package com.msgestionusuario.gestionusuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.repository.RolRepository;

@Service
public class RolService {
    
    @Autowired
    private RolRepository rolRepository;

    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol findxIdRol(int idRol) {
        return rolRepository.findById(idRol);
    }


    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    public Rol editRol(Integer idRol, Rol rol) {
        Optional<Rol> rolExistente = rolRepository.findById(idRol);
        if (rolExistente.isPresent()) {
            Rol rolActualizado = rolExistente.get();
            rolActualizado.setNombreRol(rol.getNombreRol());
            rolActualizado.setFuncion(rol.getFuncion());
            return rolRepository.save(rolActualizado);
        }
        return null;
    }

    public Rol eliminarRol(int idRol) {
        Rol rol = rolRepository.findById(idRol);
        if (rol != null) {
            rolRepository.deleteById(idRol);
            return rol;
        }
        return null;
    }

}

