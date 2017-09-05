package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Velicinagrada implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "vgID")
    private Integer id;
	
	@Column(name = "sifra") 
    private Integer sifra;
	
	@Column(name = "naziv") 
    private String naziv;
	
	@Column(name = "aktivan") 
    private Boolean aktivan;

	public Velicinagrada() {}
	
	public Velicinagrada(Integer sifra, String naziv, Boolean aktivan) {
		this.sifra = sifra;
		this.naziv = naziv;
		this.aktivan = aktivan;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSifra() {
		return sifra;
	}

	public void setSifra(Integer sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}
}
