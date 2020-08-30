package com.example.KCApp.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.ZahtevZaPregledDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.beans.ZahtevZaPregled;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.CenovnikService;
import com.example.KCApp.service.EmailService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.ZahtevZaPregledService;

@RestController
@RequestMapping(value="/api")
public class ZahtevZaPregledController {
	@Autowired
	private ZahtevZaPregledService service;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private AdministratorKlinikeService serviceAK;
	
	@Autowired
	private LekarService lekarService;	
	
	@Autowired
	private CenovnikService cenovnikService;	
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(value= "/zahteviZaPregled/{idPac}/{idLek}",consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<ZahtevZaPregledDTO> saveZZP(@RequestBody ZahtevZaPregledDTO zahtevZaPregledDTO, @PathVariable Integer idPac, @PathVariable Integer idLek) {
		ZahtevZaPregled zahtev = new ZahtevZaPregled();
		Pacijent p = pacijentService.get(idPac);
		Lekar l = lekarService.get(idLek);
		zahtev.setPacijent(p);
		zahtev.setLekar(l);
		TipPregleda tp = l.getTipPregleda();
		Klinika k = l.getKlinika();
		zahtev.setTipPregleda(tp);
		zahtev.setKlinika(k);
		zahtev.setVreme(zahtevZaPregledDTO.getVreme());
		Cenovnik cen = new Cenovnik();
		List<Cenovnik> cenovnici = cenovnikService.findAllByKlinika(k);
		for(Cenovnik c:cenovnici) {
			if(c.getTipPregledaCenovnik() == tp) {
				cen = c;
			}
		}
		zahtev.setCena(cen.getCena());
		zahtev=service.save(zahtev);
		return new ResponseEntity<>(new ZahtevZaPregledDTO(zahtev), HttpStatus.CREATED);
	}
	
	//dodavanje zahteva za pregled kad lekar unese datum
	@PostMapping(value= "/zahteviZaPregled/datumVreme/{datumVreme}/{idPregleda}",consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<ZahtevZaPregledDTO> saveZZPL(@RequestBody ZahtevZaPregledDTO zahtevZaPregledDTO, @PathVariable String datumVreme, @PathVariable Integer idPregleda) {
		System.out.println("USAO U ZAHTEV ZA PREGLEDE LEKAR");
		
		ZahtevZaPregled zahtev = new ZahtevZaPregled();
		
		Pregled p = pregledService.get(idPregleda);
		System.out.println("PREGLED" + p.getIdPregleda());
		
		Lekar l = lekarService.get(p.getLekar().getId());
		System.out.println("LEKAR" + l.getId());

		Pacijent pac = pacijentService.get(p.getPacijent().getId());
		System.out.println("PAC" + pac.getId());

		TipPregleda tp = l.getTipPregleda();
		System.out.println("TP" + tp);

		Klinika k = l.getKlinika();
		System.out.println("KLINIKA" + k.getIdKlinike());

		zahtev.setVreme(datumVreme);
		System.out.println("DV" + datumVreme);

		zahtev.setLekar(l);
		zahtev.setPacijent(pac);
		zahtev.setTipPregleda(tp);
		zahtev.setKlinika(k);
		Cenovnik cen = new Cenovnik();
		List<Cenovnik> cenovnici = cenovnikService.findAllByKlinika(k);
		for(Cenovnik c:cenovnici) {
			if(c.getTipPregledaCenovnik() == tp) {
				cen = c;
			}
		}
		System.out.println("CENA" + cen.getCena());

		zahtev.setCena(cen.getCena());
		zahtev=service.save(zahtev);
		System.out.println("ZAHTEV" + zahtev);
		System.out.println("id " + zahtev.getIdZahtevaZaPregled() + ", lekar " + zahtev.getLekar() + ", pac " + zahtev.getPacijent() + ", dv " + zahtev.getVreme() + ", cena " + zahtev.getCena() + ", klinika " + zahtev.getKlinika() + ", tp " + zahtev.getTipPregleda());
		emailService.slanjeZahtevaZPAdminu(zahtev);
		return new ResponseEntity<>(new ZahtevZaPregledDTO(zahtev), HttpStatus.CREATED);
		
	}
	
	/*PRIKAZ SVIH ZAHTEVA KLINIKE ZA ADMINA KLINIKE*/
	@GetMapping(value="/zahteviZaPregled/adminK/{id}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<ZahtevZaPregled> getAllZahteviZaPKlinikeAK(Model model, @PathVariable Integer id) {
		System.out.println("USAO U ZAHTEV ZA P");
		
		AdministratorKlinike ak = serviceAK.get(id);
		Klinika k = ak.getKlinika();
		List<ZahtevZaPregled> sviZahtevi = service.listAll();
		List<ZahtevZaPregled> zahteviAK = new ArrayList<ZahtevZaPregled>();
		
		for (ZahtevZaPregled z : sviZahtevi) {
			if (z.getKlinika() == k) {
				zahteviAK.add(z);
			}
		}
		
		System.out.println(zahteviAK);

		return zahteviAK;
	}
}
