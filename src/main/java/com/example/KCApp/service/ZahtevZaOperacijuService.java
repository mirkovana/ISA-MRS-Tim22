package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.beans.ZahtevZaOperaciju;
import com.example.KCApp.beans.ZahtevZaPregled;
import com.example.KCApp.repository.ZahtevZaOperacijuRepository;
import com.example.KCApp.repository.ZahtevZaPregledRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class ZahtevZaOperacijuService {

	@Autowired
	private ZahtevZaOperacijuRepository repo;
	
	@JsonIgnore
	public List<ZahtevZaOperaciju> listAll(){
		return repo.findAll();
	}
	
	public ZahtevZaOperaciju save(ZahtevZaOperaciju zahtevZaOperaciju) {
		return repo.save(zahtevZaOperaciju);
		
	}
	
	public ZahtevZaOperaciju get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
