package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.AdministratorKlinickogCentra;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.repository.AdministratorKlinickogCentraRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class AdministratorKlinickogCentraService {

	@Autowired
	private AdministratorKlinickogCentraRepository repo;

	@JsonIgnore
	public List<AdministratorKlinickogCentra> listAll() {
		return repo.findAll();
	}

	public AdministratorKlinickogCentra save(AdministratorKlinickogCentra administratoriKC) {
		return repo.save(administratoriKC);

	}

	public AdministratorKlinickogCentra get(Integer id) {
		return repo.findById(id).get();
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
