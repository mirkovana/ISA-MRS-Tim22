package com.example.KCApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.Recept;
import com.example.KCApp.beans.SifrarnikLekova;
import com.example.KCApp.repository.ReceptRepository;
import com.example.KCApp.service.MedicinskaSestraService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.ReceptService;
import com.example.KCApp.service.SifrarnikLekovaService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class ReceptController {
	@Autowired
	private ReceptService serviceRecept;
	@Autowired
	private PregledService servicePregled;
	
	@Autowired
	private ReceptRepository repository;
	
	@Autowired
	private MedicinskaSestraService serviceMS;

	@Autowired
	private SifrarnikLekovaService serviceSL;
	/*PRIKAZ SVIH NEOVERENIH RECEPATA*/
	//@GetMapping(value="/recepti/ms/{id}")
	@RequestMapping(value="/recepti/ms/{id}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MS')")
	public List<Recept> getAllRecepti(Model model, @PathVariable Integer id) {
		MedicinskaSestra ms = serviceMS.get(id);
		Klinika klinikaMS = ms.getKlinika();
		List<Recept> listaRecepata= new ArrayList<Recept>();
		List<Recept> recepti = new ArrayList<Recept>();
		//ovde ide sada provera 
		//List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaMS);
		List<Recept> listaNO= serviceRecept.findAllByOveren(false);
		for(Recept r : listaNO) {
			if(r.getPregled().getKlinika()==klinikaMS) {
				model.addAttribute("listaRecepti", recepti);
				recepti.add(r);
			}
		}
		
	
		
		for(int i=0;i<recepti.size();i++) {
			System.out.println(recepti.get(i));
		}
		//model.addAttribute("listaPacijenata", listaPacijenata);
		return recepti;
	}
	/*UPDATE*/
	@PutMapping(value="/recepti/{id}/{idRecepta}", consumes = "application/json")
	@PreAuthorize("hasRole('MS')")
	public Recept updateRecept(@PathVariable Integer id, @PathVariable Integer idRecepta) throws NotFoundException {
		MedicinskaSestra sestra = serviceMS.get(id);
		return repository.findById(idRecepta)
				.map(recept->{
					
					recept.setMedicinskaSestra(sestra);
					recept.setOveren(true);
					
					return repository.save(recept);
				}).orElseThrow(() -> new NotFoundException("Recept not found with id " + idRecepta));
		
	}
	@PostMapping(value= "/recepti/dodaj/{nazivLeka}/{idPregleda}",consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public Recept saveKlinika(@PathVariable Integer idPregleda,@PathVariable String nazivLeka ) {
		List<SifrarnikLekova> listaSL = serviceSL.listAll();
		SifrarnikLekova sl = new SifrarnikLekova();
		for(SifrarnikLekova s: listaSL) {
			if(s.getNazivLeka().equals(nazivLeka)) {
				sl=s;
			}
		}
		Recept recept= new Recept();
		Pregled p = servicePregled.get(idPregleda);
		recept.setMedicinskaSestra(null);
		recept.setOveren(false);
		recept.setPregled(p);
		recept.setSifraLeka(sl);
		recept=serviceRecept.save(recept);
		return recept;
		}
}
