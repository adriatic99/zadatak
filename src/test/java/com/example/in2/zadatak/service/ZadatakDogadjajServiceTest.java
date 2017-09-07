package com.example.in2.zadatak.service;

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
import service.DogadjajService;
import service.GradService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZadatakDogadjajServiceTest {

	@Autowired
	private DogadjajService dogadjajService;
	@Autowired
	private GradService gradService;
	Dogadjaj d1, d2, d3, d4;
	DogadjajQuery dq;
	
	@Before
	public void testData()
	{
		//test data
		String naziv = "naziv 1";
		LocalDateTime odVrijeme = LocalDateTime.of(2017, 2, 3, 10, 30);
		LocalDateTime doVrijeme = LocalDateTime.of(2017, 2, 4, 12, 30);
		boolean slobodanUlaz = true;
		Grad grad = this.gradService.findDogadjajById(3);
		d1 = new Dogadjaj();
		d1.setNaziv(naziv);
		d1.setOdVrijeme(odVrijeme);
		d1.setDoVrijeme(doVrijeme);
		d1.setSlobodanUlaz(slobodanUlaz);
		d1.setGrad(grad);
		
		naziv = "naziv 2";
		odVrijeme = LocalDateTime.of(2016, 2, 3, 10, 30);
		doVrijeme = LocalDateTime.of(2016, 2, 4, 12, 30);
		slobodanUlaz = true;
		grad = this.gradService.findDogadjajById(5);
		d2 = new Dogadjaj();
		d2.setNaziv(naziv);
		d2.setOdVrijeme(odVrijeme);
		d2.setDoVrijeme(doVrijeme);
		d2.setSlobodanUlaz(slobodanUlaz);
		d2.setGrad(grad);
		
		naziv = "naziv 3";
		odVrijeme = LocalDateTime.of(2015, 2, 3, 10, 30);
		doVrijeme = LocalDateTime.of(2015, 2, 4, 12, 30);
		slobodanUlaz = false;
		grad = this.gradService.findDogadjajById(7);
		d3 = new Dogadjaj();
		d3.setNaziv(naziv);
		d3.setOdVrijeme(odVrijeme);
		d3.setDoVrijeme(doVrijeme);
		d3.setSlobodanUlaz(slobodanUlaz);
		d3.setGrad(grad);
		
		naziv = "naziv 4";
		odVrijeme = LocalDateTime.of(2014, 2, 3, 10, 30);
		doVrijeme = LocalDateTime.of(2014, 2, 4, 12, 30);
		slobodanUlaz = true;
		grad = this.gradService.findDogadjajById(10);
		d4 = new Dogadjaj();
		d4.setNaziv(naziv);
		d4.setOdVrijeme(odVrijeme);
		d4.setDoVrijeme(doVrijeme);
		d4.setSlobodanUlaz(slobodanUlaz);
		d4.setGrad(grad);
		
		naziv = "Dodjela nagrade";
		LocalDateTime odVrijemePocetak = LocalDateTime.of(2017, 10, 10, 13, 30);
        LocalDateTime odVrijemeKraj = LocalDateTime.of(2017, 10, 10, 14, 20);
        LocalDateTime doVrijemePocetak = LocalDateTime.of(2017, 10, 10, 14, 30);
        LocalDateTime doVrijemeKraj = LocalDateTime.of(2017, 10, 10, 16, 20);
		slobodanUlaz = false;
		List<Grad> gradovi = new ArrayList<Grad>();
		gradovi.add(this.gradService.findDogadjajById(3));
		gradovi.add(this.gradService.findDogadjajById(4));
		gradovi.add(this.gradService.findDogadjajById(12));
		dq = new DogadjajQuery();
		dq.setNaziv(naziv);
		dq.setOdVrijemePocetak(odVrijemePocetak);
		dq.setOdVrijemeKraj(odVrijemeKraj);
		dq.setDoVrijemePocetak(doVrijemePocetak);
		dq.setDoVrijemeKraj(doVrijemeKraj);
		dq.setSlobodanUlaz(slobodanUlaz);
		dq.setGradovi(gradovi);
	}
	
	@Test
	public void testDogadjajCriteria()
	{   
        List<Dogadjaj> list = this.dogadjajService.findByCriteria(dq);
        assertEquals(list.size(),1);
        Dogadjaj foundDogadjaj = list.get(0);
        
        assertEquals(foundDogadjaj.getNaziv(), dq.getNaziv());
        assertEquals(foundDogadjaj.isSlobodanUlaz(), dq.isSlobodanUlaz());
        
        dq.setGradovi(null);
        list = this.dogadjajService.findByCriteria(dq);
        assertEquals(list.size(),1);
	}

}
