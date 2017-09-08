package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import models.EnumVelicinaGradaSifra;
import models.Grad;
import models.Organizacijskajedinica;
import models.Velicinagrada;
import repository.GradRepository;
import repository.VelicinaGradaRepository;

@Service
@Transactional
public class VelicinaGradaServiceImpl implements VelicinaGradaService {

	@Autowired
	private VelicinaGradaRepository velicinaGradaRepository;
	
	@Override
	public List<Velicinagrada> findAll() {
		return this.velicinaGradaRepository.findAll();
	}

	@Override
	public List<Velicinagrada> findAktivni() {
		return this.velicinaGradaRepository.findByAktivan(true);
	}
	
	
}
