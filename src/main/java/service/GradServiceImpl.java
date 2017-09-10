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
public class GradServiceImpl implements GradService {

	@Autowired
	private GradRepository gradRepository;
	@Autowired
	private VelicinaGradaRepository velicinaGradaRepository;
	
	@Override
	public void save(Grad grad) {
		this.gradRepository.save(grad);
	}

	@Override
	public void delete(Grad grad) {
		this.gradRepository.delete(grad);
	}

	@Override
	public Grad findDogadjajById(int grad) {
		return this.gradRepository.findOne(grad);
	}

	@Override
	public List<Grad> findAll() {
		return this.gradRepository.findAll();
	}

	@Override
	public List<Grad> findByOrganizacijskaJedinicaInAndVelicinaGrada(List<Organizacijskajedinica> zupanije,
			Velicinagrada tipGrada) {
		return this.findByOrganizacijskaJedinicaInAndVelicinaGrada(zupanije, tipGrada);
	}

	@Override
	public List<Grad> findByOrganizacijskaJedinicaIn(List<Organizacijskajedinica> zupanije) {
		return this.gradRepository.findByOrganizacijskaJedinicaIn(zupanije);
	}

	@Override
	public List<Grad> findByVelicinaGrada(Velicinagrada tipGrada) {
		return this.gradRepository.findByVelicinaGrada(tipGrada);
	}

	@Override
	public List<Grad> findMaleGradove() {
		Velicinagrada tip = this.velicinaGradaRepository.findOne(EnumVelicinaGradaSifra.MALI.ordinal());
		return this.gradRepository.findByVelicinaGrada(tip);
	}

	@Override
	public List<Grad> findSrednjeGradove() {
		Velicinagrada tip = this.velicinaGradaRepository.findOne(EnumVelicinaGradaSifra.SREDNJI.ordinal());
		return this.gradRepository.findByVelicinaGrada(tip);
	}

	@Override
	public List<Grad> findVelikeGradove() {
		Velicinagrada tip = this.velicinaGradaRepository.findOne(EnumVelicinaGradaSifra.VELIKI.ordinal());
		return this.gradRepository.findByVelicinaGrada(tip);
	}

	@Override
	public List<Grad> findByVelicinaGradaIn(List<Velicinagrada> tipGradaList) {
		return this.gradRepository.findByVelicinaGradaIn(tipGradaList);
	}

	@Override
	public List<Grad> findByOrganizacijskaJedinicaInAndVelicinaGradaIn(List<Organizacijskajedinica> zupanije,
			List<Velicinagrada> tipGradaList) {
		return this.gradRepository.findByOrganizacijskaJedinicaInAndVelicinaGradaIn(zupanije, tipGradaList);
	}
}
