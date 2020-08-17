package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.ZahtevZaRegistraciju;
import com.example.KCApp.service.EmailService;
import com.example.KCApp.service.ZahtevZaRegistracijuService;

@RestController
@RequestMapping(value="/api")
public class ZahtevZaRegistracijuController {

	@Autowired
	private ZahtevZaRegistracijuService service;
	
	
	@Autowired
	private EmailService emailService;
	
	/* ISPISIVANJE ZAHTEVA ZA REGISTRACIJU */
	@GetMapping(value = "/zahtevizr")
	@PreAuthorize("hasRole('ADMINKC')")
	public List<ZahtevZaRegistraciju> getAllZahtevi(Model model) {
		List<ZahtevZaRegistraciju> listaZahteva = service.listAll();
		return listaZahteva;
	}
	
	
	
	@PostMapping(value= "/odbijenZahtev/{idZahteva}",  consumes="application/json")
	@PreAuthorize("hasRole('ADMINKC')")
   	public ResponseEntity deniedRegAsync(@PathVariable Integer idZahteva){
		ZahtevZaRegistraciju user = service.get(idZahteva);

	
		String opis = "zato sto probavamo ";
		try {
			service.delete(idZahteva); 
			emailService.sendNotificaitionDeniedAsync(user, opis);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch( Exception e ){
			System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
