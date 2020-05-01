package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.KCApp.beans.Sala;
import com.example.KCApp.repository.SalaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SalaService {

	@Autowired
	private SalaRepository repo;
	
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
}
