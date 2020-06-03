package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.repository.ZahtevOdsustvaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class ZahtevOdsustvaService {

	@Autowired
	private ZahtevOdsustvaRepository repo;
	
	@JsonIgnore
	public List<ZahtevOdsustva> listAll(){
		return repo.findAll();
	}
	
	public ZahtevOdsustva save(ZahtevOdsustva zahtevOdsustva) {
		return repo.save(zahtevOdsustva);
		
	}
	
	public ZahtevOdsustva get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
}
