package com.example.KCApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.TipPregleda;

public interface LekarRepository extends JpaRepository<Lekar, Integer>{

	List<Lekar> findAllByTipPregleda(TipPregleda tipPregleda);
}