package com.example.KCApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.LekarDTO;
import com.example.KCApp.DTO.PacijentDTO;
import com.example.KCApp.DTO.SalaDTO;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.RadniKalendarL;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.repository.PacijentRepository;
import com.example.KCApp.repository.SalaRepository;
import com.example.KCApp.service.KlinikaService;
import com.example.KCApp.service.SalaService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class SalaController {

	@Autowired
	private SalaService service;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private SalaRepository repository;
	
	/*ISPISIVANJE SALA*/
	@GetMapping(value="/sale")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Sala> getAllSale(Model model) {
		List<Sala> listaSala = service.listAll();
		model.addAttribute("listaSala", listaSala);
		return listaSala;
	}
	
	/*BRISANJE SALE SA ODREDJENIM ID*/
	@DeleteMapping(value= "/sale/{idSale}")
	@PreAuthorize("hasRole('ADMINK')")
	public String deleteSala(@PathVariable Integer idSale) {
		
		Sala sala = service.get(idSale);
	
		if (sala != null && !sala.getKlinika().getOperacije().contains(sala)) {
			service.delete(idSale);
			return "Uspesno obrisana sala sa id: " + idSale;
		} 
		else if (sala != null & sala.getKlinika().getOperacije().contains(sala)){ //kako proveravam da li je sala rez?
			return "Sala sa id: " + idSale + "ne moze da se obrise jer je rezervisana."; 
		}
		else {
			return "Sala sa id: " + idSale + " nije pronadjena"; //ne prikazuje poruku kad se stavi id koji ne postoji
		}
	}
	
	/*PRETRAGA SALE PO KRITERIJUMU - BROJ SALE*/
	@GetMapping(value = "/sale/brojSale/{brojSale}")
	@PreAuthorize("hasRole('ADMINK')")
	public Sala findSalaByBrojSale(@PathVariable int brojSale) { //da li treba i po nazivu?
		Sala sala = service.findByBrojSale(brojSale);
		return sala;
	}
	
	
	
	/*DODAVANJE SALA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/sale",consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<SalaDTO> saveSala(@RequestBody SalaDTO salaDTO) {

		Sala sala = new Sala();
		sala.setBrojSale(salaDTO.getBrojSale());
		sala.setNazivSale(salaDTO.getNazivSale());
		Integer idK = salaDTO.getKlinika();
		
		Klinika k = klinikaService.get(idK);

		sala.setKlinika(k);
		sala = service.save(sala);
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}
	
	/*UPDATE SALE*/
	@PutMapping(value="/sale/{idSale}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public Sala updateSala(@PathVariable Integer idSale, @Valid @RequestBody SalaDTO salaUpdated) throws NotFoundException {
		return repository.findById(idSale)
				.map(sala->{
					sala.setNazivSale(salaUpdated.getNazivSale());
					sala.setBrojSale(salaUpdated.getBrojSale());
					return repository.save(sala);
				}).orElseThrow(() -> new NotFoundException("Pacijent not found with id " + idSale));
		
	}
	
}
