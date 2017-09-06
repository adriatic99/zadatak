package models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import converters.LocalDateTimeAttributeConverter;

@Entity
public class Dogadjaj implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "dogadjajID")
    private Integer id;
	
	@Column(name = "naziv") 
    private String naziv;
	
	@Column(name = "odvrijeme")
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime odVrijeme;
	
	@Column(name = "dovrijeme")
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime doVrijeme;

	@Column(name = "slobodanulaz")
	private boolean slobodanUlaz;
	
	@ManyToOne
    @JoinColumn(name="gradFK", referencedColumnName = "gradID")	
	private Grad grad;

	public Dogadjaj() {}
	
	public Dogadjaj(String naziv, LocalDateTime odVrijeme, LocalDateTime doVrijeme, boolean slobodanUlaz, Grad grad) {
		this.naziv = naziv;
		this.odVrijeme = odVrijeme;
		this.doVrijeme = doVrijeme;
		this.slobodanUlaz = slobodanUlaz;
		this.grad = grad;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Grad getGrad() {
		return grad;
	}

	public void setGrad(Grad grad) {
		this.grad = grad;
	}
}
