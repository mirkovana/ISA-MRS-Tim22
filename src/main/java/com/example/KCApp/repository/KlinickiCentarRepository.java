package com.example.KCApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.KlinickiCentar;

//MILAN: dodat repozitorijum jer nam treba za kreiranje klinike posto ste u modelu rekle da klinika ima cenovnik i pripada klinickom centru koji ne sme biti null
//Onda pri kreiranju klinike moramo da setujemo kojem KC pripada
public interface KlinickiCentarRepository extends JpaRepository<KlinickiCentar, Integer>{

}
