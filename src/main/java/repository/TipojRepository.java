package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Tipoj;

public interface TipojRepository extends JpaRepository<Tipoj, Integer> {
	
	public List<Tipoj> findByAktivan(Boolean aktivan);
}
