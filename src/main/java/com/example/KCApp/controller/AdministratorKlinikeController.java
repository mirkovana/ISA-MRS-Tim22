package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.KCApp.DTO.AdministratorKlinickogCentraDTO;
import com.example.KCApp.DTO.AdministratorKlinikeDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.KlinikaService;

@RestController
@RequestMapping(value="/api")
public class AdministratorKlinikeController {
	@Autowired
	private AdministratorKlinikeService service;
	
	@Autowired
	private KlinikaService klinikaService;
	
	/*PRIKAZ ADMINISTRATORA KLINIKE PO ID-u*/
	@GetMapping(value = "/adminiKlinike/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public AdministratorKlinike findAdministratorKlinikeById(@PathVariable Integer id) {
		AdministratorKlinike administratorKlinike = service.get(id);
		return administratorKlinike;
	}
	
	
	/*PRIKAZ SVIH ADMINA KLINIKE*/
	@GetMapping(value="/adminiKlinike")
	@PreAuthorize("hasRole('ADMINKC')")
	public List<AdministratorKlinike> getAllAdministratorKlinike(Model model) {
		List<AdministratorKlinike> administratorKlinike = service.listAll();
		model.addAttribute("administratorKlinike", administratorKlinike);
		return administratorKlinike;
	}
	
	/*DODAVANJE ADMINISTRATORA KLINIKE*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/adminiKlinike",consumes = "application/json")
	@PreAuthorize("hasRole('ADMINKC') or hasRole('ADMINK')")
	public ResponseEntity<AdministratorKlinikeDTO> saveAdminKlinike(@RequestBody AdministratorKlinikeDTO administratorKlinikeDTO) {

		AdministratorKlinike administratorKlinike = new AdministratorKlinike();
		administratorKlinike.setIme(administratorKlinikeDTO.getIme());
		administratorKlinike.setPrezime(administratorKlinikeDTO.getPrezime());
		administratorKlinike.setEmail(administratorKlinikeDTO.getEmail());
		administratorKlinike.setUsername(administratorKlinikeDTO.getUsername());
		administratorKlinike.setPassword(administratorKlinikeDTO.getPassword());
		administratorKlinike.setAdresa(administratorKlinikeDTO.getAdresa());
		administratorKlinike.setGrad(administratorKlinikeDTO.getGrad());
		administratorKlinike.setDrzava(administratorKlinikeDTO.getDrzava());
		administratorKlinike.setBrojTelefona(administratorKlinikeDTO.getBrojTelefona());
		//administratorKlinike.setKlinika(administratorKlinikeDTO.getKlinika());
		Integer idK=administratorKlinikeDTO.getKlinika();
		
		Klinika k = klinikaService.get(idK);
	  

		//MILAN: u kliniku postavljamo jos klinicki centar jer ste u klasi Klinika navele da referenca ne sme biti null
		administratorKlinike.setKlinika(k);
		administratorKlinike = service.save(administratorKlinike);
		return new ResponseEntity<>(new AdministratorKlinikeDTO(administratorKlinike), HttpStatus.CREATED);
	}
}
