package com.example.KCApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.PacijentDTO;
import com.example.KCApp.beans.Lekar;
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
	@GetMapping(value = "/pacijenti/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public Pacijent findPacijentById(@PathVariable Integer id) {
		Pacijent pacijent = service.get(id);
		return pacijent;
	}
	
	/*PRIKAZ PACIJENTA PO IMENU*/
	@GetMapping(value = "/pacijenti/ime/{ime}") //da li je ok i sta je ono model kod prikaza svih i da li treba i po prezimenu?
	@PreAuthorize("hasRole('LEKAR') or hasRole('MS')")
	public List<Pacijent> findPacijentByIme(@PathVariable String ime) {
		List<Pacijent> listaPacijenataPoImenu = service.findAllByIme(ime);
		return listaPacijenataPoImenu;
	}
	
	/*PRIKAZ PACIJENTA PO PREZIMENU*/
	@GetMapping(value = "/pacijenti/prezime/{prezime}")
	@PreAuthorize("hasRole('LEKAR') or hasRole('MS')")
	public List<Pacijent> findPacijentByPrezime(@PathVariable String prezime) {
		List<Pacijent> listaPacijenataPoPrezimenu = service.findAllByPrezime(prezime);
		return listaPacijenataPoPrezimenu;
	}
	
	/*PRETRAGA PACIJENTA PO KRITERIJUMU - BROJ OSIGURANIKA*/
	@GetMapping(value = "/pacijenti/brojOsiguranika/{brojOsiguranika}")
	@PreAuthorize("hasRole('LEKAR') or hasRole('MS')")
	public Pacijent findPacijentByBrojOsiguranika(@PathVariable int brojOsiguranika) {
		Pacijent pacijent = service.findByBrojOsiguranika(brojOsiguranika);
		return pacijent;
	}
	
	/*PRIKAZ SVIH PACIJENATA KLINIKE*/
	@GetMapping(value="/pacijenti")
	@PreAuthorize("hasRole('LEKAR') or hasRole('MS') or hasRole('ADMINK')")
	public List<Pacijent> getAllPacijenti(Model model) {
		List<Pacijent> listaPacijenata = service.listAll();
		model.addAttribute("listaPacijenata", listaPacijenata);
		return listaPacijenata;
	}
	
	/*DODAVANJE PACIJENTA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value= "/pacijenti",consumes = "application/json")
	@PreAuthorize("permitAll()") //inace bi trebao to da radi administrator klinickog centra
	public ResponseEntity<PacijentDTO> savePacijent(@RequestBody PacijentDTO pacijentDTO) {

		Pacijent pacijent = new Pacijent();
		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setEmail(pacijentDTO.getEmail());
		pacijent.setUsername(pacijentDTO.getUsername());
		pacijent.setPassword(pacijentDTO.getPassword());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		pacijent.setBrojOsiguranika(pacijentDTO.getBrojOsiguranika());
		
		
		/*ZdravstveniKarton zdravstveniKarton = new ZdravstveniKarton();
		zdravstveniKarton.setTezina(pacijentDTO.getZdravstveniKarton().getTezina());
		zdravstveniKarton.setVisina(pacijentDTO.getZdravstveniKarton().getVisina());
		zdravstveniKarton.setDioptrija(pacijentDTO.getZdravstveniKarton().getDioptrija());
		zdravstveniKarton.setKrvnaGrupa(pacijentDTO.getZdravstveniKarton().getKrvnaGrupa());
		pacijent.setZdravstveniKarton(zdravstveniKarton);*/

		pacijent = service.save(pacijent);
		//zdravstveniKarton = serviceZK.save(zdravstveniKarton);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.CREATED);
	}
	
	/*UPDATE PACIJENTA*/
	@PutMapping(value="/pacijenti/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public Pacijent updatePacijent(@PathVariable Integer id, @Valid @RequestBody PacijentDTO pacijentUpdated) throws NotFoundException {
		return repository.findById(id)
				.map(pacijent->{
					//klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
					pacijent.setIme(pacijentUpdated.getIme());
					pacijent.setPrezime(pacijentUpdated.getPrezime());
					pacijent.setPassword(pacijentUpdated.getPassword());
					pacijent.setAdresa(pacijentUpdated.getAdresa());
					pacijent.setGrad(pacijentUpdated.getGrad());
					pacijent.setDrzava(pacijentUpdated.getDrzava());
					pacijent.setBrojTelefona(pacijentUpdated.getBrojTelefona());
					return repository.save(pacijent);
				}).orElseThrow(() -> new NotFoundException("Pacijent not found with id " + id));
		
	}
	
	
}
