package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.SifrarnikDijagnoza;
import com.example.KCApp.repository.SifrarnikDijagnozaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Service
public class SifrarnikDijagnozaService {

	@Autowired
	private SifrarnikDijagnozaRepository repo;
	
	@JsonIgnore
	public List<SifrarnikDijagnoza> listAll(){
		return repo.findAll();
	}
	
	public SifrarnikDijagnoza save(SifrarnikDijagnoza sifrarnikDijagnoza) {
		return repo.save(sifrarnikDijagnoza);
		
	}
	
	public SifrarnikDijagnoza get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
