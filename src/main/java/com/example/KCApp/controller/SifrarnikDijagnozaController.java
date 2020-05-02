package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.SifrarnikDijagnoza;
import com.example.KCApp.service.SifrarnikDijagnozaService;

@RestController
@RequestMapping(value="/api")
public class SifrarnikDijagnozaController {
	@Autowired
	private SifrarnikDijagnozaService service;
	
	/*PRIKAZ SVIH SIFRARNIKA LEKOVA*/
	@GetMapping(value="/sifrarnikDijagnoza")
	public List<SifrarnikDijagnoza> getAllSifrarnikDijagnoza(Model model) {
		List<SifrarnikDijagnoza> sifrarnikDijagnoza = service.listAll();
		model.addAttribute("sifrarnikDijagnoza", sifrarnikDijagnoza );
		return sifrarnikDijagnoza ;
	}
}
