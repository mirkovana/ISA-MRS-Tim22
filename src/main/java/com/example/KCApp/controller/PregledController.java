package com.example.KCApp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.example.KCApp.DTO.PregledDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.repository.PregledRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.EmailService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.SalaService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/api")
public class PregledController {

	@Autowired
	private PregledService service;

	@Autowired
	private AdministratorKlinikeService serviceAK;

	@Autowired
	private SalaService serviceS;

	@Autowired
	private LekarService serviceL;

	@Autowired
	private PacijentService serviceP;

	@Autowired
	private PregledRepository repository;
	
	@Autowired
	private EmailService emailService;

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

	/* ISPISIVANJE PREGLEDA - ISTORIJA PREGLEDA */
	@GetMapping(value = "/pregledi/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Pregled> getAllPregledi(Model model, @PathVariable Integer id) {
		List<Pregled> listaPregled = service.listAll();
		List<Pregled> listaPregleda = new ArrayList<Pregled>();
		Pacijent p = serviceP.get(id);
		List<Pregled> povratna = service.findAllByPacijent(p);
		Date date = new Date(); 
		for(Pregled pr : povratna) {
			//System.out.println(date.compareTo(pr.getVreme()));
			if(date.compareTo(pr.getVreme())>0) {
				listaPregleda.add(pr);
			}
		}
		
		
		return listaPregleda;
	}
	
	/* ISPISIVANJE PREGLEDA - TRENUTNI PREGLEDI */
	@GetMapping(value = "/pregledi/zakazani/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Pregled> getAllPreglediZakazani(Model model, @PathVariable Integer id) {
		List<Pregled> listaPregled = service.listAll();
		List<Pregled> listaPregleda = new ArrayList<Pregled>();
		Pacijent p = serviceP.get(id);
		List<Pregled> povratna = service.findAllByPacijent(p);
		Date date = new Date(); 
		for(Pregled pr : povratna) {
			//System.out.println(date.compareTo(pr.getVreme()));
			if(date.compareTo(pr.getVreme())<0) {
				listaPregleda.add(pr);
			}
		}
		
		
		return listaPregleda;
	}

	/* ISPISIVANJE PREGLEDA NA OSNOVU ID-A KLINIKE */
	@GetMapping(value = "/pregledi/klinika/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Pregled> getPreglediZaKliniku(Model model, @PathVariable Integer id) {
		List<Pregled> listaPregled = service.listAll();
		List<Pregled> slobodniPregledi = new ArrayList<Pregled>();
		for (Pregled p : listaPregled) {
			if (id.equals(p.getKlinika().getIdKlinike()) && p.getPacijent() == null) {
				System.out.println("AKO POPIZDIM");
				model.addAttribute("listaPregled", listaPregled);
				slobodniPregledi.add(p);
				return slobodniPregledi;
			}
		}
		List<Pregled> prazna = new ArrayList<Pregled>();
		return prazna;
	}
	
	/* ISPISIVANJE PREGLEDA NA OSNOVU ID-A ADMINA */
	@GetMapping(value = "/pregledi/adminK/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Pregled> getPreglediZaKlinikuAdmina(Model model, @PathVariable Integer idAdmina) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<Pregled> listaPregled = service.listAll();
		List<Pregled> slobodniPregledi = new ArrayList<Pregled>();
		for (Pregled p : listaPregled) {
			if (k.getIdKlinike().equals(p.getKlinika().getIdKlinike()) && p.getPacijent() == null) {
				model.addAttribute("listaPregled", listaPregled);
				slobodniPregledi.add(p);
				
			}
		}
	//	List<Pregled> prazna = new ArrayList<Pregled>();
		return slobodniPregledi;
	}

	/* DODAVANJE SLOBODNIH TERMINA PREGLEDA */ // prilikom dodavanja ispise lepo sve informacije, a prilikom
												// izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value = "/pregledi/idAK/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<PregledDTO> savePregled(@RequestBody PregledDTO pregledDTO, @PathVariable Integer id)
			throws ParseException {

		AdministratorKlinike ak = serviceAK.get(id);
		System.out.println("ADMIIINNNN " + ak.getUsername());

		Klinika klinikaAK = ak.getKlinika();

		System.out.println("KLNIKAAAAA " + klinikaAK.getIdKlinike());

		Pregled pregled = new Pregled();

		pregled.setKlinika(klinikaAK);
		System.out.println("PREGLED KLINIKA " + pregled.getKlinika());

		// String sDate1="31/07/2020";
		// Date vreme=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		// pregled.setVreme(vreme);
		pregled.setVreme(pregledDTO.getVreme()); // kako formatirati
		System.out.println("PREGLED VREME " + pregled.getVreme());

		pregled.setTipPregleda(pregledDTO.getTipPregleda());
		System.out.println("PREGLED TIP " + pregled.getTipPregleda());

		pregled.setTrajanje(pregledDTO.getTrajanje());
		System.out.println("PREGLED TRAJANJE " + pregled.getTrajanje());

		Lekar lekar = serviceL.get(pregledDTO.getIdLekara());
		pregled.setLekar(lekar);
		System.out.println("PREGLED LEKAR " + pregled.getLekar());
		System.out.println("PREGLED LEKAR ID " + pregled.getLekar().getId());

		Sala sala = serviceS.get(pregledDTO.getIdSale());
		pregled.setSala(sala);
		System.out.println("PREGLED SALA " + pregled.getSala());
		System.out.println("PREGLED SALA ID " + pregled.getSala().getIdSale());

		pregled.setCena(pregledDTO.getCena());
		System.out.println("PREGLED CENA " + pregled.getCena());

		// pregled.setIdPregleda(3);
		// System.out.println("ID PREGLEDA " + pregled.getIdPregleda());

		// System.out.println("PREGLEDDD: " + "klinika" + pregled.getKlinika() + "
		// vreme" + pregled.getVreme() + " tip" + pregled.getTipPregleda() + " tr" +
		// pregled.getTrajanje() + " cena" +pregled.getCena());

		pregled = service.save(pregled);
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.CREATED);
	}

	@PutMapping(value = "/pregledi/{id}/{idPregleda}", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public Pregled updatePregled(@PathVariable Integer id, @PathVariable Integer idPregleda) throws NotFoundException {
		Pacijent p = serviceP.get(id);
		return repository.findById(idPregleda).map(pregled -> {

			pregled.setPacijent(p);
			emailService.zakazanPredefinisan(pregled);;
			return repository.save(pregled);
		}).orElseThrow(() -> new NotFoundException("Pregled nije pronadjen sa id : " + idPregleda));

	}

	/* ISPISIVANJE PREGLEDA NA OSNOVU LEKARA I PACIJENTA */
	@GetMapping(value = "/pregledi/lekar/{idPacijenta}/{idLekara}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pregled> getPreglediZaPacILek(Model model, @PathVariable Integer idPacijenta,
			@PathVariable Integer idLekara) {
		List<Pregled> listaPregled = service.listAll();
		List<Pregled> pregledi = new ArrayList<Pregled>();

		for (Pregled p : listaPregled) {

			if (p.getPacijent() != null && p.getLekar().getId() == idLekara && p.getPacijent().getId() == idPacijenta) {

				model.addAttribute("listaPregled", pregledi);
				pregledi.add(p);

			}
		}

		return pregledi;
	}
	
	/* ISPISIVANJE PREGLEDA LEKAR */
	@GetMapping(value = "/lekar/pregledi/rk/{id}")
	@PreAuthorize("hasRole('LEKAR')")
	public List<Pregled> getAllPreglediLekar(Model model, @PathVariable Integer id) {
		List<Pregled> listaPregled = service.listAll();
		List<Pregled> listaPovratna = new ArrayList<Pregled>();
		for (Pregled p : listaPregled) {
			if (id.equals(p.getLekar().getId())) {
				listaPovratna.add(p);
			
			}
		}
		//List<Pregled> prazna = new ArrayList<Pregled>();
		return listaPovratna;
	}
	
	@PutMapping(value= "/pregledi/obrisi/{idPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public void deletePregled(@PathVariable Integer idPregleda) {
		service.delete(idPregleda);
	}
}
