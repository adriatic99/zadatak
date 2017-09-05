package repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import models.Dogadjaj;
import repository.DogadjajCriteriaDAO;

@Repository
public class DogadjajCriteriaDAOImpl implements DogadjajCriteriaDAO {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public Dogadjaj getEvents(Dogadjaj dogadjaj) {
		
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Dogadjaj> query = cb.createQuery(Dogadjaj.class);
		Root<Dogadjaj> root = query.from(Dogadjaj.class);
		
		Predicate p = null;
		
		if(!dogadjaj.getNaziv().isEmpty())
		{
			cb.and(cb.equal(root.get("naziv"), dogadjaj.getNaziv()));
		}
		
		if(dogadjaj.getOdVrijeme() != null)
		{
			cb.and(cb.greaterThan(root.get("odVrijeme"), dogadjaj.getOdVrijeme()));
		}
		
		return null;
	}

}
