package com.msgestionusuario.gestionusuario.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msgestionusuario.gestionusuario.model.Rol;
import com.msgestionusuario.gestionusuario.model.Roles;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombreRol(Roles nombreRol);
}
