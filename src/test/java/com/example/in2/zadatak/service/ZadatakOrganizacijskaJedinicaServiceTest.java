package com.example.in2.zadatak.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import models.Organizacijskajedinica;
import models.Tipoj;
import repository.OrganizacijskaJedinicaRepository;
import repository.TipojRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZadatakOrganizacijskaJedinicaServiceTest {

	@Autowired
	private OrganizacijskaJedinicaRepository organizacijskaJedinicaDAO;
	@Autowired
	private TipojRepository tipojDAO;
	private List<Tipoj> listTipoj;
	
	@Before
	public void listTipoj()
	{
		listTipoj = tipojDAO.findAll();
	}
	
	@Test
	public void testOrganizacijskaJedinica()
	{
		//test data
		String nazivChanged = "changed";
		String nazivA = "naziv A";
		String nazivB = "naziv B";
		String nazivC = "naziv C";
		String nazivD = "naziv D";
		
		String opisA = "opis A";
		String opisB = "opis B";
		String opisC = "opis C";
		String opisD = "opis D";
		
		Tipoj tipojA = null;
		Tipoj tipojB = null;
		Tipoj tipojC = null;
		Tipoj tipojD = null;
		if(!this.listTipoj.isEmpty())
		{
			tipojA = this.listTipoj.get(0);
			tipojB = this.listTipoj.get(0);
			if(this.listTipoj.size() > 1)
			{
				tipojC = this.listTipoj.get(1);
				tipojD = this.listTipoj.get(1);
			}
			else
			{
				tipojC = this.listTipoj.get(0);
				tipojD = this.listTipoj.get(0);
			}
		}
		
		Organizacijskajedinica organizacijskajedinicaA = new Organizacijskajedinica(nazivA,opisA,tipojA,null);
		Organizacijskajedinica organizacijskajedinicaB = new Organizacijskajedinica(nazivB,opisB,tipojB,null);
		Organizacijskajedinica organizacijskajedinicaC = new Organizacijskajedinica(nazivC,opisC,tipojC,organizacijskajedinicaA);
		Organizacijskajedinica organizacijskajedinicaD = new Organizacijskajedinica(nazivD,opisD,tipojD,organizacijskajedinicaB);
		
		//save records
		Organizacijskajedinica ojSavedA = this.organizacijskaJedinicaDAO.save(organizacijskajedinicaA);
		Organizacijskajedinica ojSavedB = this.organizacijskaJedinicaDAO.save(organizacijskajedinicaB);
		Organizacijskajedinica ojSavedC = this.organizacijskaJedinicaDAO.save(organizacijskajedinicaC);
		Organizacijskajedinica ojSavedD = this.organizacijskaJedinicaDAO.save(organizacijskajedinicaD);
		
		assertNotNull(ojSavedA.getId());
		assertNotNull(ojSavedB.getId());
		assertNotNull(ojSavedC.getId());
		assertNotNull(ojSavedD.getId());
		
		//find records
		List<Organizacijskajedinica> list = this.organizacijskaJedinicaDAO.findAll();
		assertTrue(list.size() > 0);
		
		Organizacijskajedinica foundOj = this.organizacijskaJedinicaDAO.findOne(ojSavedA.getId());
		assertEquals(ojSavedA.getNaziv(), foundOj.getNaziv());
		assertEquals(ojSavedA.getOpis(), foundOj.getOpis());
		assertEquals(ojSavedA.getTipoj().getId(), foundOj.getTipoj().getId());
		
		//update record
		ojSavedA.setNaziv(nazivChanged);
		ojSavedA = this.organizacijskaJedinicaDAO.save(ojSavedA);
		
		assertEquals(nazivChanged, ojSavedA.getNaziv());
		
		//delete records
		this.organizacijskaJedinicaDAO.delete(ojSavedC.getId());
		list = this.organizacijskaJedinicaDAO.findAll();
		for(Organizacijskajedinica oj : list)
		{
			if(oj.getParent()!=null && (oj.getParent().getId().intValue() == ojSavedB.getId().intValue()))
			{
				oj.setParent(null);
				this.organizacijskaJedinicaDAO.save(oj);
			}
		}
		this.organizacijskaJedinicaDAO.delete(ojSavedB.getId());
		ojSavedD = this.organizacijskaJedinicaDAO.findOne(ojSavedD.getId());
		this.organizacijskaJedinicaDAO.delete(ojSavedD.getId());
		this.organizacijskaJedinicaDAO.delete(ojSavedA.getId());
	}
	
	@Test
	public void testZupanije()
	{
		List<Organizacijskajedinica> regije = new ArrayList<Organizacijskajedinica>();
		regije.add(this.organizacijskaJedinicaDAO.findOne(1));
		regije.add(this.organizacijskaJedinicaDAO.findOne(2));
		regije.add(this.organizacijskaJedinicaDAO.findOne(3));
		regije.add(this.organizacijskaJedinicaDAO.findOne(4));
		
		List<Organizacijskajedinica> zupanije = this.organizacijskaJedinicaDAO.findByParentIn(regije);
		assertEquals(zupanije.size(), 14);
	}
}
