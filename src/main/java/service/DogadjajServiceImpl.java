package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import models.Dogadjaj;
import models.DogadjajQuery;
import repository.DogadjajCriteria;
import repository.DogadjajRepository;

@Service
@Transactional
public class DogadjajServiceImpl implements DogadjajService {

	@Autowired
	DogadjajRepository dogadjajRepository;
	@Autowired
	DogadjajCriteria dogadjajCriteria;
	
	@Override
	public void save(Dogadjaj dogadjaj) {
		this.dogadjajRepository.save(dogadjaj);
	}

	@Override
	public void delete(Dogadjaj dogadjaj) {
		this.dogadjajRepository.delete(dogadjaj);
	}

	@Override
	public Dogadjaj findDogadjajById(int dogadjaj) {
		return this.dogadjajRepository.findOne(dogadjaj);
	}

	@Override
	public List<Dogadjaj> findAll() {
		return this.dogadjajRepository.findAll();
	}

	@Override
	public List<Dogadjaj> findByCriteria(DogadjajQuery dogadjaj) {
		return this.dogadjajCriteria.getEvents(dogadjaj);
	}

}
