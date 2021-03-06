package com.example.KCApp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.service.OperacijaService;
import com.example.KCApp.service.PacijentService;

@RestController
@RequestMapping(value="/api")
public class OperacijaController {

	@Autowired
	private OperacijaService service;
	
	@Autowired
	private PacijentService serviceP;
	
	/*ISPISIVANJE OPERACIJA*/
	@GetMapping(value="/operacije/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Operacija> getAllOperacije(Model model, @PathVariable Integer id) {
		
		Pacijent p = serviceP.get(id);
		List<Operacija> listaOperacija = service.findAllByPacijent(p);
		List<Operacija> listaPovratna = new ArrayList<Operacija>();

		Date date = new Date();
		for(Operacija o : listaOperacija) {
			//System.out.println(date.compareTo(pr.getVreme()));
			if(date.compareTo(o.getVremeOperacije())>0) {
				listaPovratna.add(o);
			}
		}
		return listaPovratna;
	}
	
	
	/* ISPISIVANJE PREGLEDA LEKAR */
	@GetMapping(value = "/lekar/operacije/rk/{id}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Operacija> getAllOperacijaLekar(Model model, @PathVariable Integer id) {
		List<Operacija> listaOperacija = service.listAll();
		List<Operacija> listaPovratna = new ArrayList<Operacija>();
		for (Operacija o : listaOperacija) {
			if (id.equals(o.getLekar().getId())) {
				listaPovratna.add(o);
			
			}
		}
		//List<Pregled> prazna = new ArrayList<Pregled>();
		return listaPovratna;
	}
}
