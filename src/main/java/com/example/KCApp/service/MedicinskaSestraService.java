package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.repository.MedicinskaSestraRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class MedicinskaSestraService {
	@Autowired
	private MedicinskaSestraRepository repo;
	
	@JsonIgnore
	public List<MedicinskaSestra> listAll(){
		return repo.findAll();
	}
	
	public MedicinskaSestra save(MedicinskaSestra medicinskaSestra) {
		return repo.save(medicinskaSestra);
		
	}
	
	public MedicinskaSestra get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
