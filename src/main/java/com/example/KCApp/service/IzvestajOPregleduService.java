package com.example.KCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.IzvestajOPregledu;
import com.example.KCApp.repository.IzvestajOPregleduRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class IzvestajOPregleduService {
	@Autowired
	private IzvestajOPregleduRepository repo;

	@JsonIgnore
	public List<IzvestajOPregledu> listAll() {
		return repo.findAll();
	}

	public IzvestajOPregledu save(IzvestajOPregledu izvestajOPregledu) {
		return repo.save(izvestajOPregledu);

	}

	public IzvestajOPregledu get(Integer id) {
		return repo.findById(id).get();
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
