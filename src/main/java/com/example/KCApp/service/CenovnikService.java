package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.repository.CenovnikRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class CenovnikService {

	@Autowired
	private CenovnikRepository repo;
	
	@JsonIgnore
	public List<Cenovnik> listAll(){
		return repo.findAll();
	}
	
	public Cenovnik save(Cenovnik cenovnik) {
		return repo.save(cenovnik);
		
	}
	
	
	public Cenovnik get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public List<Cenovnik> findAllByKlinika(Klinika k){
		return repo.findAllByKlinika(k);
	}
}
