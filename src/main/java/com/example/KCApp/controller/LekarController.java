package com.example.KCApp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.RadniKalendarL;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.repository.LekarRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.AuthorityService;
import com.example.KCApp.service.KlinikaService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.OperacijaService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.ZahtevOdsustvaService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class LekarController {
	
	@Autowired
	private LekarService service;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private AdministratorKlinikeService serviceAK;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private ZahtevOdsustvaService zzoService;
	
	@Autowired
	private LekarRepository repository;
	
	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	
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
	public List<Klinika> findAllLekarByTipPregleda(@PathVariable TipPregleda tipPregleda) {
		List<Lekar> lekari = service.findAllByTipPregleda(tipPregleda);
		List<Klinika> klinike = new ArrayList<Klinika>();
		for(Lekar l : lekari)
		{
		    Klinika k = klinikaService.get(l.getKlinika().getIdKlinike());
		    klinike.add(k);
		}
		return klinike;
	}
	
	
	/*DODAVANJE LEKARA*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value = "/lekari/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<?> saveLekar(@RequestBody LekarDTO lekarDTO, @PathVariable Integer idAdmina) {

		Lekar lekar = new Lekar();
		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setEmail(lekarDTO.getEmail());
		lekar.setUsername(lekarDTO.getUsername());
		lekar.setPassword(passwordEncoder.encode(lekarDTO.getPassword()));
		lekar.setAdresa(lekarDTO.getAdresa());
		lekar.setGrad(lekarDTO.getGrad());
		lekar.setDrzava(lekarDTO.getDrzava());
		lekar.setBrojTelefona(lekarDTO.getBrojTelefona());
		
		lekar.setOcena(lekarDTO.getOcena());
		lekar.setTipPregleda(lekarDTO.getTipPregleda());
		RadniKalendarL radniKalendarL = new RadniKalendarL();
		//radniKalendarL.setIdRadnogKalendara(lekarDTO.getRadniKalendarL().getIdRadnogKalendara());
		lekar.setRadniKalendar(radniKalendarL);
		
		AdministratorKlinike ak = serviceAK.get(idAdmina);		
		Klinika k = ak.getKlinika();
		lekar.setKlinika(k);
		
		lekar.setLastPasswordResetDate(lekarDTO.getLastPasswordResetDate());
		lekar.setAuthorities(Arrays.asList(authorityService.findOne(2)));

		lekar = service.save(lekar);
		System.out.println("LEKARRRR"+lekar);
		return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	//IZMENA OCENE
		@PutMapping(value="/lekari/{id}/{ocena}", consumes = "application/json")
		@PreAuthorize("hasRole('PACIJENT')")
		public User updateOcenaLekara(@PathVariable Integer id, @PathVariable double ocena) throws NotFoundException {
			return repository.findById(id)
					.map(user->{
						
						user.prosecnaOcena(ocena);
						return repository.save(user);
					}).orElseThrow(() -> new NotFoundException("Lekar nije pronadjen sa id : " + id));
			
		}
	
	/*BRISANJE LEKARA SA ODREDJENIM ID*/
	@DeleteMapping(value= "/lekari/brisanje/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Lekar> deleteLekar(@PathVariable Integer id) {
		
		Lekar lekar = service.get(id);
		List<Lekar> lekari = service.listAll();
		List<Pregled> pregledi = pregledService.listAll();
		List<Operacija> operacije = operacijaService.listAll();
		List<Pregled> pomocna = new ArrayList<Pregled>();
		List<Operacija> pomocna2 = new ArrayList<Operacija>();
		
		for (Pregled p : pregledi) {
			if (p.getLekar() == lekar) {
				pomocna.add(p);
			}
		}
		
		for (Operacija o : operacije) {
			if (o.getLekar() == lekar) {
				pomocna2.add(o);
			}
		}
		if (pomocna.isEmpty() && pomocna2.isEmpty()) {
			lekari.remove(lekar);
			service.delete(id);
		}
		
		System.out.println("lekari" + lekari);
		return lekari;
	}
	
	/*PRIKAZ LEKARA PO KRITERIJUMU - ID KLINIKE KOJOJ LEKAR PRIPADA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/{tipPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByIdKlinike(@PathVariable Integer idKlinike, @PathVariable TipPregleda tipPregleda) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		for(Lekar l:lekari)
		{
			if(l.getKlinika() == k && l.getTipPregleda() == tipPregleda) {
				lekariKlinike.add(l);
			}
		}
		return lekariKlinike;
	}
	
	/*PRIKAZ LEKARA KLINIKE ADMINA*/
	@GetMapping(value = "/lekari/admink/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Lekar> findAllLekarByIdKlinikeIzvestaj(@PathVariable Integer idAdmina) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		for(Lekar l:lekari)
		{
			if(l.getKlinika() == k) {
				lekariKlinike.add(l);
			}
		}
		return lekariKlinike;
	}
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - ID KLINIKE*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/datum/{datum}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByIdKlinikeSvi(@PathVariable Integer idKlinike, @PathVariable String datum) throws ParseException {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Date datumPregleda = new Date();
		datumPregleda = formatter2.parse(datum);
		List<ZahtevOdsustva> zzo = zzoService.listAll();
		for(ZahtevOdsustva zo : zzo) {
			for(Lekar l:lekari)
			{
				if(zo.getUser().getId().equals(l.getId())) {
					if(l.getKlinika() == k && !(datumPregleda.compareTo(zo.getDatumPocetka())>0 && datumPregleda.compareTo(zo.getDatumKraja())<0)) {
						lekariKlinike.add(l);
					}
				}else {
					if(l.getKlinika() == k) {
						lekariKlinike.add(l);
					}
				}
			}
		}
		
		return lekariKlinike;
	}
	// && !(datumPregleda.compareTo(zo.getDatumPocetka())>0 && datumPregleda.compareTo(zo.getDatumKraja())<0)
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - IME,PREZIME,OCENA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/{ime}/{prezime}/{ocena}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByIPO(@PathVariable Integer idKlinike, @PathVariable String ime, @PathVariable String prezime, @PathVariable int ocena) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getIme().equals(ime) && l.getPrezime().equals(prezime)) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*ZA ADMINA - PRIKAZ SVIH LEKARA PO KRITERIJUMU - IME,PREZIME*/
	@GetMapping(value = "/lekari/adminK/{idAdmina}/{ime}/{prezime}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Lekar> findAllLekarByIP(@PathVariable Integer idAdmina, @PathVariable String ime, @PathVariable String prezime) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);		
		Klinika k = ak.getKlinika();
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getIme().equals(ime) && l.getPrezime().equals(prezime)) {
				lekariKlinike.add(l);
			}
		}
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		System.out.println("LEKARIII KLINIKEEE"+lekariKlinike);
		return lekariKlinike;
	}
	
	/*ZA ADMINA - PRIKAZ SVIH LEKARA PO KRITERIJUMU - PREZIME*/
	@GetMapping(value = "/lekari/adminK/{idAdmina}/prezime/{prezime}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Lekar> findAllLekarByP(@PathVariable Integer idAdmina, @PathVariable String prezime) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);		
		Klinika k = ak.getKlinika();
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getPrezime().equals(prezime)) {
				lekariKlinike.add(l);
			}
		}
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		return lekariKlinike;
	}
	
	/*ZA ADMINA - PRIKAZ SVIH LEKARA PO KRITERIJUMU - IME*/
	@GetMapping(value = "/lekari/adminK/{idAdmina}/ime/{ime}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Lekar> findAllLekarByI(@PathVariable Integer idAdmina, @PathVariable String ime) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);		
		Klinika k = ak.getKlinika();
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getIme().equals(ime)) {
				lekariKlinike.add(l);
			}
		}
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		return lekariKlinike;
	}
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - PREZIME,OCENA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/prezime/{prezime}/{ocena}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByPO(@PathVariable Integer idKlinike, @PathVariable String prezime, @PathVariable int ocena) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getPrezime().equals(prezime)) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - IME,OCENA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/ime/{ime}/{ocena}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByIO(@PathVariable Integer idKlinike, @PathVariable String ime, @PathVariable int ocena) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getIme().equals(ime)) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - OCENA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/ocena/{ocena}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByO(@PathVariable Integer idKlinike, @PathVariable int ocena) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*-----------------------------------------------+TIP PREGLEDA----------------------------------------------------*/
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - IME,PREZIME,OCENA,TIP PREGLEDA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/{ime}/{prezime}/{ocena}/{tipPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByIPOTP(@PathVariable Integer idKlinike, @PathVariable String ime, @PathVariable String prezime, @PathVariable int ocena, @PathVariable TipPregleda tipPregleda) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getIme().equals(ime) && l.getPrezime().equals(prezime) && l.getTipPregleda() == tipPregleda) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - PREZIME,OCENA,TIP PREGLEDA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/prezime/{prezime}/{ocena}/{tipPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByPOTP(@PathVariable Integer idKlinike, @PathVariable String prezime, @PathVariable int ocena, @PathVariable TipPregleda tipPregleda) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getPrezime().equals(prezime) && l.getTipPregleda() == tipPregleda) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - IME,OCENA,TIP PREGLEDA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/ime/{ime}/{ocena}/{tipPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByIOTP(@PathVariable Integer idKlinike, @PathVariable String ime, @PathVariable int ocena, @PathVariable TipPregleda tipPregleda) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getIme().equals(ime) && l.getTipPregleda() == tipPregleda) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*PRIKAZ SVIH LEKARA PO KRITERIJUMU - OCENA,TIP PREGLEDA*/
	@GetMapping(value = "/lekari/klinika/{idKlinike}/ocena/{ocena}/{tipPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByOTP(@PathVariable Integer idKlinike, @PathVariable int ocena, @PathVariable TipPregleda tipPregleda) {
		Klinika k = klinikaService.get(idKlinike);
		List<Lekar> lekari = service.listAll();
		List<Lekar> lekariKlinike = new ArrayList<Lekar>();
		List<Lekar> lekariKlinike1 = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
			if(l.getKlinika() == k && l.getTipPregleda() == tipPregleda) {
				lekariKlinike.add(l);
			}
		}
		
		if(lekariKlinike.isEmpty()) {
			return lekariKlinike;
		}
		
		for(Lekar l : lekariKlinike) {
			if(ocena == 1) {
				if(l.getOcena()>=1 && l.getOcena()<2) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 2) {
				if(l.getOcena()>=2 && l.getOcena()<3) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 3) {
				if(l.getOcena()>=3 && l.getOcena()<4) {
					lekariKlinike1.add(l);
				}
			}else if(ocena == 4) {
				if(l.getOcena()>=4 && l.getOcena()<=5) {
					lekariKlinike1.add(l);
				}
			}
		}
		return lekariKlinike1;
	}
	
	/*FILTRIRANJE LEKARA PO KRITERIJUMU - TIP PREGLEDA*/
	@GetMapping(value = "/lekari/filter/tipPregleda/{tipPregleda}/{idKlinike}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Lekar> findAllLekarByTipPregledaFilter(@PathVariable TipPregleda tipPregleda, @PathVariable Integer idKlinike) {
		List<Lekar> lekari = service.findAllByTipPregleda(tipPregleda);
		List<Lekar> filtriraniLekari = new ArrayList<Lekar>();
		for(Lekar l : lekari)
		{
		   if(l.getKlinika().getIdKlinike() == idKlinike) {
			   filtriraniLekari.add(l);
		   }
		}
		return filtriraniLekari;
	}
	
	//IZMENA PROFILA
	@PutMapping(value="/lekari/izmena/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public User updateAK(@PathVariable Integer id, @Valid @RequestBody LekarDTO lUpdated) throws NotFoundException {
		return repository.findById(id)
				.map(l->{
					//klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
					l.setIme(lUpdated.getIme());
					l.setPrezime(lUpdated.getPrezime());
					
					l.setAdresa(lUpdated.getAdresa());
					l.setGrad(lUpdated.getGrad());
					l.setDrzava(lUpdated.getDrzava());
					l.setBrojTelefona(lUpdated.getBrojTelefona());
					//user.setAuthorities(Arrays.asList(authorityService.findOne(1)));
					return repository.save(l);
				}).orElseThrow(() -> new NotFoundException("Lekar nije pronadjen sa id : " + id));
	}
}
