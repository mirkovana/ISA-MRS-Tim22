package com.example.KCApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.repository.ZdravstveniKartonRepository;
import com.example.KCApp.service.ZdravstveniKartonService;

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
}
