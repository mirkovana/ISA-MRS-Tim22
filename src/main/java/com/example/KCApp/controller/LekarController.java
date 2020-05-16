package com.example.KCApp.controller;

import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.KCApp.DTO.PacijentDTO;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.RadniKalendarL;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.service.KlinikaService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.DTO.LekarDTO;
import com.example.KCApp.repository.LekarRepository;
import com.example.KCApp.service.LekarService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class LekarController {
	
	@Autowired
	private LekarService service;
	
	@Autowired
	private KlinikaService klinikaService;
	
	/*PRIKAZ SVIH LEKARA KLINIKE*/
	@GetMapping(value="/lekari")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Lekar> getAllLekari(Model model) {
		List<Lekar> listaLekara = service.listAll();
		model.addAttribute("listaLekara", listaLekara);
		return listaLekara;
	}
	
	/*PRIKAZ LEKARA PO ID-u*/
	@PreAuthorize("hasRole('ADMINK')")
	@GetMapping(value = "/lekari/{id}")
	public Lekar findLekarById(@PathVariable Integer id) {
		Lekar lekar = service.get(id);
		return lekar;
	}
	
	/*PRETRAGA LEKARA PO KRITERIJUMU - TIP PREGLEDA*/
	@GetMapping(value = "/lekari/tipPregleda/{tipPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByTipPregleda(@PathVariable TipPregleda tipPregleda) {
		List<Lekar> lekar = service.findAllByTipPregleda(tipPregleda);
		return lekar;
	}
	
	/*DODAVANJE LEKARA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/lekari",consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<LekarDTO> saveLekar(@RequestBody LekarDTO lekarDTO) {

		Lekar lekar = new Lekar();
		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setEmail(lekarDTO.getEmail());
		lekar.setUsername(lekarDTO.getUsername());
		lekar.setPassword(lekarDTO.getPassword());
		lekar.setAdresa(lekarDTO.getAdresa());
		lekar.setGrad(lekarDTO.getGrad());
		lekar.setDrzava(lekarDTO.getDrzava());
		lekar.setBrojTelefona(lekarDTO.getBrojTelefona());
		Integer idK = lekarDTO.getKlinika();
		lekar.setOcena(lekarDTO.getOcena());
		lekar.setTipPregleda(lekarDTO.getTipPregleda());
		RadniKalendarL radniKalendarL = new RadniKalendarL();
		//radniKalendarL.setIdRadnogKalendara(lekarDTO.getRadniKalendarL().getIdRadnogKalendara());
		lekar.setRadniKalendar(radniKalendarL);
		Klinika k = klinikaService.get(idK);

		lekar.setKlinika(k);
		lekar = service.save(lekar);
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}
	
	
	
	
	/*BRISANJE LEKARA SA ODREDJENIM ID*/
	@DeleteMapping(value= "/lekari/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public String deleteLekar(@PathVariable Integer id) {
		
		Lekar lekar = service.get(id);
	
		if (lekar != null) {
			service.delete(id);
			return "Uspesno obrisana lekara sa id: " + id;
		} 
		else {
			return "Lekar sa id: " + id + " nije pronadjen"; //ne prikazuje poruku kad se stavi id koji ne postoji
		}
	}
}
