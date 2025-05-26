package com.msgestionusuario.gestionusuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msgestionusuario.gestionusuario.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    Rol save(Rol rol);
    Rol findById(int idRol);
    List<Rol> findAll();
    Rol deleteById(int idRol);

    

    
}
