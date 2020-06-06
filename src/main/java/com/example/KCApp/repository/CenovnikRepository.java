package com.example.KCApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;

public interface CenovnikRepository extends JpaRepository<Cenovnik, Integer>{
	List<Cenovnik> findAllByKlinika(Klinika k);
}
