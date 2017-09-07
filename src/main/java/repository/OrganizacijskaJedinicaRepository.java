package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Organizacijskajedinica;

public interface OrganizacijskaJedinicaRepository extends JpaRepository<Organizacijskajedinica, Integer> {
	List<Organizacijskajedinica> findByParentIn(List<Organizacijskajedinica> regije);
}