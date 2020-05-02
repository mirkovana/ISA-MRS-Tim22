package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.service.SalaService;

@RestController
@RequestMapping(value="/api")
public class SalaController {

	@Autowired
	private SalaService service;
	
	/*ISPISIVANJE SALA*/
	@GetMapping(value="/sale")
	public List<Sala> getAllSale(Model model) {
		List<Sala> listaSala = service.listAll();
		model.addAttribute("listaSala", listaSala);
		return listaSala;
	}
}
