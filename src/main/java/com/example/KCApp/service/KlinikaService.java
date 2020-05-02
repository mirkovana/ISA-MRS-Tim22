package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Ocena;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.repository.KlinikaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class KlinikaService {

	@Autowired
	private KlinikaRepository repo;
	@JsonIgnore
	public List<Klinika> listAll(){
		return repo.findAll();
	}
	
	public Klinika save(Klinika klinika) {
		return repo.save(klinika);
		
	}
	
	
	public Klinika get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public List<Klinika> findAllByOcena(Ocena ocena) {
		return repo.findAllByOcena(ocena);
	}
	
	public List<Klinika> findAllByGrad(String grad) {
		return repo.findAllByGrad(grad);
	}
}
