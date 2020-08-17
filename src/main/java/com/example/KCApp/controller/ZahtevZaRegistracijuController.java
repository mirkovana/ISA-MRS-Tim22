package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.ZahtevZaRegistraciju;
import com.example.KCApp.service.ZahtevZaRegistracijuService;

@RestController
@RequestMapping(value="/api")
public class ZahtevZaRegistracijuController {

	@Autowired
	private ZahtevZaRegistracijuService service;
	
	/* ISPISIVANJE ZAHTEVA ZA REGISTRACIJU */
	@GetMapping(value = "/zahtevizr")
	@PreAuthorize("hasRole('ADMINKC')")
	public List<ZahtevZaRegistraciju> getAllZahtevi(Model model) {
		List<ZahtevZaRegistraciju> listaZahteva = service.listAll();
		return listaZahteva;
	}
}
