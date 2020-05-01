package com.example.KCApp.controller;

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
import com.example.KCApp.DTO.KlinikaDTO;
import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.repository.KlinikaRepository;
import com.example.KCApp.service.CenovnikService;
import com.example.KCApp.service.KlinikaService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class KlinikaController {

	@Autowired
	private KlinikaService service;
	
//	@Autowired(required=false)
//	private CenovnikService serviceCenovnik;
//	
	@Autowired
	private KlinikaRepository repository;

	/*ISPISIVANJE KLINIKA*/
	@GetMapping(value="/klinike")
	public List<Klinika> getAllKlinike(Model model) {
		List<Klinika> listaKlinika = service.listAll();
		model.addAttribute("listaKlinika", listaKlinika);
		return listaKlinika;
	}
	
	/*BRISANJE KLINIKE SA ODREDJENIM ID*/
	@DeleteMapping(value= "/klinike/{idKlinike}")
	public String deleteKlinika(@PathVariable Integer idKlinike) {
		
		Klinika klinika = service.get(idKlinike);
	
		if (klinika != null) {
			service.delete(idKlinike);
			return "Uspesno obrisana klinika sa id: " + idKlinike;
		} else {
			return "Klinika sa id: " + idKlinike + " nije pronadjena"; //ne prikazuje poruku kad se stavi id koji ne postoji
		}
	}
	
	/*DODAVANJE KLINIKA PROBLEMATICNO VRLO*/
	/*@PostMapping(consumes = "application/json", value="/klinike")
	public Klinika addKlinika(@Valid @RequestBody Klinika klinika) {
		return repository.save(klinika);		
		
	}*/
	
	/*@PostMapping(consumes = "application/json")
	public ResponseEntity<KlinikaDTO> saveTeacher(@RequestBody KlinikaDTO klinikaDTO) {

		Cenovnik cenovnik = serviceCenovnik.get(klinikaDTO.getCenovnik().getIdCenovnika());
		Klinika klinika = new Klinika();
		klinika.setIdKlinike(klinikaDTO.getIdKlinike());
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setGrad(klinikaDTO.getGrad());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setOcena(klinikaDTO.getOcena());
		klinika.setCenovnik(cenovnik);

		klinika = service.save(klinika);
		}*/
	
	/*UPDATE*/
	@PutMapping(value="/klinike/{idKlinike}", consumes = "application/json")
	public Klinika updateKlinika(@PathVariable Integer idKlinike, @Valid @RequestBody Klinika klinikaUpdated) throws NotFoundException {
		return repository.findById(idKlinike)
				.map(klinika->{
					klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
					klinika.setAdresa(klinikaUpdated.getAdresa());
					klinika.setGrad(klinikaUpdated.getGrad());
					klinika.setNaziv(klinikaUpdated.getNaziv());
					klinika.setOpis(klinikaUpdated.getOpis());
					klinika.setOcena(klinikaUpdated.getOcena());
					klinika.setCenovnik(klinikaUpdated.getCenovnik());
					klinika.setKlinickiCentar(klinikaUpdated.getKlinickiCentar());
					return repository.save(klinika);
				}).orElseThrow(() -> new NotFoundException("Student not found with id " + idKlinike));
		
	}
	
}
