package com.example.KCApp.DTO;

import java.sql.Timestamp;

import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.UserTokenState;

public class UserDTO {
	private String ime;
	private String prezime;
	private String email;
	private String username;
	private String password;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private Timestamp lastPasswordResetDate;
	private Integer id;
	private String role;
	private UserTokenState token;
	
	public UserDTO() {
		super();
		this.id = 0;
		this.ime = "";
		this.prezime = "";
		this.email = "";
		this.username = "";
		this.password = "";
		this.adresa = "";
		this.grad = "";
		this.drzava = "";
		this.brojTelefona = "";
		this.lastPasswordResetDate = new Timestamp(0);
		this.token = new UserTokenState();
	}
	
	public UserDTO(String ime, String prezime, String email, String username, String password, String adresa,
			String grad, String drzava, String brojTelefona, Timestamp lastPasswordResetDate, Integer id, UserTokenState token) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.username = username;
		this.password = password;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.token = token;
		this.id = id;
	}
	
	public UserDTO(User user, UserTokenState userTokenState) {
		this.id = user.getId();
		this.ime = user.getIme();
		this.prezime = user.getPrezime();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.adresa = user.getAdresa();
		this.grad = user.getGrad();
		this.drzava = user.getDrzava();
		this.brojTelefona = user.getBrojTelefona();
		this.lastPasswordResetDate = user.getLastPasswordResetDate();
		this.token = userTokenState;
		for(Object au : user.getAuthorities()) {
			this.role = ((Authority) au).getName();
		}
	}

	public UserDTO(User user) {
		this(
				user.getIme(),
				user.getPrezime(),
				user.getEmail(),
				user.getUsername(),
				user.getPassword(),
				user.getAdresa(), 
				user.getGrad(), 
				user.getDrzava(),
				user.getBrojTelefona(),
				user.getLastPasswordResetDate(),
				user.getId(),
				new UserTokenState()
			);
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

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserTokenState getToken() {
		return token;
	}

	public void setToken(UserTokenState token) {
		this.token = token;
	}
}
