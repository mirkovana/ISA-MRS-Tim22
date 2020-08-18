package com.example.KCApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.VerificationToken;
import com.example.KCApp.repository.VerificationTokenRepository;
@Service
public class VerificationTokenService {
	@Autowired 
	private VerificationTokenRepository verificationTokenRepository;
	
	
	public VerificationToken save(VerificationToken token) {
		return verificationTokenRepository.save(token);
	}
	
	public VerificationToken findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}
	public VerificationToken findByPacijent(Pacijent pacijent) {
		return verificationTokenRepository.findByPacijent(pacijent);
	}	

}
