package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Pregled;
import com.example.KCApp.repository.PregledRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class PregledService {

	@Autowired
	private PregledRepository repo;
	
	@JsonIgnore
	public List<Pregled> listAll(){
		return repo.findAll();
	}
	
	public Pregled save(Pregled pregled) {
		return repo.save(pregled);
		
	}
	
	public Pregled get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
