package com.example.KCApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pacijent;

public interface OperacijaRepository extends JpaRepository<Operacija, Integer>{
	List<Operacija> findAllByKlinika(Klinika K);
	List<Operacija> findAllByPacijent(Pacijent p);
}
