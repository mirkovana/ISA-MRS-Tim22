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

import com.example.KCApp.DTO.IzvestajOPregleduDTO;
import com.example.KCApp.beans.IzvestajOPregledu;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.SifrarnikDijagnoza;
import com.example.KCApp.beans.SifrarnikLekova;
import com.example.KCApp.service.IzvestajOPregleduService;
import com.example.KCApp.service.PregledService;
import com.example.KCApp.service.SifrarnikDijagnozaService;
import com.example.KCApp.service.SifrarnikLekovaService;

@RestController
@RequestMapping(value="/api")
public class IzvestajOPregleduController {
	
	@Autowired
	private IzvestajOPregleduService service;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private SifrarnikLekovaService serviceSL;
	
	@Autowired
	private SifrarnikDijagnozaService serviceSD;
	
	@PostMapping(value= "/izvestajOPregleduDTO/{idPregleda}",consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<IzvestajOPregleduDTO> saveKlinika(@RequestBody IzvestajOPregleduDTO iopDTO, @PathVariable Integer idPregleda) {

		List<SifrarnikLekova> listaSL = serviceSL.listAll();
		List<SifrarnikDijagnoza> listaSD = serviceSD.listAll();
		SifrarnikLekova sl = new SifrarnikLekova();
		SifrarnikDijagnoza sd = new SifrarnikDijagnoza();
		IzvestajOPregledu iop = new IzvestajOPregledu();
		Pregled p = pregledService.get(idPregleda);
		for(SifrarnikDijagnoza s : listaSD) {
			if(s.getNazivDijagnoze().equals(iopDTO.getNazivDijagnoze())) {
				sd=s;
			}
		}
		
		for(SifrarnikLekova s: listaSL) {
			if(s.getNazivLeka().equals(iopDTO.getNazivLeka())) {
				sl=s;
			}
		}
		
		iop.setPregled(p);
		iop.setSifrarnikDijagnoza(sd);
		iop.setSifrarnikLekova(sl);
		iop.setInfo(iopDTO.getInfo());
		iop=service.save(iop);
		return new ResponseEntity<>(new IzvestajOPregleduDTO(iop), HttpStatus.CREATED);
		}
}
