package com.example.KCApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.AdministratorKlinickogCentraDTO;
import com.example.KCApp.beans.AdministratorKlinickogCentra;
import com.example.KCApp.beans.KlinickiCentar;
import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.repository.KlinikaRepository;
import com.example.KCApp.service.AdministratorKlinickogCentraService;
import com.example.KCApp.service.KlinickiCentarService;

@RestController
@RequestMapping(value="/api")
public class AdministratorKlinickogCentraController {
	@Autowired
	private AdministratorKlinickogCentraService service;
	@Autowired
	private KlinickiCentarService kcService;
	
	/*PRIKAZ PACIJENTA PO ID-u*/
	@GetMapping(value = "/adminiKC/{idKorisnika}")
	public  AdministratorKlinickogCentra findPacijentById(@PathVariable Integer idKorisnika) {
		 AdministratorKlinickogCentra adminiKC = service.get(idKorisnika);
		return adminiKC;
	}
	
	/*DODAVANJE PACIJENTA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/adminiKC",consumes = "application/json")
	public ResponseEntity<AdministratorKlinickogCentraDTO> saveAdminKC(@RequestBody AdministratorKlinickogCentraDTO administratorKlinickogCentraDTO) {

		AdministratorKlinickogCentra administratorKlinickogCentra = new AdministratorKlinickogCentra();
		administratorKlinickogCentra.setIme(administratorKlinickogCentraDTO.getIme());
		administratorKlinickogCentra.setPrezime(administratorKlinickogCentraDTO.getPrezime());
		administratorKlinickogCentra.setEmail(administratorKlinickogCentraDTO.getEmail());
		administratorKlinickogCentra.setLozinka(administratorKlinickogCentraDTO.getLozinka());
		administratorKlinickogCentra.setAdresa(administratorKlinickogCentraDTO.getAdresa());
		administratorKlinickogCentra.setGrad(administratorKlinickogCentraDTO.getGrad());
		administratorKlinickogCentra.setDrzava(administratorKlinickogCentraDTO.getDrzava());
		administratorKlinickogCentra.setBrojTelefona(administratorKlinickogCentraDTO.getBrojTelefona());
		
		
		
		KlinickiCentar kc = kcService.get(1);

		//MILAN: u kliniku postavljamo jos klinicki centar jer ste u klasi Klinika navele da referenca ne sme biti null
		administratorKlinickogCentra.setKlinickiCentar(kc);
		administratorKlinickogCentra = service.save(administratorKlinickogCentra);
		return new ResponseEntity<>(new AdministratorKlinickogCentraDTO(administratorKlinickogCentra), HttpStatus.CREATED);
	}
}
