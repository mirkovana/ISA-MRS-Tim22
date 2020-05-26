package com.example.KCApp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.ZdravstveniKartonDTO;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.repository.ZdravstveniKartonRepository;
import com.example.KCApp.service.ZdravstveniKartonService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/api")
public class ZdravstveniKartonController {
	
	@Autowired
	private ZdravstveniKartonService service;
	
	@Autowired
	private ZdravstveniKartonRepository repository;
	
	@GetMapping(value="/kartoni/svi/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public ZdravstveniKarton getAllZdravstveniKartoni(Model model, @PathVariable Integer id) {
		List<ZdravstveniKarton> listaZdravstveniKartoni = service.listAll();
		for(ZdravstveniKarton zk : listaZdravstveniKartoni)
		{
			if(id.equals(zk.getPacijent().getId()))
			{
				model.addAttribute("listaZdravstveniKartoni", listaZdravstveniKartoni);
				return zk;
			}
		}
		ZdravstveniKarton prazan = new ZdravstveniKarton();
		return prazan;
	}
	
	@GetMapping(value = "/kartoni/{idZdravstvenogKartona}")
	public ZdravstveniKarton findZDById(@PathVariable Integer idZdravstvenogKartona) {
		ZdravstveniKarton zdravstveniKarton = service.get(idZdravstvenogKartona);
		return zdravstveniKarton;
	}
	
	
	/*UPDATE ZDRAVSTVENOG KARTONA*/
	@PutMapping(value="/kartoni/{idZdravstvenogKartona}", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ZdravstveniKarton updateZdravstveniKarton(@PathVariable Integer idZdravstvenogKartona, @Valid @RequestBody ZdravstveniKartonDTO zdravstveniKartonUpdated) throws NotFoundException {
		return repository.findById(idZdravstvenogKartona)
				.map(zdravstveniKarton->{
					//klinika.setIdKlinike(klinikaUpdated.getIdKlinike());
					zdravstveniKarton.setTezina(zdravstveniKartonUpdated.getTezina());
					zdravstveniKarton.setVisina(zdravstveniKartonUpdated.getVisina());
					zdravstveniKarton.setDioptrija(zdravstveniKartonUpdated.getDioptrija());
					zdravstveniKarton.setKrvnaGrupa(zdravstveniKartonUpdated.getKrvnaGrupa());
				
					return repository.save(zdravstveniKarton);
				}).orElseThrow(() -> new NotFoundException("Zdravstveni karton not found with id " + idZdravstvenogKartona));
		
	}
}
