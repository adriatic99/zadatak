package service;

import java.util.List;

import models.Grad;
import models.Organizacijskajedinica;
import models.Velicinagrada;

public interface GradService {
	
	public void save(Grad grad);
	public void delete(Grad grad);
	public Grad findDogadjajById(int grad);
	public List<Grad> findAll();
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
	List<Grad> findMaleGradove();
	List<Grad> findSrednjeGradove();
	List<Grad> findVelikeGradove();
}
