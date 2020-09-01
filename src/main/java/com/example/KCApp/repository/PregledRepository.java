package com.example.KCApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Integer>{
	List<Pregled> findAllByKlinika(Klinika K);
	List<Pregled> findAllByPacijent(Pacijent p);
}
