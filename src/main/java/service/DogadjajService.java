package service;

import java.util.List;

import models.Dogadjaj;
import models.DogadjajQuery;

public interface DogadjajService {

	public void save(Dogadjaj dogadjaj);
	public void delete(Dogadjaj dogadjaj);
	public Dogadjaj findDogadjajById(int dogadjaj);
	public List<Dogadjaj> findAll();
	public List<Dogadjaj> findByCriteria(DogadjajQuery dogadjaj);
}
