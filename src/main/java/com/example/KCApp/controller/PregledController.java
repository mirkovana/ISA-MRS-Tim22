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
		
		//Date datum = new SimpleDateFormat("yyyy-MM-dd").parse(pregledDTO.getVreme());
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
	
	/* BROJ PREGLEDA U DANU PO MESECU IZVESTAJ GRAFIK PREGLEDA */
	@GetMapping(value = "/pregledi/izvestaj/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Integer> getSviPreglediZaKlinikuAdmina(Model model, @PathVariable Integer idAdmina) throws ParseException {
		
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		
		List<Pregled> svi = service.listAll();
		List<Pregled> preglediKlinike = new ArrayList<Pregled>();
		
		for (Pregled p : svi) {
			if (k.getIdKlinike().equals(p.getKlinika().getIdKlinike()) && p.getPacijent() != null) {
				preglediKlinike.add(p);
				
			}
		}
		
		System.out.println("PREGLEDI KLINIKE" + preglediKlinike);
		
		List<Integer> lista = new ArrayList<Integer>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		int prvi = 0, drugi = 0, treci = 0, cetvrti = 0, peti = 0, sesti = 0, sedmi = 0, osmi = 0, deveti = 0, deseti = 0, jedanaesti = 0, 
				dvanaesti = 0, trinaesti = 0, cetrnaesti = 0, petnaesti = 0, sesnaesti = 0, sedamnaesti = 0, osamnaesti = 0, devetnaesti = 0, 
				dvadeseti = 0, dvadesetprvi = 0, dvadesetdrugi = 0, dvadesettreci = 0, dvadesetcetvrti = 0, 
				dvadesetpeti = 0, dvadesetsesti = 0, dvadesetsedmi = 0, dvadesetosmi = 0, dvadesetdeveti = 0, trideseti = 0;
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("01/09/2020")) {
				System.out.println("USAO U PRVI");
				prvi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("02/09/2020")) {
				drugi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("03/09/2020")) {
				treci += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("04/09/2020")) {
				cetvrti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("05/09/2020")) {
				System.out.println("USAO U PETI");
				peti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("06/09/2020")) {
				sesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("07/09/2020")) {
				sedmi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("08/09/2020")) {
				osmi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("09/09/2020")) {
				deveti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("10/09/2020")) {
				deseti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("11/09/2020")) {
				jedanaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("12/09/2020")) {
				dvanaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("13/09/2020")) {
				trinaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("14/09/2020")) {
				cetrnaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("15/09/2020")) {
				petnaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("16/09/2020")) {
				sesnaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("17/09/2020")) {
				sedamnaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("18/09/2020")) {
				osamnaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("19/09/2020")) {
				devetnaesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("20/09/2020")) {
				dvadeseti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("21/09/2020")) {
				dvadesetprvi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("22/09/2020")) {
				dvadesetdrugi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("23/09/2020")) {
				dvadesettreci += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("24/09/2020")) {
				dvadesetcetvrti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("25/09/2020")) {
				dvadesetpeti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("26/09/2020")) {
				dvadesetsesti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("27/09/2020")) {
				dvadesetsedmi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("28/09/2020")) {
				dvadesetosmi += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("29/09/2020")) {
				dvadesetdeveti += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			if ((formatter.format(p.getVreme())).equals("30/09/2020")) {
				trideseti += 1;
			}
		}
		
		lista.add(prvi);
		lista.add(drugi);
		lista.add(treci);
		lista.add(cetvrti);
		lista.add(peti);
		lista.add(sesti);
		lista.add(sedmi);
		lista.add(osmi);
		lista.add(deveti);
		lista.add(deseti);
		lista.add(jedanaesti);
		lista.add(dvanaesti);
		lista.add(trinaesti);
		lista.add(cetrnaesti);
		lista.add(petnaesti);
		lista.add(sesnaesti);
		lista.add(sedamnaesti);
		lista.add(osamnaesti);
		lista.add(devetnaesti);
		lista.add(dvadeseti);
		lista.add(dvadesetprvi);
		lista.add(dvadesetdrugi);
		lista.add(dvadesettreci);
		lista.add(dvadesetcetvrti);
		lista.add(dvadesetpeti);
		lista.add(dvadesetsesti);
		lista.add(dvadesetsedmi);
		lista.add(dvadesetosmi);
		lista.add(dvadesetdeveti);
		lista.add(trideseti);
		
		return lista;
	}
	
	/* BROJ PREGLEDA U NEDELJI PO MESECU IZVESTAJ GRAFIK PREGLEDA */
	@GetMapping(value = "/pregledi/izvestajNedelja/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Integer> getSviPreglediZaKlinikuAdminaNedelja(Model model, @PathVariable Integer idAdmina) throws ParseException {
		
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		
		List<Pregled> svi = service.listAll();
		List<Pregled> preglediKlinike = new ArrayList<Pregled>();
		
		for (Pregled p : svi) {
			if (k.getIdKlinike().equals(p.getKlinika().getIdKlinike()) && p.getPacijent() != null) {
				preglediKlinike.add(p);
				
			}
		}
		
		System.out.println("PREGLEDI KLINIKE" + preglediKlinike);
		
		List<Integer> lista = new ArrayList<Integer>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		int prva = 0, druga = 0, treca = 0, cetvrta = 0, peta = 0;
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("30/08/2020")) && p.getVreme().before(formatter.parse("07/09/2020"))) {
				System.out.println("1");
				prva += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("06/09/2020")) && p.getVreme().before(formatter.parse("14/09/2020"))) {
				System.out.println("2");
				druga += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("13/09/2020")) && p.getVreme().before(formatter.parse("21/09/2020"))) {
				System.out.println("3");
				treca += 1;

			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("21/09/2020")) && p.getVreme().before(formatter.parse("28/09/2020"))) {
				System.out.println("4");
				cetvrta += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("27/09/2020")) && p.getVreme().before(formatter.parse("05/10/2020"))) {
				System.out.println("5");
				peta += 1;
			}
		}
		
		
		lista.add(prva);
		lista.add(druga);
		lista.add(treca);
		lista.add(cetvrta);
		lista.add(peta);
		
		return lista;
	}
	
	/* BROJ PREGLEDA U MESECU IZVESTAJ GRAFIK PREGLEDA */
	@GetMapping(value = "/pregledi/izvestajMesec/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Integer> getSviPreglediZaKlinikuAdminaMesec(Model model, @PathVariable Integer idAdmina) throws ParseException {
		
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		
		List<Pregled> svi = service.listAll();
		List<Pregled> preglediKlinike = new ArrayList<Pregled>();
		
		for (Pregled p : svi) {
			if (k.getIdKlinike().equals(p.getKlinika().getIdKlinike()) && p.getPacijent() != null) {
				preglediKlinike.add(p);
				
			}
		}
		
		System.out.println("PREGLEDI KLINIKE" + preglediKlinike);
		
		List<Integer> lista = new ArrayList<Integer>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		int jan = 0, feb = 0, mar = 0, apr = 0, maj = 0, jun = 0, jul = 0, avg = 0, sept = 0, okt = 0, nov = 0, dec = 0;
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("31/12/2019")) && p.getVreme().before(formatter.parse("01/02/2020"))) {
				System.out.println("1");
				jan += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("31/01/2020")) && p.getVreme().before(formatter.parse("01/03/2020"))) {
				System.out.println("2");
				feb += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("30/02/2020")) && p.getVreme().before(formatter.parse("01/04/2020"))) {
				System.out.println("3");
				mar += 1;

			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("31/03/2020")) && p.getVreme().before(formatter.parse("01/05/2020"))) {
				System.out.println("4");
				apr += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("30/04/2020")) && p.getVreme().before(formatter.parse("01/06/2020"))) {
				System.out.println("5");
				maj += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("31/05/2020")) && p.getVreme().before(formatter.parse("01/07/2020"))) {
				System.out.println("6");
				jun += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("30/06/2020")) && p.getVreme().before(formatter.parse("01/08/2020"))) {
				System.out.println("7");
				jul += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("31/07/2020")) && p.getVreme().before(formatter.parse("01/09/2020"))) {
				System.out.println("8");
				avg += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("31/08/2020")) && p.getVreme().before(formatter.parse("01/10/2020"))) {
				System.out.println("9");
				sept += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("30/09/2020")) && p.getVreme().before(formatter.parse("01/11/2020"))) {
				System.out.println("10");
				okt += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("31/10/2020")) && p.getVreme().before(formatter.parse("01/12/2020"))) {
				System.out.println("11");
				nov += 1;
			}
		}
		
		for (Pregled p : preglediKlinike) {
			System.out.println(p.getVreme());
			if (p.getVreme().after(formatter.parse("30/11/2020")) && p.getVreme().before(formatter.parse("01/01/2021"))) {
				System.out.println("12");
				dec += 1;
			}
		}
		
		
		lista.add(jan);
		lista.add(feb);
		lista.add(mar);
		lista.add(apr);
		lista.add(maj);
		lista.add(jun);
		lista.add(jul);
		lista.add(avg);
		lista.add(sept);
		lista.add(okt);
		lista.add(nov);
		lista.add(dec);
		
		return lista;
	}
}
