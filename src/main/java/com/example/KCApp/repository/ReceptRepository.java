package com.example.KCApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Recept;
import com.example.KCApp.beans.TipPregleda;

public interface ReceptRepository extends JpaRepository<Recept, Integer>{

	List<Recept> findAllByOveren(boolean overen);
}
