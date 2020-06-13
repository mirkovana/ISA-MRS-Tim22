package com.example.KCApp.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.AdministratorKlinikeDTO;
import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.User;
import com.example.KCApp.repository.AdministratorKlinikeRepository;
import com.example.KCApp.repository.UserRepository;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.AuthorityService;
import com.example.KCApp.service.KlinikaService;
import com.example.KCApp.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class AdministratorKlinikeController {
	@Autowired
	private AdministratorKlinikeService service;
	
	@Autowired
	private KlinikaService klinikaService;
	
	
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AdministratorKlinikeRepository repository2;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	/*PRIKAZ ADMINISTRATORA KLINIKE PO ID-u*/
	@GetMapping(value = "/adminiKlinike/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public AdministratorKlinike findAdministratorKlinikeById(@PathVariable Integer id) {
		AdministratorKlinike administratorKlinike = service.get(id);
		return administratorKlinike;
	}
	
	
	/*PRIKAZ SVIH ADMINA KLINIKE*/
	@GetMapping(value="/adminiKlinike")
	@PreAuthorize("hasRole('ADMINKC')")
	public List<AdministratorKlinike> getAllAdministratorKlinike(Model model) {
		List<AdministratorKlinike> administratorKlinike = service.listAll();
		model.addAttribute("administratorKlinike", administratorKlinike);
		return administratorKlinike;
	}
	
	/*DODAVANJE ADMINISTRATORA KLINIKE*/ //prilikom dodavanja ispise lepo sve informacije, a prilikom izlistavanja nakon dodavanja za zdravstveni karton stavi da je null
	@PostMapping("/adminiKlinike")
	@PreAuthorize("hasRole('ADMINKC') or hasRole('ADMINK')")
	public ResponseEntity<?> saveAdminKlinike(@RequestBody AdministratorKlinikeDTO administratorKlinikeDTO) {
		/*list[0]=usedto
		list[1]=idklinike*/
		
		//UserDTO administratorKlinikeDTO;
		//administratorKlinikeDTO = mapper.readValue(a, UserDTO.class);
		//Integer idK=Integer.parseInt(i);
		AdministratorKlinike administratorKlinike = new AdministratorKlinike();
		administratorKlinike.setIme(administratorKlinikeDTO.getIme());
		administratorKlinike.setPrezime(administratorKlinikeDTO.getPrezime());
		administratorKlinike.setEmail(administratorKlinikeDTO.getEmail());
		administratorKlinike.setUsername(administratorKlinikeDTO.getEmail());
		administratorKlinike.setPassword(passwordEncoder.encode(administratorKlinikeDTO.getPassword()));
		administratorKlinike.setAdresa(administratorKlinikeDTO.getAdresa());
		administratorKlinike.setGrad(administratorKlinikeDTO.getGrad());
		administratorKlinike.setDrzava(administratorKlinikeDTO.getDrzava());
		administratorKlinike.setBrojTelefona(administratorKlinikeDTO.getBrojTelefona());
		//administratorKlinike.setKlinika(administratorKlinikeDTO.getKlinika());
		//Integer idK=(Integer) lista.get(1);
		administratorKlinike.setLastPasswordResetDate(administratorKlinikeDTO.getLastPasswordResetDate());
		administratorKlinike.setAuthorities(Arrays.asList(authorityService.findOne(2)));
	 	//userService.save(administratorKlinike);
	 	Integer idK=administratorKlinikeDTO.getKlinika();
		Klinika k = klinikaService.get(idK);
		
	

		//MILAN: u kliniku postavljamo jos klinicki centar jer ste u klasi Klinika navele da referenca ne sme biti null
		administratorKlinike.setKlinika(k);
		administratorKlinike = service.save(administratorKlinike);
		return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	//IZMENA PROFILA
	@PutMapping(value="/adminiKlinike/izmena/{id}", consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public User updateAK(@PathVariable Integer id, @Valid @RequestBody AdministratorKlinikeDTO akUpdated) throws NotFoundException {
		return repository2.findById(id)
				.map(ak->{
					System.out.println("USERRRRR" + ak);
					//klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
					ak.setIme(akUpdated.getIme());
					ak.setPrezime(akUpdated.getPrezime());
					ak.setPassword(passwordEncoder.encode(akUpdated.getPassword()));
					ak.setLastPasswordResetDate(akUpdated.getLastPasswordResetDate());
					ak.setAdresa(akUpdated.getAdresa());
					ak.setGrad(akUpdated.getGrad());
					ak.setDrzava(akUpdated.getDrzava());
					ak.setBrojTelefona(akUpdated.getBrojTelefona());
					//user.setAuthorities(Arrays.asList(authorityService.findOne(1)));
					return repository2.save(ak);
				}).orElseThrow(() -> new NotFoundException("Administrator klinike nije pronadjen sa id : " + id));
	}
}
