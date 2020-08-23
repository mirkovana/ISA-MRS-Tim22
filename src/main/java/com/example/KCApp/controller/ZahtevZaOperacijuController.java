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

import com.example.KCApp.DTO.ZahtevZaOperacijuDTO;
import com.example.KCApp.DTO.ZahtevZaPregledDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.beans.ZahtevZaPregled;
import com.example.KCApp.beans.ZahtevZaOperaciju;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.CenovnikService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.OperacijaService;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.ZahtevZaOperacijuService;
import com.example.KCApp.service.ZahtevZaPregledService;

@RestController
@RequestMapping(value="/api")
public class ZahtevZaOperacijuController {
	
	@Autowired
	private ZahtevZaOperacijuService service;
	
	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private AdministratorKlinikeService serviceAK;
	
	@Autowired
	private LekarService lekarService;	
	
	@PostMapping(value= "/zahteviZaOperaciju/datumVremeO/{datumVreme}/{idPregleda}",consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<ZahtevZaOperacijuDTO> saveZZOL(@RequestBody ZahtevZaOperacijuDTO zahtevZaOperacijuDTO, @PathVariable String datumVreme, @PathVariable Integer idPregleda) {
		System.out.println("USAO U ZAHTEV ZA OPERACIJU LEKAR");
		
		ZahtevZaOperaciju zahtev = new ZahtevZaOperaciju();
		
		Operacija o = operacijaService.get(idPregleda);
		System.out.println("OPERACIJA" + o.getIdOperacije());
		
		Lekar l = lekarService.get(o.getLekar().getId());
		System.out.println("LEKAR" + l.getId());

		Pacijent pac = pacijentService.get(o.getPacijent().getId());
		System.out.println("PAC" + pac.getId());


		Klinika k = l.getKlinika();
		System.out.println("KLINIKA" + k.getIdKlinike());

		zahtev.setVreme(datumVreme);
		System.out.println("DV" + datumVreme);

		zahtev.setLekar(l);
		zahtev.setPacijent(pac);
		zahtev.setKlinika(k);
		

		zahtev=service.save(zahtev);
		
		System.out.println("ZAHTEV" + zahtev);
		System.out.println("id " + zahtev.getIdZahtevaZaOperaciju() + ", lekar " + zahtev.getLekar() + ", pac " + zahtev.getPacijent() + ", dv " + zahtev.getVreme() + ", klinika " + zahtev.getKlinika() + ", tp ");
		return new ResponseEntity<>(new ZahtevZaOperacijuDTO(zahtev), HttpStatus.CREATED);
		
	}

}
