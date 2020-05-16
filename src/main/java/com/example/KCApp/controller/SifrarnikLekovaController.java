package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.SifrarnikDijagnozaDTO;
import com.example.KCApp.DTO.SifrarnikLekovaDTO;
import com.example.KCApp.beans.KlinickiCentar;
import com.example.KCApp.beans.SifrarnikLekova;
import com.example.KCApp.service.KlinickiCentarService;
import com.example.KCApp.service.SifrarnikLekovaService;

@RestController
@RequestMapping(value="/api")
public class SifrarnikLekovaController {
	@Autowired
	private SifrarnikLekovaService service;
	
	@Autowired
	private KlinickiCentarService kcService;
	
	/*PRIKAZ SVIH SIFRARNIKA LEKOVA*/
	@GetMapping(value="/sifrarnikLekova")
	@PreAuthorize("hasRole('ADMINKC') or hasRole('LEKAR')")
	public List<SifrarnikLekova> getAllSifrarnikLekova(Model model) {
		List<SifrarnikLekova> sifrarnikLekova = service.listAll();
		model.addAttribute("sifrarnikLekova", sifrarnikLekova );
		return sifrarnikLekova ;
	}
	
	/*DODAVANJE SIFRARNIKA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/sifrarnikLekova",consumes = "application/json")
	@PreAuthorize("hasRole('ADMINKC')")
	public ResponseEntity<SifrarnikLekovaDTO> saveAdminSifrarnikDijagnoza(@RequestBody SifrarnikLekovaDTO sifrarnikLekovaDTO) {

		SifrarnikLekova sifrarnikLekova = new SifrarnikLekova();
		
	
		sifrarnikLekova.setNazivLeka(sifrarnikLekovaDTO.getNazivLeka());
		sifrarnikLekova.setSifraLeka(sifrarnikLekovaDTO.getSifraLeka());

		
		
		KlinickiCentar kc = kcService.get(1);
		sifrarnikLekova.setKlinickiCentar(kc);

		//MILAN: u kliniku postavljamo jos klinicki centar jer ste u klasi Klinika navele da referenca ne sme biti null
		
		sifrarnikLekova = service.save(sifrarnikLekova);
		return new ResponseEntity<>(new SifrarnikLekovaDTO(sifrarnikLekova), HttpStatus.CREATED);
	}
}
