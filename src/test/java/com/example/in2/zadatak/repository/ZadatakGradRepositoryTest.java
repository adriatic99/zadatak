package com.example.in2.zadatak.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import models.Organizacijskajedinica;
import models.Velicinagrada;
import models.Grad;
import repository.GradDAO;
import repository.OrganizacijskaJedinicaDAO;
import repository.VelicinaGradaDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZadatakGradRepositoryTest {

	@Autowired
	private GradDAO gradDAO;
	@Autowired
	private VelicinaGradaDAO velicinaGradaDAO;
	@Autowired
	private OrganizacijskaJedinicaDAO organizacijskaJedinicaDAO;
	private List<Organizacijskajedinica> listOj;
	private List<Velicinagrada> listTipGrada;
	
	@Before
	public void listTipoj()
	{
		listOj = organizacijskaJedinicaDAO.findAll();
		listTipGrada = velicinaGradaDAO.findAll();
	}
	
	@Test
	public void testOrganizacijskaJedinica()
	{
		//test data
		int sifraA = 100;
		int sifraB = 200;
		int sifraC = 400;
		
		String nazivA = "naziv A";
		String nazivB = "naziv B";
		String nazivC = "naziv C";
		String nazivChanged = "changed";
		
		Velicinagrada gradTipA = null;
		Velicinagrada gradTipB = null;
		Velicinagrada gradTipC = null;
		
		if(!listTipGrada.isEmpty())
		{
			gradTipA = listTipGrada.get(0);
			gradTipB = listTipGrada.get(0);
			gradTipC = listTipGrada.get(0);
		}
		if(listTipGrada.size() > 1)
		{
			gradTipB = listTipGrada.get(1);
			gradTipC = listTipGrada.get(1);
		}
		if(listTipGrada.size() > 2)
		{
			gradTipC = listTipGrada.get(2);
		}
		
		Organizacijskajedinica ojA = null;
		Organizacijskajedinica ojB = null;
		Organizacijskajedinica ojC = null;
		
		if(!listOj.isEmpty())
		{
			ojA = listOj.get(0);
			ojB = listOj.get(0);
			ojC = listOj.get(0);
		}
		if(listOj.size() > 1)
		{
			ojB = listOj.get(1);
			ojC = listOj.get(1);
		}
		if(listOj.size() > 2)
		{
			ojC = listOj.get(2);
		}
		
		Grad gradA = new Grad(sifraA,nazivA,gradTipA,ojA);
		Grad gradB = new Grad(sifraB,nazivB,gradTipB,ojB);
		Grad gradC = new Grad(sifraC,nazivC,gradTipC,ojC);
		
		//save records
		Grad savedGradA = this.gradDAO.save(gradA);
		Grad savedGradB = this.gradDAO.save(gradB);
		Grad savedGradC = this.gradDAO.save(gradC);
		
		assertNotNull(savedGradA.getId());
		assertNotNull(savedGradB.getId());
		assertNotNull(savedGradC.getId());
		
		//find records
		List<Grad> list = this.gradDAO.findAll();
		assertTrue(list.size() >= 3);
		
		Grad foundGrad = this.gradDAO.findOne(savedGradA.getId());
		assertEquals(foundGrad.getSifra(), savedGradA.getSifra());
		assertEquals(foundGrad.getNaziv(), savedGradA.getNaziv());
		assertEquals(foundGrad.getId(), savedGradA.getId());
		
		//update record
		savedGradA.setNaziv(nazivChanged);
		savedGradA = this.gradDAO.save(savedGradA);
		
		assertEquals(nazivChanged, savedGradA.getNaziv());
		
		//delete records
		this.gradDAO.delete(savedGradA);
		this.gradDAO.delete(savedGradB);
		this.gradDAO.delete(savedGradC);
	}
}
