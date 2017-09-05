package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import models.Tipoj;
import repository.TipojDAO;

@Service
@Component
public class TipojService {

	@Autowired
	TipojDAO tipDAO;
	
	public void save(Tipoj tip)
	{
		tipDAO.save(tip);
	}
}
