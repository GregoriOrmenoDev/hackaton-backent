package com.techstore.backend.repository;

import com.techstore.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuarioAndPassword(String usuario, String password);
    Optional<Usuario> findByUsuario(String usuario);
    boolean existsByUsuario(String usuario);
}
