package dao;

import model.RegistroInicioSesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroInicioSesionRepository extends JpaRepository<RegistroInicioSesion, Long> {
}
