package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.IzvestajKlinike;
import com.example.KCApp.beans.IzvestajOPregledu;
import com.example.KCApp.repository.IzvestajKlinikeRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class IzvestajKlinikeService {
	@Autowired
	private IzvestajKlinikeRepository repo;

	@JsonIgnore
	public List<IzvestajKlinike> listAll() {
		return repo.findAll();
	}

	public IzvestajKlinike save(IzvestajKlinike izvestajKlinike) {
		return repo.save(izvestajKlinike);

	}

	public IzvestajKlinike get(Integer id) {
		return repo.findById(id).get();
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
