package repository;

import java.util.List;

import models.Dogadjaj;
import models.DogadjajQuery;

public interface DogadjajCriteria {
	
	public List<Dogadjaj> getEvents(DogadjajQuery dogadjaj);
}
