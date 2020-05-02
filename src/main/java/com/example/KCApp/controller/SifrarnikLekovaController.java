package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.SifrarnikLekova;
import com.example.KCApp.service.SifrarnikLekovaService;

@RestController
@RequestMapping(value="/api")
public class SifrarnikLekovaController {
	@Autowired
	private SifrarnikLekovaService service;
	
	/*PRIKAZ SVIH SIFRARNIKA LEKOVA*/
	@GetMapping(value="/sifrarnikLekova")
	public List<SifrarnikLekova> getAllSifrarnikLekova(Model model) {
		List<SifrarnikLekova> sifrarnikLekova = service.listAll();
		model.addAttribute("sifrarnikLekova", sifrarnikLekova );
		return sifrarnikLekova ;
	}
}
