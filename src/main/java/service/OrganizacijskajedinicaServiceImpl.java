package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import models.EnumTipojSifra;
import models.Organizacijskajedinica;
import models.Tipoj;
import repository.OrganizacijskaJedinicaRepository;
import repository.TipojRepository;

@Service
@Transactional
public class OrganizacijskajedinicaServiceImpl implements OrganizacijskajedinicaService {
 
	@Autowired
	private OrganizacijskaJedinicaRepository organizacijskajedinicaRepository;
	@Autowired
	private TipojRepository tipojRepository;
	
	@Override
	public void save(Organizacijskajedinica organizacijskaJedinica) {
		this.organizacijskajedinicaRepository.save(organizacijskaJedinica);
	}

	@Override
	public void delete(Organizacijskajedinica organizacijskaJedinica) {
		this.organizacijskajedinicaRepository.delete(organizacijskaJedinica);
	}

	@Override
	public Organizacijskajedinica findById(int id) {
		return this.organizacijskajedinicaRepository.findOne(id);
	}

	@Override
	public List<Organizacijskajedinica> findAll() {
		return this.organizacijskajedinicaRepository.findAll();
	}

	@Override
	public List<Organizacijskajedinica> findByParentIn(List<Organizacijskajedinica> regije) {
		if(regije == null || regije.isEmpty())
			return this.getZupanije();
		else
			return this.organizacijskajedinicaRepository.findByParentIn(regije);
	}

	@Override
	public List<Organizacijskajedinica> getRegije() {
		Tipoj tipoj = this.tipojRepository.findOne(EnumTipojSifra.REGIJA.getValue());
		return this.organizacijskajedinicaRepository.findByTipoj(tipoj);
	}

	@Override
	public List<Organizacijskajedinica> getZupanije() {
		Tipoj tipoj = this.tipojRepository.findOne(EnumTipojSifra.ZUPANIJA.getValue());
		return this.organizacijskajedinicaRepository.findByTipoj(tipoj);
	}

}
