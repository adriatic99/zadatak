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
import repository.VelicinaGradaDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZadatakVelicinaGradaRepositoryTest {

	@Autowired
	private VelicinaGradaDAO velicinaGradaDAO;
	
	@Test
	public void testVelicinaGradaDAO()
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
		Velicinagrada velicinaGradaA = new Velicinagrada(sifraA,nazivA,aktivanA);
		Velicinagrada velicinaGradaB = new Velicinagrada(sifraB,nazivB,aktivanB);
		Velicinagrada velicinaGradaC = new Velicinagrada(sifraC,nazivC,aktivanC);
		
		Velicinagrada savedVelicinaGradaA = velicinaGradaDAO.save(velicinaGradaA);
		Velicinagrada savedVelicinaGradaB = velicinaGradaDAO.save(velicinaGradaB);
		Velicinagrada savedVelicinaGradaC = velicinaGradaDAO.save(velicinaGradaC);
		
		assertNotNull(savedVelicinaGradaA);
		assertNotNull(savedVelicinaGradaB);
		assertNotNull(savedVelicinaGradaC);
		
		//find all records
		List<Velicinagrada> list = velicinaGradaDAO.findAll();
		assertTrue(list.size() > 0);
		
		//find exact record
		Velicinagrada foundVelicinaGrada = velicinaGradaDAO.findOne(savedVelicinaGradaA.getId());
		assertEquals(foundVelicinaGrada.getId(), savedVelicinaGradaA.getId());
		
		//delete record
		velicinaGradaDAO.delete(savedVelicinaGradaA);
		velicinaGradaDAO.delete(savedVelicinaGradaB);
		velicinaGradaDAO.delete(savedVelicinaGradaC);
	}
}
