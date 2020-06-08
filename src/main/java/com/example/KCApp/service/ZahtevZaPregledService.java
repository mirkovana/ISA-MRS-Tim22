package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.beans.ZahtevZaPregled;
import com.example.KCApp.repository.ZahtevZaPregledRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class ZahtevZaPregledService {

	@Autowired
	private ZahtevZaPregledRepository repo;
	
	@JsonIgnore
	public List<ZahtevZaPregled> listAll(){
		return repo.findAll();
	}
	
	public ZahtevZaPregled save(ZahtevZaPregled zahtevZaPregled) {
		return repo.save(zahtevZaPregled);
		
	}
	
	public ZahtevZaPregled get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
