package com.example.in2.zadatak.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import models.Tipoj;
import models.Velicinagrada;
import repository.TipojDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZadatakTipojRepositoryTest {

	@Autowired
	private TipojDAO tipojDAO;
	
	@Test
	public void testTipojDAO()
	{
		//test data
		int sifraA = 1000;
		int sifraB = 1400;
		int sifraC = 2300;
		
		String nazivA = "test A";
		String nazivB = "test B";
		String nazivC = "test C";
		
		boolean aktivanA = true;
		boolean aktivanB = true;
		boolean aktivanC = false;
		
		//save new records
		Tipoj tipojA = new Tipoj();
		tipojA.setSifra(sifraA);
		tipojA.setNaziv(nazivA);
		tipojA.setAktivan(aktivanA);
		Tipoj tipojB = new Tipoj(sifraB,nazivB,aktivanB);
		Tipoj tipojC = new Tipoj(sifraC,nazivC,aktivanC);
		
		Tipoj savedTipojA = tipojDAO.save(tipojA);
		Tipoj savedTipojB = tipojDAO.save(tipojB);
		Tipoj savedTipojC = tipojDAO.save(tipojC);
	
		//find all records
		List<Tipoj> list = tipojDAO.findAll();
		assertTrue(list.size() > 0);
		
		//find exact record
		Tipoj foundTipoj = tipojDAO.findOne(savedTipojA.getId());
		assertEquals(foundTipoj.getId(), savedTipojA.getId());
		
		//delete record
		tipojDAO.delete(savedTipojA);
		tipojDAO.delete(savedTipojB);
		tipojDAO.delete(savedTipojC);
	}
	
	@Test
	public void testAktivniTipoviOrganizacije()
	{
		List<Tipoj> list = this.tipojDAO.findByAktivan(false);
		assertTrue(list.size() == 0);
		
		list = this.tipojDAO.findByAktivan(true);
		assertTrue(list.size() == 2);
	}
}
