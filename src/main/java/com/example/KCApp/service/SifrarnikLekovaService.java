package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.SifrarnikLekova;
import com.example.KCApp.repository.SifrarnikLekovaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class SifrarnikLekovaService {

	@Autowired
	private SifrarnikLekovaRepository repo;
	
	@JsonIgnore
	public List<SifrarnikLekova> listAll(){
		return repo.findAll();
	}
	
	public SifrarnikLekova save(SifrarnikLekova sifrarnikLekova) {
		return repo.save(sifrarnikLekova);
		
	}
	
	public SifrarnikLekova get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
