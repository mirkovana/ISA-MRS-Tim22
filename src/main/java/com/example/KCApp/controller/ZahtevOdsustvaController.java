package com.example.KCApp.controller;

import java.util.ArrayList;
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

import com.example.KCApp.DTO.SalaDTO;
import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.DTO.ZahtevOdsustvaDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.repository.SalaRepository;
import com.example.KCApp.repository.ZahtevOdsustvaRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.MedicinskaSestraService;
import com.example.KCApp.service.UserService;
import com.example.KCApp.service.ZahtevOdsustvaService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class ZahtevOdsustvaController {

	@Autowired
	private ZahtevOdsustvaService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdministratorKlinikeService serviceAK;
	
	@Autowired
	private LekarService serviceL;
	
	@Autowired
	private MedicinskaSestraService serviceMS;
	
	@Autowired
	private ZahtevOdsustvaRepository repository;
	
	
	@PostMapping(value= "/zahteviOdsustva/{id}",consumes = "application/json")
	@PreAuthorize("hasRole('MS') or hasRole('LEKAR')")
	public ResponseEntity<ZahtevOdsustvaDTO> saveZO(@RequestBody ZahtevOdsustvaDTO zahtevOdsustvaDTO, @PathVariable Integer id) {

		
		ZahtevOdsustva zahtev = new ZahtevOdsustva();
		User u = userService.findById(id);
		zahtev.setUser(u);
		zahtev.setDatumKraja(zahtevOdsustvaDTO.getDatumKraja());
		zahtev.setDatumPocetka(zahtevOdsustvaDTO.getDatumPocetka());
		zahtev.setOdobren(false);
		zahtev.setRazlog(zahtevOdsustvaDTO.getRazlog());
		zahtev.setZavrseno(false);
		
		zahtev=service.save(zahtev);
		return new ResponseEntity<>(new ZahtevOdsustvaDTO(zahtev), HttpStatus.CREATED);
		}
	
	/*PRIKAZ SVIH ZAHTEVA KLINIKE ZA ADMINA KLINIKE*/
	@GetMapping(value="/zahteviOdsustva/adminK/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<ZahtevOdsustva> getAllZahteviKlinikeAK(Model model, @PathVariable Integer id) {
		System.out.println("USAO U PRIKAZ ZAHTEVA ADMINA");
		AdministratorKlinike ak = serviceAK.get(id);
		Klinika klinikaAK = ak.getKlinika();
		List<ZahtevOdsustva> sviZahtevi = service.listAll();
		List<ZahtevOdsustva> zahteviAdmina = new ArrayList<ZahtevOdsustva>();
		
		for (ZahtevOdsustva z : sviZahtevi) {
			User u = z.getUser();
			System.out.println("USER" + u);
			if (u.getClass().equals(Lekar.class)) {
				Lekar l = serviceL.get(u.getId());
				System.out.println("LEKAR" + l);
				if (l.getKlinika() == klinikaAK) {
					if (z.isZavrseno() == false) {
						zahteviAdmina.add(z);
						System.out.println("ZAHTEV" + z);
					}
				}
			}
			else if (u.getClass().equals(MedicinskaSestra.class)) {
				MedicinskaSestra ms = serviceMS.get(u.getId());
				System.out.println("MS" + ms);
				if (ms.getKlinika() == klinikaAK) {
					if (z.isZavrseno() == false) {
						zahteviAdmina.add(z);
						System.out.println("ZAHTEV" + z);
					}
				}
			}
		}
		System.out.println("ZAHTEVI" + zahteviAdmina);
		return zahteviAdmina;
	}
	
	/*PRIHVATI*/
	@PutMapping(value="/zahteviOdsustva/prihvati/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ZahtevOdsustva prihvatiZO(@PathVariable Integer id) throws NotFoundException {
		return repository.findById(id)
				.map(zahtev->{
					zahtev.setOdobren(true);
					zahtev.setZavrseno(true);
					return repository.save(zahtev);
				}).orElseThrow(() -> new NotFoundException("Zahtev not found with id " + id));
		
	}
	
	/*ODBIJ*/
	@PutMapping(value="/zahteviOdsustva/odbij/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ZahtevOdsustva odbijZO(@PathVariable Integer id) throws NotFoundException {
		return repository.findById(id)
				.map(zahtev->{
					zahtev.setOdobren(false);
					zahtev.setZavrseno(true);
					return repository.save(zahtev);
				}).orElseThrow(() -> new NotFoundException("Zahtev not found with id " + id));
		
	}
}
