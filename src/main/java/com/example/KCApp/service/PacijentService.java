package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.repository.PacijentRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository repo;
	
	@JsonIgnore
	public List<Pacijent> listAll(){
		return repo.findAll();
	}
	
	public Pacijent save(Pacijent pacijent) {
		return repo.save(pacijent);
		
	}
	
	public Pacijent get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
