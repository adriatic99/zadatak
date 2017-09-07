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

public class DogadjajCriteria {
	 
    private String naziv;
	private LocalDateTime odVrijeme;
	private LocalDateTime doVrijeme;
	private boolean slobodanUlaz;
	private List<Grad> gradovi;
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public LocalDateTime getOdVrijeme() {
		return odVrijeme;
	}
	public void setOdVrijeme(LocalDateTime odVrijeme) {
		this.odVrijeme = odVrijeme;
	}
	public LocalDateTime getDoVrijeme() {
		return doVrijeme;
	}
	public void setDoVrijeme(LocalDateTime doVrijeme) {
		this.doVrijeme = doVrijeme;
	}
	public boolean isSlobodanUlaz() {
		return slobodanUlaz;
	}
	public void setSlobodanUlaz(boolean slobodanUlaz) {
		this.slobodanUlaz = slobodanUlaz;
	}
	public List<Grad> getGradovi() {
		return gradovi;
	}
	public void setGradovi(List<Grad> gradovi) {
		this.gradovi = gradovi;
	}

	public void addGrad(Grad grad)
	{
		if(this.gradovi == null)
			this.gradovi = new ArrayList<Grad>();
		this.gradovi.add(grad);
	}
}
