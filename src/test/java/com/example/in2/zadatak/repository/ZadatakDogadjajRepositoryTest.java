package com.example.in2.zadatak.repository;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import models.Dogadjaj;
import models.DogadjajQuery;
import models.Grad;
import models.Tipoj;
import repository.DogadjajCriteria;
import repository.DogadjajRepository;
import repository.GradRepository;
import repository.TipojRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZadatakDogadjajRepositoryTest {

	@Autowired
	private DogadjajRepository dogadjajRepository;
	@Autowired
	private DogadjajCriteria dogadjajCriteria;
	@Autowired
	private GradRepository gradDAO;
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
		Dogadjaj savedDogadjaj = this.dogadjajRepository.save(dogadjaj);
	
		//find all records
		List<Dogadjaj> list = this.dogadjajRepository.findAll();
		assertTrue(list.size() > 0);
		
		//find exact record
		Dogadjaj foundDogadjaj = this.dogadjajRepository.findOne(savedDogadjaj.getId());
		assertEquals(foundDogadjaj.getId(), savedDogadjaj.getId());
		assertEquals(foundDogadjaj.getNaziv(), savedDogadjaj.getNaziv());
		assertEquals(foundDogadjaj.getDoVrijeme(), savedDogadjaj.getDoVrijeme());
		assertEquals(foundDogadjaj.getOdVrijeme(), savedDogadjaj.getOdVrijeme());
		assertEquals(foundDogadjaj.isSlobodanUlaz(), savedDogadjaj.isSlobodanUlaz());
		
		//delete record
		this.dogadjajRepository.delete(foundDogadjaj);
	}
	
	@Test
	public void testDogadjajCriteria()
	{
		//test data
		String naziv = "Dodjela nagrade";
        LocalDateTime odVrijemePocetak = LocalDateTime.of(2017, 10, 10, 13, 30);
        LocalDateTime odVrijemeKraj = LocalDateTime.of(2017, 10, 10, 14, 20);
        LocalDateTime doVrijemePocetak = LocalDateTime.of(2017, 10, 10, 14, 30);
        LocalDateTime doVrijemeKraj = LocalDateTime.of(2017, 10, 10, 16, 20);
        boolean slobodanUlaz = false;
        Grad grad = this.listGradovi.get(3);
        DogadjajQuery dogadjaj = new DogadjajQuery();
        dogadjaj.addGrad(grad);
        dogadjaj.setOdVrijemePocetak(odVrijemePocetak);
        dogadjaj.setOdVrijemeKraj(odVrijemeKraj);
        dogadjaj.setDoVrijemePocetak(doVrijemePocetak);
        dogadjaj.setDoVrijemeKraj(doVrijemeKraj);
        dogadjaj.setNaziv(naziv);
        dogadjaj.setSlobodanUlaz(slobodanUlaz);
        
        List<Dogadjaj> list = this.dogadjajCriteria.getEvents(dogadjaj);
        assertEquals(list.size(),1);
        
        Dogadjaj dogadjajCriteria = list.get(0);
        
        assertEquals(dogadjaj.getNaziv(), dogadjajCriteria.getNaziv());
        assertEquals(dogadjaj.isSlobodanUlaz(), dogadjajCriteria.isSlobodanUlaz());
        
        Grad belimanastir = this.listGradovi.get(4);
        Grad donjimiholjac = this.listGradovi.get(5);
        
        dogadjaj.addGrad(donjimiholjac);
        dogadjaj.addGrad(belimanastir);
        
        list = this.dogadjajCriteria.getEvents(dogadjaj);
        assertEquals(list.size(),1);
        
        dogadjaj.setGradovi(null);
        
        list = this.dogadjajCriteria.getEvents(dogadjaj);
        assertEquals(list.size(),1);
        
        doVrijemeKraj = LocalDateTime.of(2017, 10, 10, 15, 10);
        dogadjaj.setDoVrijemeKraj(doVrijemeKraj);
        list = this.dogadjajCriteria.getEvents(dogadjaj);
        assertEquals(list.size(),1);
        
        dogadjaj.setNaziv("naziv");
        list = this.dogadjajCriteria.getEvents(dogadjaj);
        assertEquals(list.size(),0);
        
        dogadjaj.setNaziv(naziv);
        list = this.dogadjajCriteria.getEvents(dogadjaj);
        assertEquals(list.size(),1);
        
        doVrijemeKraj = LocalDateTime.of(2017, 10, 10, 15, 9);
        dogadjaj.setDoVrijemeKraj(doVrijemeKraj);
        list = this.dogadjajCriteria.getEvents(dogadjaj);
        assertEquals(list.size(),0);
        
	}

}
