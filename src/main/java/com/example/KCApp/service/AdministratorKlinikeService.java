package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.repository.AdministratorKlinikeRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class AdministratorKlinikeService {

	@Autowired
	private AdministratorKlinikeRepository repo;

	@JsonIgnore
	public List<AdministratorKlinike> listAll() {
		return repo.findAll();
	}

	public AdministratorKlinike save(AdministratorKlinike administratoriKlinike) {
		return repo.save(administratoriKlinike);

	}

	public AdministratorKlinike get(Integer id) {
		return repo.findById(id).get();
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}}
