package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.repository.LekarRepository;
import com.example.KCApp.repository.MedicinskaSestraRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class LekarService {
	@Autowired
	private LekarRepository repo;
	
	@JsonIgnore
	public List<Lekar> listAll(){
		return repo.findAll();
	}
	
	public Lekar save(Lekar lekar) {
		return repo.save(lekar);
		
	}
	
	public Lekar get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
