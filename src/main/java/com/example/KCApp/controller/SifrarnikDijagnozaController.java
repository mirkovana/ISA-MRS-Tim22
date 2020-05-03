package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.AdministratorKlinikeDTO;
import com.example.KCApp.DTO.SifrarnikDijagnozaDTO;
import com.example.KCApp.beans.KlinickiCentar;
import com.example.KCApp.beans.SifrarnikDijagnoza;
import com.example.KCApp.service.KlinickiCentarService;
import com.example.KCApp.service.SifrarnikDijagnozaService;

@RestController
@RequestMapping(value="/api")
public class SifrarnikDijagnozaController {
	@Autowired
	private SifrarnikDijagnozaService service;
	@Autowired
	private KlinickiCentarService kcService;
	/*PRIKAZ SVIH SIFRARNIKA LEKOVA*/
	@GetMapping(value="/sifrarnikDijagnoza")
	public List<SifrarnikDijagnoza> getAllSifrarnikDijagnoza(Model model) {
		List<SifrarnikDijagnoza> sifrarnikDijagnoza = service.listAll();
		model.addAttribute("sifrarnikDijagnoza", sifrarnikDijagnoza );
		return sifrarnikDijagnoza ;
	}
	
	/*DODAVANJE SIFRARNIKA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/sifrarnikDijagnoza",consumes = "application/json")
	public ResponseEntity<SifrarnikDijagnozaDTO> saveAdminSifrarnikDijagnoza(@RequestBody SifrarnikDijagnozaDTO sifrarnikDijagnozaDTO) {

		SifrarnikDijagnoza sifrarnikDijagnoza = new SifrarnikDijagnoza();
		
	
		sifrarnikDijagnoza.setNazivDijagnoze(sifrarnikDijagnozaDTO.getNazivDijagnoze());
		sifrarnikDijagnoza.setSifraDijagnoze(sifrarnikDijagnozaDTO.getSifraDijagnoze());

		
		
		KlinickiCentar kc = kcService.get(1);
		sifrarnikDijagnoza.setKlinickiCentar(kc);

		//MILAN: u kliniku postavljamo jos klinicki centar jer ste u klasi Klinika navele da referenca ne sme biti null
		
		sifrarnikDijagnoza = service.save(sifrarnikDijagnoza);
		return new ResponseEntity<>(new SifrarnikDijagnozaDTO(sifrarnikDijagnoza), HttpStatus.CREATED);
	}
}
