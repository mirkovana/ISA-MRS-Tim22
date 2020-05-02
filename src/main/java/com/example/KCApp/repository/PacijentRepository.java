package com.example.KCApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer>{

	Pacijent findByBrojOsiguranika(int brojOsiguranika);
}
