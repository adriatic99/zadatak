package models;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Dogadjaj implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "dogadjajID")
    private Integer id;
	
	@Column(name = "naziv") 
    private String naziv;
	
	@Column(name = "od")
	private Timestamp odVrijeme;
	
	@Column(name = "do")
	private Timestamp doVrijeme;

	@Column(name = "slobodanUlaz")
	private boolean slobodanUlaz;
	
	@ManyToOne
    @JoinColumn(name="gradFK", referencedColumnName = "gradID")	
	private Grad grad;

	public Dogadjaj() {}
	
	public Dogadjaj(String naziv, Timestamp odVrijeme, Timestamp doVrijeme, boolean slobodanUlaz, Grad grad) {
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

	public Timestamp getOdVrijeme() {
		return odVrijeme;
	}

	public void setOdVrijeme(Timestamp odVrijeme) {
		this.odVrijeme = odVrijeme;
	}

	public Timestamp getDoVrijeme() {
		return doVrijeme;
	}

	public void setDoVrijeme(Timestamp doVrijeme) {
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
