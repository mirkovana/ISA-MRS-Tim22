package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.TipPregledaN;
import com.example.KCApp.repository.TipPregledaNRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class TipPregledaNService {
	
	@Autowired
	private TipPregledaNRepository repo;
	
	@JsonIgnore
	public List<TipPregledaN> listAll(){
		return repo.findAll();
	}
	
	public TipPregledaN save(TipPregledaN tpn) {
		return repo.save(tpn);
		
	}
	
	public TipPregledaN get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	
	public TipPregledaN findByNazivTPN(String nazivTPN) {
		return repo.findByNazivTPN(nazivTPN);
	}

}
