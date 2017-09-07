package repository;

import java.util.List;

import models.Dogadjaj;
import models.DogadjajCriteria;

public interface DogadjajCriteriaDAO {
	
	public List<Dogadjaj> getEvents(DogadjajCriteria dogadjaj);
}
