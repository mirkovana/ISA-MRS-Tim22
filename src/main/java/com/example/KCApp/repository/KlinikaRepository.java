package com.example.KCApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Ocena;
import com.example.KCApp.beans.TipPregleda;

public interface KlinikaRepository extends JpaRepository<Klinika, Integer>{

	List<Klinika> findAllByOcena(Ocena ocena);
	List<Klinika> findAllByGrad(String grad);
}
