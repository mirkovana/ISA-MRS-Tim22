package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.service.LekarService;

@RestController
@RequestMapping(value="/api")
public class LekarController {
	@Autowired
	private LekarService service;
	
	
	/*PRIKAZ SVIH LEKARA KLINIKE*/
	@GetMapping(value="/lekari")
	public List<Lekar> getAllLekari(Model model) {
		List<Lekar> listaLekara = service.listAll();
		model.addAttribute("listaLekara", listaLekara);
		return listaLekara;
	}
	
	/*PRIKAZ PACIJENTA PO ID-u*/
	@GetMapping(value = "/lekari/{idKorisnika}")
	public Lekar findLekarById(@PathVariable Integer idKorisnika) {
		Lekar lekar = service.get(idKorisnika);
		return lekar;
	}
	
	/*PRETRAGA LEKARA PO KRITERIJUMU - TIP PREGLEDA*/
	@GetMapping(value = "/lekari/tipPregleda/{tipPregleda}")
	public List<Lekar> findAllLekarByTipPregleda(@PathVariable TipPregleda tipPregleda) {
		List<Lekar> lekar = service.findAllByTipPregleda(tipPregleda);
		return lekar;
	}
}
