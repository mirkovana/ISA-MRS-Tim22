package com.example.KCApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer>{

	Pacijent findByBrojOsiguranika(int brojOsiguranika);

	List<Pacijent> findAllByIme(String ime);
	List<Pacijent> findAllByPrezime(String prezime);

}
