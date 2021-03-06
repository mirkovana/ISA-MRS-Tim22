package com.example.KCApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Sala;

public interface SalaRepository extends JpaRepository<Sala, Integer>{
	
	Sala findByBrojSale(int brojSale);
	Sala findByNazivSale(String nazivSale);

}
