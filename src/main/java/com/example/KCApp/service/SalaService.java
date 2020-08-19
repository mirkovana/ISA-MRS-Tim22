package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.repository.SalaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class SalaService {

	@Autowired
	private SalaRepository repo;
	
	@JsonIgnore
	public List<Sala> listAll(){
		return repo.findAll();
	}
	
	public Sala save(Sala sala) {
		return repo.save(sala);
		
	}
	
	public Sala get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public Sala findByBrojSale(int brojSale) {
		return repo.findByBrojSale(brojSale);
	}
	public Sala findByNazivSale(String nazivSale) {
		return repo.findByNazivSale(nazivSale);
	}
}
