package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.ZahtevZaPregledDTO;
import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.ZahtevZaPregled;
import com.example.KCApp.service.CenovnikService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.ZahtevZaPregledService;

@RestController
@RequestMapping(value="/api")
public class ZahtevZaPregledController {
	@Autowired
	private ZahtevZaPregledService service;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private LekarService lekarService;	
	
	@Autowired
	private CenovnikService cenovnikService;	
	
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
}
