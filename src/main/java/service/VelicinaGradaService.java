package service;

import java.util.List;

import models.Velicinagrada;

public interface VelicinaGradaService {

	public List<Velicinagrada> findAll();
	public List<Velicinagrada> findAktivni();
}
