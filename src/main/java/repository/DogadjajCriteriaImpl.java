package repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import models.Dogadjaj;
import models.DogadjajQuery;
import models.Grad;

@Repository
public class DogadjajCriteriaImpl implements DogadjajCriteria {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public List<Dogadjaj> getEvents(DogadjajQuery dogadjaj) {
		
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Dogadjaj> query = cb.createQuery(Dogadjaj.class);
		Root<Dogadjaj> root = query.from(Dogadjaj.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(dogadjaj.getNaziv()))
		{
			Predicate p = cb.equal(root.get("naziv"), dogadjaj.getNaziv());
			predicates.add(p);
		}
		
		if(dogadjaj.getOdVrijemePocetak() != null && dogadjaj.getOdVrijemeKraj() != null)
		{
			Predicate p = cb.between(root.<LocalDateTime>get("odVrijeme"), dogadjaj.getOdVrijemePocetak(), dogadjaj.getOdVrijemeKraj());
			predicates.add(p);
		}
		
		if(dogadjaj.getDoVrijemePocetak() != null && dogadjaj.getDoVrijemeKraj() != null)
		{
			Predicate p = cb.between(root.<LocalDateTime>get("doVrijeme"), dogadjaj.getDoVrijemePocetak(), dogadjaj.getDoVrijemeKraj());
			predicates.add(p);
		}
		
		if(dogadjaj.getGradovi()!=null && !dogadjaj.getGradovi().isEmpty())
		{
			Join<Object, Object> grad = root.join("grad");
			List<Integer> ids = new ArrayList<Integer>();
			for(Grad g : dogadjaj.getGradovi())
				ids.add(g.getId());
			Predicate p = grad.get("id").in(ids);
			predicates.add(p);
		}
		
		if(dogadjaj.isSlobodanUlaz() != null)
		{
			Predicate p = cb.equal(root.get("slobodanUlaz"), dogadjaj.isSlobodanUlaz().booleanValue());
			predicates.add(p);
		}
		
		query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query q = entityManager.createQuery(query);
		List<Dogadjaj> result = q.getResultList();
		return result;
	}
}
