package models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import converters.LocalDateTimeAttributeConverter;

public class DogadjajQuery {
	 
    private String naziv;
	private LocalDateTime odVrijemePocetak;
	private LocalDateTime doVrijemePocetak;
	private LocalDateTime odVrijemeKraj;
	private LocalDateTime doVrijemeKraj;
	private Boolean slobodanUlaz;
	private List<Grad> gradovi;
	
	public void ponisti()
	{
		this.naziv = null;
		this.odVrijemePocetak = null;
		this.doVrijemePocetak = null;
		this.odVrijemeKraj = null;
		this.doVrijemeKraj = null;
		this.slobodanUlaz = null;
		this.gradovi = null;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public LocalDateTime getOdVrijemePocetak() {
		return odVrijemePocetak;
	}
	public void setOdVrijemePocetak(LocalDateTime odVrijemePocetak) {
		this.odVrijemePocetak = odVrijemePocetak;
	}
	public LocalDateTime getDoVrijemePocetak() {
		return doVrijemePocetak;
	}
	public void setDoVrijemePocetak(LocalDateTime doVrijemePocetak) {
		this.doVrijemePocetak = doVrijemePocetak;
	}
	public LocalDateTime getOdVrijemeKraj() {
		return odVrijemeKraj;
	}
	public void setOdVrijemeKraj(LocalDateTime odVrijemeKraj) {
		this.odVrijemeKraj = odVrijemeKraj;
	}
	public LocalDateTime getDoVrijemeKraj() {
		return doVrijemeKraj;
	}
	public void setDoVrijemeKraj(LocalDateTime doVrijemeKraj) {
		this.doVrijemeKraj = doVrijemeKraj;
	}
	public Boolean getSlobodanUlaz() {
		return slobodanUlaz;
	}
	public Boolean isSlobodanUlaz() {
		return slobodanUlaz;
	}
	public void setSlobodanUlaz(Boolean slobodanUlaz) {
		this.slobodanUlaz = slobodanUlaz;
	}
	public void setSlobodanUlazString(String value) {
		if(value.equals("Da"))
			this.slobodanUlaz = true;
		else if(value.equals("Ne"))
			this.slobodanUlaz = false;
		else
			this.slobodanUlaz = null;
	}
	public String getSlobodanUlazString() {
		if(this.slobodanUlaz == null)
			return "Nevažno";
		else if(this.slobodanUlaz.booleanValue() == true)
			return "Da";
		else if(this.slobodanUlaz.booleanValue() == false)
			return "Ne";
		else
			return "Nevažno";
	}
	public List<Grad> getGradovi() {
		return gradovi;
	}
	public void setGradovi(List<Grad> gradovi) {
		this.gradovi = gradovi;
	}

	public void addGrad(Grad grad)
	{
		if(grad == null)
			return;
		if(this.gradovi == null)
			this.gradovi = new ArrayList<Grad>();
		this.gradovi.add(grad);
	}
}
