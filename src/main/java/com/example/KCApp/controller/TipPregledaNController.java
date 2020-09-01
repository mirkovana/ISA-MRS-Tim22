package com.example.KCApp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.example.KCApp.DTO.SalaDTO;
import com.example.KCApp.DTO.TipPregledaNDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.beans.TipPregledaN;
import com.example.KCApp.repository.TipPregledaNRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.SalaService;
import com.example.KCApp.service.TipPregledaNService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/api")
public class TipPregledaNController {
	
	@Autowired
	private TipPregledaNService service;
	
	@Autowired 
	private AdministratorKlinikeService serviceAK;
	
	@Autowired
	private TipPregledaNRepository repository;
	
	@Autowired
	private PregledService pregledService;
	
	//dodavanje
	@PostMapping(value = "/tipoviPregledaN/dodavanje/{idAdmina}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<?> saveTPN(@RequestBody TipPregledaNDTO tipPregledaNDTO, @PathVariable Integer idAdmina) {

		TipPregledaN tpn = new TipPregledaN();
		tpn.setNazivTPN(tipPregledaNDTO.getNazivTPN());
		System.out.println("NAZIV" + tipPregledaNDTO.getNazivTPN());
		System.out.println("NAZIV" + tpn.getNazivTPN());
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		tpn.setKlinika(k);
		tpn = service.save(tpn);
		System.out.println(tpn.getNazivTPN());
		return new ResponseEntity<>(new TipPregledaNDTO(tpn), HttpStatus.CREATED);
	}
	
	//menjanje
	@PutMapping(value = "/tipoviPregledaN/izmena/{idTPN}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public TipPregledaN updateTPN(@PathVariable Integer idTPN, @Valid @RequestBody TipPregledaNDTO tpnUpdated)
			throws NotFoundException {
		return repository.findById(idTPN).map(tipPregledaN -> {
			tipPregledaN.setNazivTPN(tpnUpdated.getNazivTPN());
			return repository.save(tipPregledaN);
		}).orElseThrow(() -> new NotFoundException("Tip pregleda not found with id " + idTPN));

	}
	
	//brisanje
	@DeleteMapping(value = "/tipoviPregledaN/brisanje/{idTPN}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<TipPregledaN> deleteTPN(@PathVariable Integer idTPN) {
		System.out.println("USLO U BRISANJE");

		TipPregledaN tpn = service.get(idTPN);
		List<TipPregledaN> tipovi = service.listAll();
		
		tipovi.remove(tpn);
		service.delete(idTPN);

		return tipovi;
	}
	
	//prikaz tipova klinike admina
	@GetMapping(value = "/tipoviPregledaN/admink/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<TipPregledaN> findAllTPNByIdKlinike(@PathVariable Integer idAdmina) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<TipPregledaN> tipovi = service.listAll();
		List<TipPregledaN> tpnKlinike = new ArrayList<TipPregledaN>();
		for (TipPregledaN tpn : tipovi) {
			if (tpn.getKlinika() == k) {
				tpnKlinike.add(tpn);
			}
		}
		return tpnKlinike;
	}
	
	//pretraga po idu
	@GetMapping(value = "/tipoviPregledaN/idtpn/{idtpn}")
	@PreAuthorize("hasRole('ADMINK')")
	public TipPregledaN findTPNById(@PathVariable int idtpn) { 
		TipPregledaN tpn = service.get(idtpn);
		return tpn;
	}
	
	/* pretraga po nazivu */
	@GetMapping(value = "/tipoviPregledaN/adminK/{idAdmina}/naziv/{naziv}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<TipPregledaN> findAllTPNByN(@PathVariable Integer idAdmina, @PathVariable String naziv) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<TipPregledaN> tipovi = service.listAll();
		List<TipPregledaN> trazeni = new ArrayList<TipPregledaN>();
		for (TipPregledaN tpn : tipovi) {
			if (tpn.getKlinika() == k && tpn.getNazivTPN().equals(naziv)) {
				trazeni.add(tpn);
			}
		}
		System.out.println("trazeni" + trazeni);
		if (trazeni.isEmpty()) {
			return trazeni;
		}
		return trazeni;
	}
	

}
