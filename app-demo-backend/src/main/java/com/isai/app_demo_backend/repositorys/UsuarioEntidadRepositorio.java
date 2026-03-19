package com.isai.app_demo_backend.repositorys;

import com.isai.app_demo_backend.entitys.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioEntidadRepositorio
    extends JpaRepository<UsuarioEntidad, Long> {
  Optional<UsuarioEntidad> findByEmail(String email);
}
