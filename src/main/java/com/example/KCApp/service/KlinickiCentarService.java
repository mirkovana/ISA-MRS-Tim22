package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.KlinickiCentar;
import com.example.KCApp.repository.KlinickiCentarRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class KlinickiCentarService {
	
	@Autowired
	private KlinickiCentarRepository repo;
	
	@JsonIgnore
	public List<KlinickiCentar> listAll(){
		return repo.findAll();
	}
	
	public KlinickiCentar save(KlinickiCentar kc) {
		return repo.save(kc);
		
	}
	
	
	public KlinickiCentar get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
