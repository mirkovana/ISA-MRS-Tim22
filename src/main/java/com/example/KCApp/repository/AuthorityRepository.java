package com.example.KCApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KCApp.beans.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	Authority findByName(String name);
}
