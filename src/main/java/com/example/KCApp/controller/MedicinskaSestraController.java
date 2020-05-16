package com.example.KCApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.service.MedicinskaSestraService;

@RestController
@RequestMapping(value="/api")
public class MedicinskaSestraController {

	@Autowired
	private MedicinskaSestraService service;
	
	/*PRIKAZ MEDICINSKE SESTRE PO ID-u*/
	@GetMapping(value = "/medicinskeSestre/{id}")
	@PreAuthorize("hasRole('MS')")
	public MedicinskaSestra findPacijentById(@PathVariable Integer id) {
		MedicinskaSestra medicinskaSestra = service.get(id);
		return medicinskaSestra;
	}
}
