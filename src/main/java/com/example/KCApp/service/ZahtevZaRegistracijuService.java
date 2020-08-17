package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.ZahtevZaRegistraciju;
import com.example.KCApp.repository.ZahtevZaRegistracijuRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class ZahtevZaRegistracijuService {
	
	@Autowired
	private ZahtevZaRegistracijuRepository repo;
	
	@JsonIgnore
	public List<ZahtevZaRegistraciju> listAll(){
		return repo.findAll();
	}
	
	public ZahtevZaRegistraciju save(ZahtevZaRegistraciju zahtevZaRegistraciju) {
		return repo.save(zahtevZaRegistraciju);
		
	}
	
	public ZahtevZaRegistraciju get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
