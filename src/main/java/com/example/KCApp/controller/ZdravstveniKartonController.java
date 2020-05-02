package com.example.KCApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.ZdravstveniKartonDTO;
import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.repository.ZdravstveniKartonRepository;
import com.example.KCApp.service.ZdravstveniKartonService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class ZdravstveniKartonController {
	
	@Autowired
	private ZdravstveniKartonService service;
	
	@Autowired
	private ZdravstveniKartonRepository repository;
	
	@GetMapping(value = "/kartoni/{idZdravstvenogKartona}")
	public ZdravstveniKarton findZDById(@PathVariable Integer idZdravstvenogKartona) {
		ZdravstveniKarton zdravstveniKarton = service.get(idZdravstvenogKartona);
		return zdravstveniKarton;
	}
	
	
	/*UPDATE PACIJENTA*/
	@PutMapping(value="/kartoni/{idZdravstvenogKartona}", consumes = "application/json")
	public ZdravstveniKarton updateZdravstveniKarton(@PathVariable Integer idZdravstvenogKartona, @Valid @RequestBody ZdravstveniKartonDTO zdravstveniKartonUpdated) throws NotFoundException {
		return repository.findById(idZdravstvenogKartona)
				.map(zdravstveniKarton->{
					//klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
					zdravstveniKarton.setTezina(zdravstveniKartonUpdated.getTezina());
					zdravstveniKarton.setVisina(zdravstveniKartonUpdated.getVisina());
					zdravstveniKarton.setDioptrija(zdravstveniKartonUpdated.getDioptrija());
					zdravstveniKarton.setKrvnaGrupa(zdravstveniKartonUpdated.getKrvnaGrupa());
				
					return repository.save(zdravstveniKarton);
				}).orElseThrow(() -> new NotFoundException("Zdravstveni karton not found with id " + idZdravstvenogKartona));
		
	}
}
