
package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Grad;
import models.Organizacijskajedinica;
import models.Velicinagrada;

public interface GradRepository extends JpaRepository<Grad, Integer> {
	List<Grad> findByOrganizacijskaJedinicaInAndVelicinaGrada(
			List<Organizacijskajedinica> zupanije, 
			Velicinagrada tipGrada);
	List<Grad> findByOrganizacijskaJedinicaInAndVelicinaGradaIn(
			List<Organizacijskajedinica> zupanije, 
			List<Velicinagrada> tipGradaList);
	List<Grad> findByOrganizacijskaJedinicaIn(
			List<Organizacijskajedinica> zupanije);
	List<Grad> findByVelicinaGrada(Velicinagrada tipGrada);
	List<Grad> findByVelicinaGradaIn(List<Velicinagrada> tipGradaList);
}
