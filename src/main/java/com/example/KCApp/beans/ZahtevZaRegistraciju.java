package com.example.KCApp.beans;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "zahtevZaRegistraciju")
@SequenceGenerator(name = "zahtevZaRegistracijutIdSeq", sequenceName = "zahtevZaRegistracijuIdGen", initialValue = 1, allocationSize = 1)
public class ZahtevZaRegistraciju {
	
	//neautentifikovani korisnik?
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "zahtevZaRegistracijuIdSeq")
	@Column(name = "idZahtevaZaRegistraciju", unique = true, nullable = false)
	private Integer idZahtevaZaRegistraciju;
	
	@Column(name="ime", unique=false, nullable=false)
	private String ime;
	@Column(name="prezime", unique=false, nullable=false)
	private String prezime;
	@Column(name="email", unique=true, nullable=false)
	private String email;
	@Column(name = "username", unique = true, nullable=false)
    private String username;
	@Column(name="password", unique=false, nullable=false)
	private String password;
	@Column(name="adresa", unique=false, nullable=false)
	private String adresa;
	@Column(name="grad", unique=false, nullable=false)
	private String grad;
	@Column(name="drzava", unique=false, nullable=false)
	private String drzava;
	@Column(name="brojTelefona", unique=false, nullable=false)
	private String brojTelefona;
	@Column(name="brojOsiguranika", unique=true, nullable=false)
	private int brojOsiguranika;

	public ZahtevZaRegistraciju() {
		super();
	}
	
	public ZahtevZaRegistraciju(Integer idZahtevaZaRegistraciju, String ime, String prezime, String email,
			String username, String password, String adresa, String grad, String drzava, String brojTelefona,
			int brojOsiguranika) {
		super();
		this.idZahtevaZaRegistraciju = idZahtevaZaRegistraciju;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.username = username;
		this.password = password;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		this.brojOsiguranika = brojOsiguranika;
	}

	public Integer getIdZahtevaZaRegistraciju() {
		return idZahtevaZaRegistraciju;
	}

	public void setIdZahtevaZaRegistraciju(Integer idZahtevaZaRegistraciju) {
		this.idZahtevaZaRegistraciju = idZahtevaZaRegistraciju;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public int getBrojOsiguranika() {
		return brojOsiguranika;
	}

	public void setBrojOsiguranika(int brojOsiguranika) {
		this.brojOsiguranika = brojOsiguranika;
	}

}
