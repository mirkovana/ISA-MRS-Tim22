package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.repository.OperacijaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class OperacijaService {

	@Autowired
	private OperacijaRepository repo;
	
	@JsonIgnore
	public List<Operacija> listAll(){
		return repo.findAll();
	}
	
	public Operacija save(Operacija operacija) {
		return repo.save(operacija);
		
	}
	
	public Operacija get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	public List<Operacija> findAllByKlinika(Klinika k){
		return repo.findAllByKlinika(k);
	}
}
