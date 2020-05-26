package com.example.KCApp.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.AdministratorKlinickogCentra;
import com.example.KCApp.beans.KlinickiCentar;
import com.example.KCApp.repository.UserRepository;
import com.example.KCApp.service.AdministratorKlinickogCentraService;
import com.example.KCApp.service.AuthorityService;
import com.example.KCApp.service.KlinickiCentarService;
import com.example.KCApp.service.UserService;

@RestController
@RequestMapping(value="/api")
public class AdministratorKlinickogCentraController {
	@Autowired
	private AdministratorKlinickogCentraService service;
	@Autowired
	private KlinickiCentarService kcService;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	
	@Autowired
	private UserService userService;
	

	@Autowired
	private AuthorityService authorityService;
	
	/*PRIKAZ ADMINA KLINICKOG CENTRA PO ID-u*/
	@GetMapping(value = "/adminiKC/{id}")
	@PreAuthorize("hasRole('ADMINKC')")
	public  AdministratorKlinickogCentra findPacijentById(@PathVariable Integer id) {
		 AdministratorKlinickogCentra adminiKC = service.get(id);
		return adminiKC;
	}
	
	/*DODAVANJE ADMINISTRATORA KLINICKOG CENTRA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value = "/adminiKC", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINKC')")
	public ResponseEntity<Boolean> saveAdminKC(
			@RequestBody UserDTO userData) {

		AdministratorKlinickogCentra user = new AdministratorKlinickogCentra();
		user.setPassword(passwordEncoder.encode(userData.getPassword()));
		user.setUsername(userData.getUsername());
		user.setGrad(userData.getGrad());
		user.setDrzava(userData.getDrzava());
		user.setBrojTelefona(userData.getBrojTelefona());
		user.setIme(userData.getIme());
		user.setPrezime(userData.getPrezime());
		user.setEmail(userData.getUsername());
		user.setAdresa(userData.getAdresa());
		user.setLastPasswordResetDate(userData.getLastPasswordResetDate());
		user.setAuthorities(Arrays.asList(authorityService.findOne(1)));
		KlinickiCentar kc = kcService.get(1);
		user.setKlinickiCentar(kc);
		userService.save(user);
		

		

		// MILAN: u kliniku postavljamo jos klinicki centar jer ste u klasi Klinika
		// navele da referenca ne sme biti null
		
		//user = service.save(user);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
