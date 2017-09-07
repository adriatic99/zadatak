package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import models.Dogadjaj;
import models.DogadjajCriteria;
import models.Grad;

@Repository
public class DogadjajCriteriaDAOImpl implements DogadjajCriteriaDAO {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public List<Dogadjaj> getEvents(DogadjajCriteria dogadjaj) {
		
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Dogadjaj> query = cb.createQuery(Dogadjaj.class);
		Root<Dogadjaj> root = query.from(Dogadjaj.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(dogadjaj.getNaziv()))
		{
			Predicate p = cb.equal(root.get("naziv"), dogadjaj.getNaziv());
			predicates.add(p);
		}
		
		if(dogadjaj.getOdVrijeme() != null)
		{
			Predicate p = cb.greaterThan(root.get("odvrijeme"), dogadjaj.getOdVrijeme());
			predicates.add(p);
		}
		
		if(dogadjaj.getDoVrijeme() != null)
		{
			Predicate p = cb.lessThan(root.get("dovrijeme"), dogadjaj.getDoVrijeme());
			predicates.add(p);
		}
		
		if(dogadjaj.getGradovi()!=null && !dogadjaj.getGradovi().isEmpty())
		{
			Join<Object, Object> grad = root.join("grad");
			List<Integer> ids = new ArrayList<Integer>();
			for(Grad g : dogadjaj.getGradovi())
				ids.add(g.getId());
			//Predicate p = cb.in(grad.get("id"), dogadjaj.getGradovi());
			Predicate p = grad.get("id").in(ids);
			predicates.add(p);
		}
		
		query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = entityManager.createQuery(query);
		List<Dogadjaj> result = q.getResultList();
		return result;
	}
}
