package com.example.KCApp.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.VerificationToken;
import com.example.KCApp.beans.ZahtevZaRegistraciju;
import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.service.AuthorityService;
import com.example.KCApp.service.EmailService;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.UserService;
import com.example.KCApp.service.VerificationTokenService;
import com.example.KCApp.service.ZahtevZaRegistracijuService;
import com.example.KCApp.verifikacijaEmaila.OnRegistrationCompleteEvent;

import javassist.NotFoundException;



@RestController
@RequestMapping(value="/api")
public class ZahtevZaRegistracijuController {

	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private ZahtevZaRegistracijuService service;
	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VerificationTokenService verificationService;
	/* ISPISIVANJE ZAHTEVA ZA REGISTRACIJU */
	@GetMapping(value = "/zahtevizr")
	@PreAuthorize("hasRole('ADMINKC')")
	public List<ZahtevZaRegistraciju> getAllZahtevi(Model model) {
		List<ZahtevZaRegistraciju> listaZahteva = service.listAll();
		return listaZahteva;
	}
	
	
	
	@PostMapping(value= "/odbijenZahtev/{idZahteva}/{razlog}",  consumes="application/json")
	@PreAuthorize("hasRole('ADMINKC')")
   	public ResponseEntity deniedRegAsync(@PathVariable Integer idZahteva, @PathVariable String razlog){
		ZahtevZaRegistraciju user = service.get(idZahteva);

	
		String opis = razlog;
		try {
			service.delete(idZahteva); 
			emailService.sendNotificaitionDeniedAsync(user, opis);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch( Exception e ){
			System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Transactional
	@PostMapping(value= "/prihvaceno/{idZahteva}")
	@PreAuthorize("hasRole('ADMINKC')")
	public ResponseEntity acceptedRegAsync(@PathVariable Integer idZahteva, HttpServletRequest request){
		ZahtevZaRegistraciju zahtev = service.get(idZahteva);

		 User us = userService.findByUsername(zahtev.getEmail());
		 if(us != null) {
			return new ResponseEntity<>(new UserDTO(),HttpStatus.BAD_REQUEST);
	     }

		
		 
		
		Pacijent registrovaniPacijent = new Pacijent();
		registrovaniPacijent.setAdresa(zahtev.getAdresa());
		registrovaniPacijent.setIme(zahtev.getIme());
		registrovaniPacijent.setPrezime(zahtev.getPrezime());
		registrovaniPacijent.setUsername(zahtev.getEmail());
		registrovaniPacijent.setEmail(zahtev.getEmail());
		registrovaniPacijent.setBrojTelefona(zahtev.getBrojTelefona());
		registrovaniPacijent.setGrad(zahtev.getGrad());
		registrovaniPacijent.setPassword(zahtev.getPassword());
		registrovaniPacijent.setDrzava(zahtev.getDrzava());
		registrovaniPacijent.setBrojOsiguranika(zahtev.getBrojOsiguranika());
		//registrovaniPacijent.setLastPasswordResetDate((zahtev.getLastPasswordResetDate()));
	    registrovaniPacijent.setAktivan(false); 
		registrovaniPacijent.setAuthorities(Arrays.asList(authorityService.findOne(5)));
		registrovaniPacijent.setZdravstveniKarton(new ZdravstveniKarton());
		
		
		
		ZahtevZaRegistraciju user = new ZahtevZaRegistraciju();
		user.setAdresa(zahtev.getAdresa());
		user.setIme(zahtev.getIme());
		user.setPrezime(zahtev.getPrezime());
		user.setEmail(zahtev.getEmail());
		user.setBrojTelefona(zahtev.getBrojTelefona());
		user.setGrad(zahtev.getGrad());
		user.setPassword(zahtev.getPassword());
		user.setDrzava(zahtev.getDrzava());
		user.setBrojOsiguranika(zahtev.getBrojOsiguranika());
		

		try {
			ZahtevZaRegistraciju brisi = service.findByEmail(user.getEmail());
			service.delete(brisi.getIdZahtevaZaRegistraciju());
			registrovaniPacijent = pacijentService.save(registrovaniPacijent);
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registrovaniPacijent,
					request.getLocale(), request.getContextPath()));
			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch( Exception e ){
			System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
	}
	
	
	
	/*@PutMapping(value = "/omogucenaRegistracija/{token}")
	public String confirmRegistration(@PathVariable String token,HttpServletRequest request) {
        System.out.println("UZAO SAM UUUUUUUUUUUUUUU POTVRDUUU");
		VerificationToken verificationToken = verificationService.findByToken(token);
		if(verificationToken == null)
		{
			return "redirec: access denied";
		}
		Pacijent pacijent = verificationToken.getPacijent();
		Calendar calendar = Calendar.getInstance();
		if((verificationToken.getDatumUnistavanja().getTime()-calendar.getTime().getTime())<=0) {
			return "redirec: access denied";
		}
	
		pacijent.setAktivan(true);
		System.out.println("SAD JE AKTIVAN? " + pacijent.getBrojOsiguranika());
		pacijentService.save(pacijent);
		
		return null;
	}*/
	
	/*@PostMapping(value="/omogucenaRegistracija/{token}", consumes = "application/json")
	@PreAuthorize("permitAll()")
	public String confirmRegistration(@PathVariable String token){
        System.out.println("UZAO SAM UUUUUUUUUUUUUUU POTVRDUUU");
		VerificationToken verificationToken = verificationService.findByToken(token);
		Pacijent pacijent = verificationToken.getPacijent();
		pacijent.setAktivan(true);
		System.out.println("SAD JE AKTIVAN? " + pacijent.getBrojOsiguranika());
		pacijentService.save(pacijent);
		
		return token;
	}*/

}
