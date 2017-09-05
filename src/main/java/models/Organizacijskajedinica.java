package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

@Entity
public class Organizacijskajedinica implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "ojID")
    private Integer id;
	
	@Column(name = "naziv") 
    private String naziv;
	
	@Column(name = "opis") 
    private String opis;

	@ManyToOne
	@JoinColumn(name = "tipFK", referencedColumnName = "tipOJID")
	private Tipoj tipoj;

	@ManyToOne
    @JoinColumn(name="parentID", referencedColumnName = "ojID")	
	private Organizacijskajedinica parent;

	public Organizacijskajedinica() {}
	
	public Organizacijskajedinica(String naziv, String opis, Tipoj tipoj, Organizacijskajedinica parent) {
		this.naziv = naziv;
		this.opis = opis;
		this.tipoj = tipoj;
		this.parent = parent;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Tipoj getTipoj() {
		return tipoj;
	}

	public void setTipoj(Tipoj tipoj) {
		this.tipoj = tipoj;
	}

	public Organizacijskajedinica getParent() {
		return parent;
	}

	public void setParent(Organizacijskajedinica parent) {
		this.parent = parent;
	}
}
