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

import com.example.KCApp.DTO.PacijentDTO;
import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.User;
import com.example.KCApp.repository.PacijentRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.MedicinskaSestraService;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.ZdravstveniKartonService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class PacijentController {

	@Autowired
	private PacijentService service;
	
	@Autowired
	private PregledService servicePregled;
	
	@Autowired
	private MedicinskaSestraService serviceMS;
	@Autowired
	private ZdravstveniKartonService serviceZK;
	
	@Autowired
	private LekarService serviceL;
	
	@Autowired
	private PacijentRepository repository;
	
	@Autowired
	private AdministratorKlinikeService serviceAK;
	
	/*PRIKAZ PACIJENTA PO ID-u*/
	@GetMapping(value = "/pacijenti/{id}")
	@PreAuthorize("hasRole('PACIJENT') or hasRole('LEKAR') or hasRole('MS')")
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
	
	/*PRIKAZ SVIH PACIJENATA KLINIKE ZA MED SESTRU*/
	@GetMapping(value="/pacijenti/ms/{id}")
	@PreAuthorize("hasRole('MS')")
	public List<Pacijent> getAllPacijentiKlinikeMS(Model model, @PathVariable Integer id) {
		MedicinskaSestra ms = serviceMS.get(id);
		Klinika klinikaMS = ms.getKlinika();
		List<Pacijent> listaPacijenata = new ArrayList<Pacijent>();
		//ovde ide sada provera 
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaMS);
		for(Pregled pre:preglediKlinike) {
			listaPacijenata.add(pre.getPacijent());
			model.addAttribute("listaPacijenata", listaPacijenata);
			return listaPacijenata;
		}
		//model.addAttribute("listaPacijenata", listaPacijenata);
		return listaPacijenata;
	}
	
	/*PRIKAZ SVIH PACIJENATA KLINIKE ZA ADMINA KLINIKE*/
	@GetMapping(value="/pacijenti/admink/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Pacijent> getAllPacijentiKlinikeAK(Model model, @PathVariable Integer id) {
		AdministratorKlinike ak = serviceAK.get(id);
		Klinika klinikaAK = ak.getKlinika();
		List<Pacijent> listaPacijenata = new ArrayList<Pacijent>();
		//ovde ide sada provera 
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaAK);
		for(Pregled pre:preglediKlinike) {
			listaPacijenata.add(pre.getPacijent());
			model.addAttribute("listaPacijenata", listaPacijenata);
			return listaPacijenata;
		}
		//model.addAttribute("listaPacijenata", listaPacijenata);
		return listaPacijenata;
	}
	
	/*PRIKAZ SVIH PACIJENATA KLINIKE ZA LEKARA*/
	@GetMapping(value="/pacijenti/lekar/{id}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> getAllPacijentiKlinikeLekara(Model model, @PathVariable Integer id) {
		Lekar l = serviceL.get(id);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> listaPacijenata = new ArrayList<Pacijent>();
		//ovde ide sada provera 
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre:preglediKlinike) {
			listaPacijenata.add(pre.getPacijent());
			model.addAttribute("listaPacijenata", listaPacijenata);
			return listaPacijenata;
		}
		//model.addAttribute("listaPacijenata", listaPacijenata);
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
	
	//IZMENA PROFILA
	@PutMapping(value="/pacijenti/izmena/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public User updatePacijent(@PathVariable Integer id, @Valid @RequestBody UserDTO pacijentUpdated) throws NotFoundException {
		return repository.findById(id)
				.map(user->{
					user.setIme(pacijentUpdated.getIme());
					user.setPrezime(pacijentUpdated.getPrezime());
					user.setAdresa(pacijentUpdated.getAdresa());
					user.setGrad(pacijentUpdated.getGrad());
					user.setDrzava(pacijentUpdated.getDrzava());
					user.setBrojTelefona(pacijentUpdated.getBrojTelefona());
					return repository.save(user);
				}).orElseThrow(() -> new NotFoundException("Pacijent nije pronadjen sa id : " + id));
		
	}
	
	/*PRIKAZ SVIH PACIJENATA PO KRITERIJUMU - IME,PREZIME*/
	@GetMapping(value = "/pacijenti/lekar/{idLekara}/{ime}/{prezime}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> findAllPacijentByIP(@PathVariable Integer idLekara, @PathVariable String ime, @PathVariable String prezime) {
		//dobavim pacijente klinike kojoj lekar pripada
		Lekar l = serviceL.get(idLekara);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> pacijentiKlinike = new ArrayList<Pacijent>();
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre : preglediKlinike) {
			if (pre.getPacijent() != null) {
				pacijentiKlinike.add(pre.getPacijent());
			}
		}
		//pretrazujem po imenu i prezimenu iz nje
		List<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentiKlinike)
		{
			if(p.getIme().equals(ime) && p.getPrezime().equals(prezime)) {
				pacijenti.add(p);
			}
		}
		if(pacijenti.isEmpty()) {
			return pacijenti;
		}
		return pacijenti;
	}
	
	/*PRIKAZ SVIH PACIJENATA PO KRITERIJUMU - PREZIME*/
	@GetMapping(value = "/pacijenti/lekar/{idLekara}/prezime/{prezime}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> findAllPacijentByP(@PathVariable Integer idLekara, @PathVariable String prezime) {
		//dobavim pacijente klinike kojoj lekar pripada
		Lekar l = serviceL.get(idLekara);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> pacijentiKlinike = new ArrayList<Pacijent>();
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre : preglediKlinike) {
			if (pre.getPacijent() != null) {
				pacijentiKlinike.add(pre.getPacijent());
			}
		}
		//pretrazujem po imenu i prezimenu iz nje
		List<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentiKlinike)
		{
			if(p.getPrezime().equals(prezime)) {
				pacijenti.add(p);
			}
		}
		if(pacijenti.isEmpty()) {
			return pacijenti;
		}
		return pacijenti;
	}
	
	/*PRIKAZ SVIH PACIJENATA PO KRITERIJUMU - IME*/
	@GetMapping(value = "/pacijenti/lekar/{idLekara}/ime/{ime}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> findAllPacijentByI(@PathVariable Integer idLekara, @PathVariable String ime) {
		System.out.println("USAO U PRETRAGU PO IMENU");
		//dobavim pacijente klinike kojoj lekar pripada
		Lekar l = serviceL.get(idLekara);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> pacijentiKlinike = new ArrayList<Pacijent>();
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre : preglediKlinike) {
			if (pre.getPacijent() != null) {
				pacijentiKlinike.add(pre.getPacijent());
			}
		}
		System.out.println("PACIJENTI KLINIKE" + pacijentiKlinike);
		
		//pretrazujem po imenu i prezimenu iz nje
		List<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentiKlinike)
		{
			if(p.getIme().equals(ime)) {
				pacijenti.add(p);
			}
		}
		System.out.println("NADJENI PACIJENTI" + pacijenti);
		if(pacijenti.isEmpty()) {
			return pacijenti;
		}
		return pacijenti;
	}
	
	/*PRIKAZ SVIH PACIJENATA PO KRITERIJUMU - BR OS*/
	@GetMapping(value = "/pacijenti/lekar/{idLekara}/brO/{brO}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> findAllPacijentByBrO(@PathVariable Integer idLekara, @PathVariable int brO) {
		//dobavim pacijente klinike kojoj lekar pripada
		Lekar l = serviceL.get(idLekara);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> pacijentiKlinike = new ArrayList<Pacijent>();
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre : preglediKlinike) {
			if (pre.getPacijent() != null) {
				pacijentiKlinike.add(pre.getPacijent());
			}
		}
		//pretrazujem po imenu i prezimenu iz nje
		List<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentiKlinike)
		{
			if(p.getBrojOsiguranika() == brO) {
				pacijenti.add(p);
			}
		}
		if(pacijenti.isEmpty()) {
			return pacijenti;
		}
		return pacijenti;
	}
	
	/*PRIKAZ SVIH PACIJENATA PO KRITERIJUMU - IME, BR OS*/
	@GetMapping(value = "/pacijenti/lekar/{idLekara}/ime/{ime}/brO/{brO}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> findAllPacijentByIbrO(@PathVariable Integer idLekara, @PathVariable String ime, @PathVariable int brO) {
		//dobavim pacijente klinike kojoj lekar pripada
		Lekar l = serviceL.get(idLekara);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> pacijentiKlinike = new ArrayList<Pacijent>();
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre : preglediKlinike) {
			if (pre.getPacijent() != null) {
				pacijentiKlinike.add(pre.getPacijent());
			}
		}
		//pretrazujem po imenu i prezimenu iz nje
		List<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentiKlinike)
		{
			if(p.getIme().equals(ime) && p.getBrojOsiguranika() == brO) {
				pacijenti.add(p);
			}
		}
		if(pacijenti.isEmpty()) {
			return pacijenti;
		}
		return pacijenti;
	}
	
	/*PRIKAZ SVIH PACIJENATA PO KRITERIJUMU - PREZIME, BR OS*/
	@GetMapping(value = "/pacijenti/lekar/{idLekara}/prezime/{prezime}/brO/{brO}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> findAllPacijentByPbrO(@PathVariable Integer idLekara, @PathVariable String prezime, @PathVariable int brO) {
		//dobavim pacijente klinike kojoj lekar pripada
		Lekar l = serviceL.get(idLekara);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> pacijentiKlinike = new ArrayList<Pacijent>();
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre : preglediKlinike) {
			if (pre.getPacijent() != null) {
				pacijentiKlinike.add(pre.getPacijent());
			}
		}
		//pretrazujem po imenu i prezimenu iz nje
		List<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentiKlinike)
		{
			if(p.getPrezime().equals(prezime) && p.getBrojOsiguranika() == brO) {
				pacijenti.add(p);
			}
		}
		if(pacijenti.isEmpty()) {
			return pacijenti;
		}
		return pacijenti;
	}
	
	/*PRIKAZ SVIH PACIJENATA PO KRITERIJUMU - IME, PREZIME, BR OS*/
	@GetMapping(value = "/pacijenti/lekar/{idLekara}/ime/{ime}/prezime/{prezime}/brO/{brO}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pacijent> findAllPacijentByIbrO(@PathVariable Integer idLekara, @PathVariable String ime, @PathVariable String prezime, @PathVariable int brO) {
		//dobavim pacijente klinike kojoj lekar pripada
		Lekar l = serviceL.get(idLekara);
		Klinika klinikaL = l.getKlinika();
		List<Pacijent> pacijentiKlinike = new ArrayList<Pacijent>();
		List<Pregled> preglediKlinike = servicePregled.findAllByKlinika(klinikaL);
		for(Pregled pre : preglediKlinike) {
			if (pre.getPacijent() != null) {
				pacijentiKlinike.add(pre.getPacijent());
			}
		}
		//pretrazujem po imenu i prezimenu iz nje
		List<Pacijent> pacijenti = new ArrayList<Pacijent>();
		for(Pacijent p : pacijentiKlinike)
		{
			if(p.getIme().equals(ime) && p.getBrojOsiguranika() == brO && p.getPrezime().equals(prezime)) {
				pacijenti.add(p);
			}
		}
		if(pacijenti.isEmpty()) {
			return pacijenti;
		}
		return pacijenti;
	}
}
