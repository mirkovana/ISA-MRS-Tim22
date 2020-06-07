package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Recept;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.repository.ReceptRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class ReceptService {
	@Autowired
	private ReceptRepository repo;

	@JsonIgnore
	public List<Recept> listAll() {
		return repo.findAll();
	}

	public Recept save(Recept cenovnik) {
		return repo.save(cenovnik);

	}

	public Recept get(Integer id) {
		return repo.findById(id).get();
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	@JsonIgnore
	public List<Recept> findAllByOveren(boolean overen) {
		return repo.findAllByOveren(overen);
	}
}
