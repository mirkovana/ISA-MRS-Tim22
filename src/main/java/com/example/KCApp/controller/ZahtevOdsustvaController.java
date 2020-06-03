package com.example.KCApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.ZahtevOdsustvaDTO;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.service.UserService;
import com.example.KCApp.service.ZahtevOdsustvaService;

@RestController
@RequestMapping(value="/api")
public class ZahtevOdsustvaController {

	@Autowired
	private ZahtevOdsustvaService service;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(value= "/zahteviOdsustva/{id}",consumes = "application/json")
	@PreAuthorize("hasRole('MS')")
	public ResponseEntity<ZahtevOdsustvaDTO> saveKlinika(@RequestBody ZahtevOdsustvaDTO zahtevOdsustvaDTO, @PathVariable Integer id) {

		
		ZahtevOdsustva zahtev = new ZahtevOdsustva();
		User u = userService.findById(id);
		zahtev.setUser(u);
		zahtev.setDatumKraja(zahtevOdsustvaDTO.getDatumKraja());
		zahtev.setDatumPocetka(zahtevOdsustvaDTO.getDatumPocetka());
		zahtev.setOdobren(false);
		zahtev.setRazlog(zahtevOdsustvaDTO.getRazlog());
		
		zahtev=service.save(zahtev);
		return new ResponseEntity<>(new ZahtevOdsustvaDTO(zahtev), HttpStatus.CREATED);
		}
}
