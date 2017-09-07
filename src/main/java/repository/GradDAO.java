
package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Grad;
import models.Organizacijskajedinica;
import models.Velicinagrada;

public interface GradDAO extends JpaRepository<Grad, Integer> {
	List<Grad> findByOrganizacijskaJedinicaInAndVelicinaGrada(
			List<Organizacijskajedinica> zupanije, 
			Velicinagrada tipGrada);
	List<Grad> findByOrganizacijskaJedinicaIn(
			List<Organizacijskajedinica> zupanije);
	List<Grad> findByVelicinaGrada(Velicinagrada tipGrada);
}
