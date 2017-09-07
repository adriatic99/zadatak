package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Velicinagrada;

public interface VelicinaGradaRepository extends JpaRepository<Velicinagrada, Integer> {
	
	public List<Velicinagrada> findByAktivan(Boolean aktivan);
}
