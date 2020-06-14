package com.example.KCApp.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//@Entity
//@Inheritance(strategy = TABLE_PER_CLASS)
//@SequenceGenerator(name = "korisnikIdSeq", sequenceName = "korisnikIdGen", initialValue = 1, allocationSize = 1)
//@MappedSuperclass
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails{
	
	private static final long serialVersionUID = -8552379237972611631L;
	
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
	@Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="korisnikIdGen")
	@SequenceGenerator(name = "korisnikIdGen", sequenceName = "korisnik_id_seq", initialValue = 11, allocationSize=1)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;
	
	public User() {}
	
	public User(Integer id, String ime, String prezime, String email, String username, String password, String adresa, String grad, String drzava,
			String brojTelefona) {
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.username = username;
		this.password = password;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		
	}
	
	public User(String ime, String prezime, String email, String username, String password, String adresa, String grad, String drzava,
			String brojTelefona) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.username = username;
		this.password = password;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public void setPassword(String password) {
		Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
		this.password = password;
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
