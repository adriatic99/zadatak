package repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import models.Organizacijskajedinica;

public interface ZupanijaRepository extends Repository<Organizacijskajedinica, Integer> {
	
	List<Organizacijskajedinica> findByParentIn(List<Organizacijskajedinica> regije);
}
