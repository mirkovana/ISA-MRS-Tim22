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
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.beans.ZahtevZaOperaciju;
import com.example.KCApp.beans.ZahtevZaPregled;
import com.example.KCApp.repository.SalaRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.EmailService;
import com.example.KCApp.service.KlinikaService;
import com.example.KCApp.service.OperacijaService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.SalaService;
import com.example.KCApp.service.ZahtevZaOperacijuService;
import com.example.KCApp.service.ZahtevZaPregledService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/api")
public class SalaController {

	@Autowired
	private SalaService service;

	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private PregledService pregledService;

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	private SalaRepository repository;

	@Autowired
	private AdministratorKlinikeService serviceAK;

	@Autowired
	private ZahtevZaPregledService zzpService;

	@Autowired
	private ZahtevZaOperacijuService zzoService;
	@Autowired
	private EmailService emailService;
	
	/* ISPISIVANJE SALA */
	@GetMapping(value = "/sale")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Sala> getAllSale(Model model) {
		List<Sala> listaSala = service.listAll();
		model.addAttribute("listaSala", listaSala);
		return listaSala;
	}

	/* BRISANJE SALE SA ODREDJENIM ID */
	@DeleteMapping(value = "/sale/brisanje/{idSale}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Sala> deleteSala(@PathVariable Integer idSale) {
		System.out.println("USLO U BRISANJE");

		Sala sala = service.get(idSale);
		List<Sala> sale = service.listAll();
		List<Pregled> pregledi = pregledService.listAll();
		List<Operacija> operacije = operacijaService.listAll();
		List<Pregled> pomocna = new ArrayList<Pregled>();
		List<Operacija> pomocna2 = new ArrayList<Operacija>();
		Date date = new Date();

		/*
		 * for (Pregled p : pregledi) { if (p.getSala() == sala && p.getPacijent() !=
		 * null) { if (p.getVreme().after(date)){ System.out.println("SALA ZA BRISANJE"
		 * + sala.getNazivSale()); sale.remove(sala); service.delete(idSale); } } }
		 */

		for (Pregled p : pregledi) {
			if (p.getSala() == sala) {
				pomocna.add(p);
			}
		}

		for (Operacija o : operacije) {
			if (o.getSala() == sala) {
				pomocna2.add(o);
			}
		}
		if (pomocna.isEmpty() && pomocna2.isEmpty()) {
			sale.remove(sala);
			service.delete(idSale);
		}

		System.out.println("SALE" + sale);
		return sale;
	}

	/* ZA ADMINA - PRIKAZ SVIH SALA PO KRITERIJUMU - NAZIV,BROJ */
	@GetMapping(value = "/sale/adminK/{idAdmina}/{naziv}/{broj}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Sala> findAllSalaByNB(@PathVariable Integer idAdmina, @PathVariable String naziv,
			@PathVariable int broj) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<Sala> sale = service.listAll();
		List<Sala> saleKlinike = new ArrayList<Sala>();
		for (Sala s : sale) {
			if (s.getKlinika() == k && s.getNazivSale().equals(naziv) && s.getBrojSale() == broj) {
				saleKlinike.add(s);
			}
		}
		if (saleKlinike.isEmpty()) {
			return saleKlinike;
		}
		return saleKlinike;
	}

	/* ZA ADMINA - PRIKAZ SVIH SALA PO KRITERIJUMU - BROJ */
	@GetMapping(value = "/sale/adminK/{idAdmina}/broj/{broj}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Sala> findAllSalaByB(@PathVariable Integer idAdmina, @PathVariable int broj) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<Sala> sale = service.listAll();
		List<Sala> saleKlinike = new ArrayList<Sala>();
		for (Sala s : sale) {
			if (s.getKlinika() == k && s.getBrojSale() == broj) {
				saleKlinike.add(s);
			}
		}
		if (saleKlinike.isEmpty()) {
			return saleKlinike;
		}
		return saleKlinike;
	}

	/* ZA ADMINA - PRIKAZ SVIH SALA PO KRITERIJUMU - NAZIV */
	@GetMapping(value = "/sale/adminK/{idAdmina}/naziv/{naziv}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Sala> findAllSalaByN(@PathVariable Integer idAdmina, @PathVariable String naziv) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<Sala> sale = service.listAll();
		List<Sala> saleKlinike = new ArrayList<Sala>();
		for (Sala s : sale) {
			if (s.getKlinika() == k && s.getNazivSale().equals(naziv)) {
				saleKlinike.add(s);
			}
		}
		if (saleKlinike.isEmpty()) {
			return saleKlinike;
		}
		return saleKlinike;
	}

	/* PRETRAGA SALE PO KRITERIJUMU - BROJ SALE */
	@GetMapping(value = "/sale/brojSale/{brojSale}")
	@PreAuthorize("hasRole('ADMINK')")
	public Sala findSalaByBrojSale(@PathVariable int brojSale) { // da li treba i po nazivu?
		Sala sala = service.findByBrojSale(brojSale);
		return sala;
	}

	/* PRETRAGA SALE PO KRITERIJUMU - ID SALE */
	@GetMapping(value = "/sale/idSale/{idSale}")
	@PreAuthorize("hasRole('ADMINK')")
	public Sala findSalaById(@PathVariable int idSale) { // da li treba i po nazivu?
		Sala sala = service.get(idSale);
		return sala;
	}

	// prikaz sala klinike admina
	@GetMapping(value = "/sale/admink/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Sala> findAllSalaByIdKlinikeIzvestaj(@PathVariable Integer idAdmina) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<Sala> sale = service.listAll();
		List<Sala> saleKlinike = new ArrayList<Sala>();
		for (Sala s : sale) {
			if (s.getKlinika() == k) {
				saleKlinike.add(s);
			}
		}
		return saleKlinike;
	}

	/* DODAVANJE SALA */ // prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon
							// dodavanja za zdravstveni karton stavi da je null
	@PostMapping(value = "/sale/{idAdmina}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<SalaDTO> saveSala(@RequestBody SalaDTO salaDTO, @PathVariable Integer idAdmina) {

		Sala sala = new Sala();
		sala.setBrojSale(salaDTO.getBrojSale());
		sala.setNazivSale(salaDTO.getNazivSale());
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		sala.setKlinika(k);
		sala = service.save(sala);
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}

	/* UPDATE SALE */
	@PutMapping(value = "/sale/izmena/{idSale}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public Sala updateSala(@PathVariable Integer idSale, @Valid @RequestBody SalaDTO salaUpdated)
			throws NotFoundException {
		return repository.findById(idSale).map(sala -> {
			sala.setNazivSale(salaUpdated.getNazivSale());
			sala.setBrojSale(salaUpdated.getBrojSale());
			return repository.save(sala);
		}).orElseThrow(() -> new NotFoundException("Pacijent not found with id " + idSale));

	}

	/* DOBAVI SALE ZA PREGLED I ZADATI DATUM */
	@GetMapping(value = "/slobodneSale/{zahtev}")
	@PreAuthorize("hasRole('ADMINK')")
	public Set<String> dobaviSaleZaDatum(@PathVariable ZahtevZaPregled zahtev) throws ParseException { // da li treba i
																										// po nazivu?
		List<Sala> sveSale = service.listAll(); // sve sale u bazi
		// Klinika k = klinikaService.get(zahtev.getKlinika().getIdKlinike());
		List<Sala> saleKlinike = new ArrayList<Sala>();
		List<String> naziviSala = new ArrayList<String>();
		for (Sala s : sveSale) {
			if (s.getKlinika() == zahtev.getKlinika()) {
				saleKlinike.add(s);

			}
		}
		List<String> moguciTermini = new ArrayList<String>();
		moguciTermini.add("11:00:00.0");
		moguciTermini.add("12:00:00.0");
		moguciTermini.add("13:00:00.0");
		moguciTermini.add("14:00:00.0");
		moguciTermini.add("15:00:00.0");
		moguciTermini.add("16:00:00.0");
		DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		List<Pregled> sviPregledi = pregledService.findAllByKlinika(zahtev.getKlinika());
		Date datumPregleda = new Date();
		datumPregleda = formatter2.parse(zahtev.getVreme());
		// System.out.println("OVAJ NE KONV" + zahtev.getVreme());

		List<Pregled> preglediDatum = new ArrayList<Pregled>();
		for (Pregled p : sviPregledi) {
			// System.out.println("DATUM PREGLEDA " + datumPregleda);

			if (zahtev.getVreme().equals(p.getVreme().toString().split(" ")[0])) {

				preglediDatum.add(p);
			}
		}
		List<String> pomocna = new ArrayList<String>();
		for (String term : moguciTermini) {

			pomocna.add(term);
		}

		if (preglediDatum.isEmpty()) {
			for (Sala s : saleKlinike) {
				for (String term : moguciTermini) {
					naziviSala.add(s.getNazivSale() + "-" + term);
				}
			}
		} else {
			for (Pregled pp : preglediDatum) {
				for (Sala s : saleKlinike) {
					if (s.equals(pp.getSala())) {
						System.out.println("USAO NEKAD");
						if (pomocna.contains((pp.getVreme().toString().split(" ")[1]))) {
							pomocna.remove(pp.getVreme().toString().split(" ")[1]);
						}

					} else {
						for (String term : moguciTermini) {

							naziviSala.add(s.getNazivSale() + "-" + term);
						}
					}

				}

			}

			for (Pregled pp : preglediDatum) {
				for (Sala s : saleKlinike) {
					if (s.equals(pp.getSala())) {

						for (String p1 : pomocna) {
							System.out.println(p1);
							naziviSala.add(s.getNazivSale() + "-" + p1);
						}
					}
				}
			}
		}

		// proveri salu i proveri satnicu
		Set<String> set1 = new HashSet<String>();
		set1.addAll(naziviSala);
		for (String s : set1) {
			System.out.println(s);
		}
		return set1;
	}

	@PostMapping(value = "/potvrdiSalu/{idZahteva}/{slobodneSale}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<?> dodajPregled(@PathVariable Integer idZahteva, @PathVariable String slobodneSale)
			throws ParseException {

		String nazivSale = slobodneSale.split("-")[0];
		String vreme = slobodneSale.split("-")[1];
		ZahtevZaPregled z = zzpService.get(idZahteva);
		Pregled p = new Pregled();
		Sala sala = service.findByNazivSale(nazivSale);
		p.setCena((int) z.getCena());
		p.setKlinika(z.getKlinika());
		p.setLekar(z.getLekar());
		p.setPacijent(z.getPacijent());
		p.setSala(sala);
		String datumVreme = z.getVreme() + " " + vreme;
		Date dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datumVreme);
		p.setVreme(dd);
		p.setTipPregleda(z.getTipPregleda());
		pregledService.save(p);
		zzpService.delete(idZahteva);
        emailService.slanjePacijentuOdobrenPregled(p);
        emailService.slanjeLekaruOdobrenPregled(p);
		
		return null;

	}
	
	
	
	/* DOBAVI SALE ZA OPERACIJU I ZADATI DATUM */
	@GetMapping(value = "/slobodneSaleOP/{zahtev}")
	@PreAuthorize("hasRole('ADMINK')")
	public Set<String> dobaviSaleZaDatumOP(@PathVariable ZahtevZaOperaciju zahtev) throws ParseException { // da li treba i
																										// po nazivu?
		List<Sala> sveSale = service.listAll(); // sve sale u bazi
		// Klinika k = klinikaService.get(zahtev.getKlinika().getIdKlinike());
		List<Sala> saleKlinike = new ArrayList<Sala>();
		List<String> naziviSala = new ArrayList<String>();
		for (Sala s : sveSale) {
			if (s.getKlinika() == zahtev.getKlinika()) {
				saleKlinike.add(s);

			}
		}
		List<String> moguciTermini = new ArrayList<String>();
		moguciTermini.add("11:00:00.0");
		moguciTermini.add("12:00:00.0");
		moguciTermini.add("13:00:00.0");
		moguciTermini.add("14:00:00.0");
		moguciTermini.add("15:00:00.0");
		moguciTermini.add("16:00:00.0");
		DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		List<Operacija> sviPregledi = operacijaService.findAllByKlinika(zahtev.getKlinika());
		Date datumPregleda = new Date();
		datumPregleda = formatter2.parse(zahtev.getVreme());
		// System.out.println("OVAJ NE KONV" + zahtev.getVreme());

		List<Operacija> preglediDatum = new ArrayList<Operacija>();
		for (Operacija p : sviPregledi) {
			// System.out.println("DATUM PREGLEDA " + datumPregleda);

			if (zahtev.getVreme().equals(p.getVremeOperacije().toString().split(" ")[0])) {

				preglediDatum.add(p);
			}
		}
		List<String> pomocna = new ArrayList<String>();
		for (String term : moguciTermini) {

			pomocna.add(term);
		}

		if (preglediDatum.isEmpty()) {
			for (Sala s : saleKlinike) {
				for (String term : moguciTermini) {
					naziviSala.add(s.getNazivSale() + "-" + term);
				}
			}
		} else {
			for (Operacija pp : preglediDatum) {
				for (Sala s : saleKlinike) {
					if (s.equals(pp.getSala())) {
						System.out.println("USAO NEKAD");
						if (pomocna.contains((pp.getVremeOperacije().toString().split(" ")[1]))) {
							pomocna.remove(pp.getVremeOperacije().toString().split(" ")[1]);
						}

					} else {
						for (String term : moguciTermini) {

							naziviSala.add(s.getNazivSale() + "-" + term);
						}
					}

				}

			}

			for (Operacija pp : preglediDatum) {
				for (Sala s : saleKlinike) {
					if (s.equals(pp.getSala())) {

						for (String p1 : pomocna) {
							System.out.println(p1);
							naziviSala.add(s.getNazivSale() + "-" + p1);
						}
					}
				}
			}
		}

		// proveri salu i proveri satnicu
		Set<String> set1 = new HashSet<String>();
		set1.addAll(naziviSala);
		for (String s : set1) {
			System.out.println(s);
		}
		return set1;
	}
	@PostMapping(value = "/potvrdiSaluOP/{idZahteva}/{slobodneSale}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public ResponseEntity<?> dodajPregledOP(@PathVariable Integer idZahteva, @PathVariable String slobodneSale)
			throws ParseException {

		String nazivSale = slobodneSale.split("-")[0];
		String vreme = slobodneSale.split("-")[1];
		ZahtevZaOperaciju z = zzoService.get(idZahteva);
		Operacija p = new Operacija();
		Sala sala = service.findByNazivSale(nazivSale);
		
		p.setKlinika(z.getKlinika());
		p.setLekar(z.getLekar());
		p.setPacijent(z.getPacijent());
		p.setSala(sala);
		p.setDodatneInfoOOperaciji(z.getDodatneInfoOOperaciji());
		String datumVreme = z.getVreme() + " " + vreme;
		Date dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datumVreme);
		p.setVremeOperacije(dd);
		
		operacijaService.save(p);
		emailService.slanjePacijentuOdobrenaOperacija(p);
		emailService.slanjeLekaruOdobrenaOperacija(p);
		zzoService.delete(idZahteva);

		return null;

	}
}
