package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.ZdravstveniKarton;
import com.example.KCApp.repository.ZdravstveniKartonRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class ZdravstveniKartonService {

	@Autowired
	private ZdravstveniKartonRepository repo;
	
	@JsonIgnore
	public List<ZdravstveniKarton> listAll(){
		return repo.findAll();
	}
	
	public ZdravstveniKarton save(ZdravstveniKarton zdravstveniKarton) {
		return repo.save(zdravstveniKarton);
		
	}
	
	public ZdravstveniKarton get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
