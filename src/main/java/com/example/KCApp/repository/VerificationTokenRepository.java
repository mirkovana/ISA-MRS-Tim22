package com.example.KCApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{
	
	
	 @Query(value = "SELECT * FROM verification_token WHERE token = ?1", nativeQuery = true)
	  VerificationToken findByToken(String token);
	  
	  @Query(value = "SELECT * FROM verification_token WHERE lbo = ?1", nativeQuery = true)
	  VerificationToken findByPacijent(Pacijent pacijent);
}
