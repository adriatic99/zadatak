package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name="grad")
public class Grad implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "gradID")
    private Integer id;
	
	@Column(name = "sifra") 
    private Integer sifra;
	
	@Column(name = "naziv") 
    private String naziv;

	@ManyToOne
	@JoinColumn(name = "tipFK", referencedColumnName = "vgID")
	private Velicinagrada velicinaGrada;

	@ManyToOne
    @JoinColumn(name="orgjedinicaFK", referencedColumnName = "ojID")	
	private Organizacijskajedinica organizacijskaJedinica;

	public Grad() {}
	
	public Grad(Integer sifra, String naziv, Velicinagrada velicinaGrada, Organizacijskajedinica organizacijskaJedinica) {
		this.sifra = sifra;
		this.naziv = naziv;
		this.velicinaGrada = velicinaGrada;
		this.organizacijskaJedinica = organizacijskaJedinica;
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

	public Velicinagrada getVelicinaGrada() {
		return velicinaGrada;
	}

	public void setVelicinaGrada(Velicinagrada velicinaGrada) {
		this.velicinaGrada = velicinaGrada;
	}

	public Organizacijskajedinica getOrganizacijskaJedinica() {
		return organizacijskaJedinica;
	}

	public void setOrganizacijskaJedinica(Organizacijskajedinica organizacijskaJedinica) {
		this.organizacijskaJedinica = organizacijskaJedinica;
	}
}
