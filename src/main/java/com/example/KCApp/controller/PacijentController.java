package com.example.KCApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.PacijentDTO;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.repository.PacijentRepository;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.ZdravstveniKartonService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class PacijentController {

	@Autowired
	private PacijentService service;
	@Autowired
	private ZdravstveniKartonService serviceZK;
	
	
	@Autowired
	private PacijentRepository repository;
	
	/*PRIKAZ PACIJENTA PO ID-u*/
	@GetMapping(value = "/pacijenti/{idKorisnika}")
	public Pacijent findPacijentById(@PathVariable Integer idKorisnika) {
		Pacijent pacijent = service.get(idKorisnika);
		return pacijent;
	}
	
	/*PRETRAGA PACIJENTA PO KRITERIJUMU - BROJ OSIGURANIKA*/
	@GetMapping(value = "/pacijenti/brojOsiguranika/{brojOsiguranika}")
	public Pacijent findPacijentByBrojOsiguranika(@PathVariable int brojOsiguranika) {
		Pacijent pacijent = service.findByBrojOsiguranika(brojOsiguranika);
		return pacijent;
	}
	
	/*PRIKAZ SVIH PACIJENATA KLINIKE*/
	@GetMapping(value="/pacijenti")
	public List<Pacijent> getAllPacijenti(Model model) {
		List<Pacijent> listaPacijenata = service.listAll();
		model.addAttribute("listaPacijenata", listaPacijenata);
		return listaPacijenata;
	}
	
	/*DODAVANJE PACIJENTA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/pacijenti",consumes = "application/json")
	public ResponseEntity<PacijentDTO> savePacijent(@RequestBody PacijentDTO pacijentDTO) {

		Pacijent pacijent = new Pacijent();
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setLozinka(pacijentDTO.getLozinka());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		pacijent.setBrojOsiguranika(pacijentDTO.getBrojOsiguranika());
		
		
		ZdravstveniKarton zdravstveniKarton = new ZdravstveniKarton();
		zdravstveniKarton.setTezina(pacijentDTO.getZdravstveniKarton().getTezina());
		zdravstveniKarton.setVisina(pacijentDTO.getZdravstveniKarton().getVisina());
		zdravstveniKarton.setDioptrija(pacijentDTO.getZdravstveniKarton().getDioptrija());
		zdravstveniKarton.setKrvnaGrupa(pacijentDTO.getZdravstveniKarton().getKrvnaGrupa());
		pacijent.setZdravstveniKarton(zdravstveniKarton);

		pacijent = service.save(pacijent);
		zdravstveniKarton = serviceZK.save(zdravstveniKarton);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}
	
	/*UPDATE PACIJENTA*/
	@PutMapping(value="/pacijenti/{idKorisnika}", consumes = "application/json")
	public Pacijent updatePacijent(@PathVariable Integer idKorisnika, @Valid @RequestBody PacijentDTO pacijentUpdated) throws NotFoundException {
		return repository.findById(idKorisnika)
				.map(pacijent->{
					//klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
					pacijent.setIme(pacijentUpdated.getIme());
					pacijent.setPrezime(pacijentUpdated.getPrezime());
					pacijent.setLozinka(pacijentUpdated.getLozinka());
					pacijent.setAdresa(pacijentUpdated.getAdresa());
					pacijent.setGrad(pacijentUpdated.getGrad());
					pacijent.setDrzava(pacijentUpdated.getDrzava());
					pacijent.setBrojTelefona(pacijentUpdated.getBrojTelefona());
					return repository.save(pacijent);
				}).orElseThrow(() -> new NotFoundException("Pacijent not found with id " + idKorisnika));
		
	}
}
