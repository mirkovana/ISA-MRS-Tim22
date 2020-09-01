package com.example.KCApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.TipPregledaN;

public interface TipPregledaNRepository extends JpaRepository<TipPregledaN, Integer>{
	
	TipPregledaN findByNazivTPN(String nazivTPN);

}

