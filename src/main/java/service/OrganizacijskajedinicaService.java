package service;

import java.util.List;

import models.Organizacijskajedinica;

public interface OrganizacijskajedinicaService {

	void save(Organizacijskajedinica organizacijskaJedinica);
	void delete(Organizacijskajedinica organizacijskaJedinica);
	Organizacijskajedinica findById(int id);
	List<Organizacijskajedinica> findAll();
	List<Organizacijskajedinica> findByParentIn(List<Organizacijskajedinica> regije);
	List<Organizacijskajedinica> getRegije();
	List<Organizacijskajedinica> getZupanije();
}
