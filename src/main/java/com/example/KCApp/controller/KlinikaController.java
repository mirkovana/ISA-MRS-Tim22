package com.example.KCApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.KCApp.DTO.KlinikaDTO;
import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.KlinickiCentar;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Ocena;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.User;
import com.example.KCApp.repository.KlinikaRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.CenovnikService;
import com.example.KCApp.service.KlinickiCentarService;
import com.example.KCApp.service.KlinikaService;
import com.example.KCApp.service.MedicinskaSestraService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/api")
public class KlinikaController {

	@Autowired
	private KlinikaService service;

	@Autowired
	private AdministratorKlinikeService serviceAK;

//	@Autowired(required=false)
//	private CenovnikService serviceCenovnik;
//	
	@Autowired
	private KlinikaRepository repository;

	@Autowired
	private KlinickiCentarService kcService;

	/* PRIKAZ KLINIKE ADMINA KLINIKE */
	@GetMapping(value = "/klinike/admink/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public Klinika getKlinikaAdminaK(Model model, @PathVariable Integer id) {
		AdministratorKlinike ak = serviceAK.get(id);
		Klinika klinikaAK = ak.getKlinika();
		return klinikaAK;
	}

	/* ISPISIVANJE KLINIKA */
	@GetMapping(value = "/klinike")
	@PreAuthorize("hasRole('ADMINKC') or hasRole('PACIJENT')")
	public List<Klinika> getAllKlinike(Model model) {
		List<Klinika> listaKlinika = service.listAll();
		model.addAttribute("listaKlinika", listaKlinika);
		return listaKlinika;
	}

	/* BRISANJE KLINIKE SA ODREDJENIM ID */
	@DeleteMapping(value = "/klinike/{idKlinike}")
	@PreAuthorize("hasRole('ADMINKC')")
	public String deleteKlinika(@PathVariable Integer idKlinike) {

		Klinika klinika = service.get(idKlinike);

		if (klinika != null) {
			service.delete(idKlinike);
			return "Uspesno obrisana klinika sa id: " + idKlinike;
		} else {
			return "Klinika sa id: " + idKlinike + " nije pronadjena"; // ne prikazuje poruku kad se stavi id koji ne
																		// postoji
		}
	}

	@PostMapping(value = "/klinike", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINKC')")
	public ResponseEntity<KlinikaDTO> saveKlinika(@RequestBody KlinikaDTO klinikaDTO) {

		// MILAN: Povezale ste cenovnik sa klinikom, sto znaci da ne mozete imati
		// cenovnik te klinike pre nego sto kreirate kliniku te je ova linija ispod koja
		// je zakomentarisana nevalidna.
		// potrebno je prvo kreirati kliniku na osnovu DTO, a zatim odmah i cenovnik te
		// klinike.
		// Cenovnik cenovnik =
		// serviceCenovnik.get(klinikaDTO.getCenovnik().getIdCenovnika());
		Klinika klinika = new Klinika();
		// MILAN: ne mozete preuzimati ID klinike koja jos uvek ne postoji. Definisale
		// ste sekvencu za svaki entitet u beans paketu i time rekli da
		// korisnik kada zeli da sacuva novu torku, moze da prosledi sve podatke sem ID
		// jer ce njega tek kada se pozove metoda save kreirati baza.
		// klinika.setIdKlinike(klinikaDTO.getIdKlinike());
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setGrad(klinikaDTO.getGrad());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setOcena(klinikaDTO.getOcena());

//		//MILAN: u modelu ste rekle da su klinika i cenovnik povezane i ovde se onda mora kreirati cenovnik i uvezati sa tom klinikom.
//		Cenovnik cenovnik1 = new Cenovnik();
//		cenovnik1.setTipPregledaCenovnik(klinikaDTO.getCenovnik().getTipPregledaCenovnik());
//		cenovnik1.setCena(klinikaDTO.getCenovnik().getCena());
//		//MILAN: uvezivanje klinike i cenovnika je dovoljno pozivom settera setCenovnik jer je dodat Cascade ALL u klasi Klinika linija 85.
//		klinika.setCenovnik(cenovnik1);

		// MILAN: ubacili ste skriptom jedan jeidni Klinicki Centar kome pripada cela
		// aplikacija pa samo sa get(1) uvek dobavljate isti
		KlinickiCentar kc = kcService.get(1);

		// MILAN: u kliniku postavljamo jos klinicki centar jer ste u klasi Klinika
		// navele da referenca ne sme biti null
		klinika.setKlinickiCentar(kc);
		// MILAN: cuvamo kliniku i automatski ce se sacuvati i cenovnik i uvezace se
		// reference, tj. strani kljucevi cenovnika i klinickog centra sa klinikom.
		klinika = service.save(klinika);
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}

	/* UPDATE */
	@PutMapping(value = "/klinike/{idKlinike}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public Klinika updateKlinika(@PathVariable Integer idKlinike, @Valid @RequestBody KlinikaDTO klinikaUpdated)
			throws NotFoundException {
		return repository.findById(idKlinike).map(klinika -> {
			// klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
			klinika.setAdresa(klinikaUpdated.getAdresa());
			klinika.setGrad(klinikaUpdated.getGrad());
			klinika.setNaziv(klinikaUpdated.getNaziv());
			klinika.setOpis(klinikaUpdated.getOpis());
			klinika.setOcena(klinikaUpdated.getOcena());
			// klinika.setCenovnik(klinikaUpdated.getCenovnik());
			// klinika.setKlinickiCentar(klinikaUpdated.getKlinickiCentar());
			return repository.save(klinika);
		}).orElseThrow(() -> new NotFoundException("Klinika not found with id " + idKlinike));

	}
	
	//IZMENA PROFILA
		@PutMapping(value="/klinike/izmena/{id}", consumes = "application/json")
		@PreAuthorize("hasRole('ADMINK')")
		public Klinika updateKlinika2(@PathVariable Integer id, @Valid @RequestBody KlinikaDTO klinikaUpdated) throws NotFoundException {
			AdministratorKlinike ak = serviceAK.get(id);
			Klinika klinikaAK = ak.getKlinika();
			return repository.findById(klinikaAK.getIdKlinike())
					.map(klinika->{
						klinika.setAdresa(klinikaUpdated.getAdresa());
						//klinika.setGrad(klinikaUpdated.getGrad());
						klinika.setNaziv(klinikaUpdated.getNaziv());
						klinika.setOpis(klinikaUpdated.getOpis());
						return repository.save(klinika);
					}).orElseThrow(() -> new NotFoundException("Klinika nije pronadjen sa id : " + id));
			
		}

	/* PRETRAGA KLINIKA PO KRITERIJUMU - OCENA */
	@GetMapping(value = "/klinike/ocena/{ocena}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Klinika> findAllKlinikaByOcena(@PathVariable Ocena ocena) {
		List<Klinika> klinika = service.findAllByOcena(ocena);
		return klinika;
	}

	/* PRETRAGA KLINIKA PO KRITERIJUMU - GRAD */
	@GetMapping(value = "/klinike/grad/{grad}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Klinika> findAllKlinikaByGrad(@PathVariable String grad) {
		List<Klinika> klinika = service.findAllByGrad(grad);
		return klinika;
	}

	/* PRETRAGA KLINIKA PO KRITERIJUMU - ID */
	@GetMapping(value = "/klinike/id/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public Klinika findKlinikaById(@PathVariable Integer id) {
		Klinika klinika = service.get(id);
		return klinika;
	}

	// IZMENA OCENE
	@PutMapping(value = "/klinike/{id}/{ocena}", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public Klinika updateOcenaKlinike(@PathVariable Integer id, @PathVariable double ocena) throws NotFoundException {
		return repository.findById(id).map(klinika -> {

			klinika.prosecnaOcena(ocena);
			return repository.save(klinika);
		}).orElseThrow(() -> new NotFoundException("Klinika nije pronadjen sa id : " + id));

	}

	/* FILTER KLINIKA PO KRITERIJUMU - NAZIV KLINIKE*/
	@GetMapping(value = "/klinike/filterNaziv/{nazivKlinike}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Klinika> findKlinikaByNaziv(@PathVariable String nazivKlinike) {
		List<Klinika> filtriraneKlinike = new ArrayList<Klinika>();
		List<Klinika> sveKlinike = service.listAll();
		for(Klinika k : sveKlinike) {
			if(k.getNaziv().equalsIgnoreCase(nazivKlinike)) {
				filtriraneKlinike.add(k);
			}
		}
		return filtriraneKlinike;
	}
	
	/* FILTER KLINIKA PO KRITERIJUMU - GRAD KLINIKE*/
	@GetMapping(value = "/klinike/filterGrad/{gradKlinike}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Klinika> findKlinikaByGrad(@PathVariable String gradKlinike) {
		List<Klinika> filtriraneKlinike = new ArrayList<Klinika>();
		List<Klinika> sveKlinike = service.listAll();
		for(Klinika k : sveKlinike) {
			if(k.getGrad().equalsIgnoreCase(gradKlinike)) {
				filtriraneKlinike.add(k);
			}
		}
		return filtriraneKlinike;
	}
	
	/* FILTER KLINIKA PO KRITERIJUMU - NAJMANJA I NAJVECA OCENA KLINIKE*/
	@GetMapping(value = "/klinike/filterOcena/obe/{ocenaNajmanjaKlinike}/{ocenaNajvecaKlinike}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Klinika> findKlinikaByOcenaObe(@PathVariable double ocenaNajmanjaKlinike, @PathVariable double ocenaNajvecaKlinike) {
		List<Klinika> filtriraneKlinike = new ArrayList<Klinika>();
		List<Klinika> sveKlinike = service.listAll();
		for(Klinika k : sveKlinike) {
			if(k.getOcena()>=ocenaNajmanjaKlinike && k.getOcena()<=ocenaNajvecaKlinike) {
				filtriraneKlinike.add(k);
			}
		}
		return filtriraneKlinike;
	}
	
	/* FILTER KLINIKA PO KRITERIJUMU - NAJMANJA OCENA KLINIKE*/
	@GetMapping(value = "/klinike/filterOcena/najmanja/{ocenaNajmanjaKlinike}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Klinika> findKlinikaByOcenaNajmanja(@PathVariable double ocenaNajmanjaKlinike) {
		List<Klinika> filtriraneKlinike = new ArrayList<Klinika>();
		List<Klinika> sveKlinike = service.listAll();
		for(Klinika k : sveKlinike) {
			if(k.getOcena()>=ocenaNajmanjaKlinike) {
				filtriraneKlinike.add(k);
			}
		}
		return filtriraneKlinike;
	}
	
	/* FILTER KLINIKA PO KRITERIJUMU - NAJVECA OCENA KLINIKE*/
	@GetMapping(value = "/klinike/filterOcena/najveca/{ocenaNajvecaKlinike}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Klinika> findKlinikaByOcenaNajveca(@PathVariable double ocenaNajvecaKlinike) {
		List<Klinika> filtriraneKlinike = new ArrayList<Klinika>();
		List<Klinika> sveKlinike = service.listAll();
		for(Klinika k : sveKlinike) {
			if(k.getOcena()<=ocenaNajvecaKlinike) {
				filtriraneKlinike.add(k);
			}
		}
		return filtriraneKlinike;
	}
}
