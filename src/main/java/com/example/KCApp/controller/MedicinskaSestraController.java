package com.example.KCApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.User;
import com.example.KCApp.repository.UserRepository;
import com.example.KCApp.service.MedicinskaSestraService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class MedicinskaSestraController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private MedicinskaSestraService service;
	
	/*PRIKAZ MEDICINSKE SESTRE PO ID-u*/
	@GetMapping(value = "/medicinskeSestre/{id}")
	@PreAuthorize("hasRole('MS')")
	public MedicinskaSestra findPacijentById(@PathVariable Integer id) {
		MedicinskaSestra medicinskaSestra = service.get(id);
		return medicinskaSestra;
	}
	
	//IZMENA PROFILA
			@PutMapping(value="/medicinskeSestre/{id}", consumes = "application/json")
			@PreAuthorize("hasRole('MS')")
			public User updateMS(@PathVariable Integer id, @Valid @RequestBody UserDTO msUpdated) throws NotFoundException {
				return repository.findById(id)
						.map(user->{
							//klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
							user.setIme(msUpdated.getIme());
							user.setPrezime(msUpdated.getPrezime());

							user.setAdresa(msUpdated.getAdresa());
							user.setGrad(msUpdated.getGrad());
							user.setDrzava(msUpdated.getDrzava());
							user.setBrojTelefona(msUpdated.getBrojTelefona());
							//user.setAuthorities(Arrays.asList(authorityService.findOne(1)));
							return repository.save(user);
						}).orElseThrow(() -> new NotFoundException("Administrator nije pronadjen sa id : " + id));
				
			}
}
