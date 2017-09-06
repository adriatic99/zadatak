package com.example.in2.zadatak.repository;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import models.Dogadjaj;
import models.Grad;
import models.Tipoj;
import repository.DogadjajCriteriaDAO;
import repository.DogadjajDAO;
import repository.GradDAO;
import repository.TipojDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZadatakDogadjajRepositoryTest {

	@Autowired
	private DogadjajDAO dogadjajDAO;
	@Autowired
	private DogadjajCriteriaDAO dogadjajCriteriaDAO;
	@Autowired
	private GradDAO gradDAO;
	private List<Grad> listGradovi;
	
	@Before
	public void listTipoj()
	{
		this.listGradovi = this.gradDAO.findAll();
	}
	
	@Test
	public void testDogadjajDAO()
	{
		//test data
		String naziv = "naziv";
        LocalDateTime odVrijeme = LocalDateTime.of(2017, 2, 3, 10, 30);
        LocalDateTime doVrijeme = LocalDateTime.of(2017, 2, 4, 12, 30);
		boolean slobodanUlaz = true;
		Grad grad = null;
		if(!this.listGradovi.isEmpty())
			grad = this.listGradovi.get(0);
		Dogadjaj dogadjaj = new Dogadjaj();
		dogadjaj.setGrad(grad);
		dogadjaj.setDoVrijeme(doVrijeme);
		dogadjaj.setOdVrijeme(odVrijeme);
		dogadjaj.setNaziv(naziv);
		dogadjaj.setSlobodanUlaz(slobodanUlaz);
		
		//save new records
		Dogadjaj savedDogadjaj = this.dogadjajDAO.save(dogadjaj);
	
		//find all records
		List<Dogadjaj> list = this.dogadjajDAO.findAll();
		assertTrue(list.size() > 0);
		
		//find exact record
		Dogadjaj foundDogadjaj = this.dogadjajDAO.findOne(savedDogadjaj.getId());
		assertEquals(foundDogadjaj.getId(), savedDogadjaj.getId());
		assertEquals(foundDogadjaj.getNaziv(), savedDogadjaj.getNaziv());
		assertEquals(foundDogadjaj.getDoVrijeme(), savedDogadjaj.getDoVrijeme());
		assertEquals(foundDogadjaj.getOdVrijeme(), savedDogadjaj.getOdVrijeme());
		assertEquals(foundDogadjaj.isSlobodanUlaz(), savedDogadjaj.isSlobodanUlaz());
		
		//delete record
		this.dogadjajDAO.delete(foundDogadjaj);
	}
	
	@Test
	public void testDogadjajCriteria()
	{
		//test data
		String naziv = "naziv";
        LocalDateTime odVrijeme = LocalDateTime.of(2017, 2, 3, 10, 30);
        LocalDateTime doVrijeme = LocalDateTime.of(2017, 2, 4, 12, 30);
        boolean slobodanUlaz = true;
        Grad grad = null;
        if(!this.listGradovi.isEmpty())
        	grad = this.listGradovi.get(0);
        Dogadjaj dogadjaj = new Dogadjaj();
        dogadjaj.setGrad(grad);
        dogadjaj.setDoVrijeme(doVrijeme);
        dogadjaj.setOdVrijeme(odVrijeme);
        dogadjaj.setNaziv(naziv);
        dogadjaj.setSlobodanUlaz(slobodanUlaz);
				
        //save new records
        Dogadjaj savedDogadjaj = this.dogadjajDAO.save(dogadjaj);
        
        //criteria
        Dogadjaj d = new Dogadjaj();
        d.setNaziv(naziv);
        d.setGrad(grad);
        
        List<Dogadjaj> list = this.dogadjajCriteriaDAO.getEvents(d);
        System.out.println("test");
        
        this.dogadjajDAO.delete(d);
	}

}
