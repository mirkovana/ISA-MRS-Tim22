package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.repository.KlinikaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class KlinikaService {

	@Autowired
	private KlinikaRepository repo;
	@JsonIgnore
	public List<Klinika> listAll(){
		return repo.findAll();
	}
	
	public Klinika save(Klinika klinika) {
		return repo.save(klinika);
		
	}
	
	
	public Klinika get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
