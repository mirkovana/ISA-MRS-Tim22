package com.example.KCApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.KCApp.DTO.PacijentDTO;
import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.UserRequest;
import com.example.KCApp.repository.UserRepository;
import com.example.KCApp.service.AuthorityService;
import com.example.KCApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authService;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	public User findById(Integer id) throws AccessDeniedException {
		User u = userRepository.findById(id).orElseGet(null);
		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

	/*@Override
	public User save(UserDTO userRequest) {
		User u = new User();
		u.setUsername(userRequest.getUsername());
		// pre nego sto postavimo lozinku u atribut hesiramo je
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setIme(userRequest.getIme());
		u.setPrezime(userRequest.getPrezime());
		//u.setEnabled(true);
		
		List<Authority> auth = authService.findByname("ROLE_PACIJENT");
		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		u.setAuthorities(auth);
		
		u = this.userRepository.save(u);
		return u;
	}*/
	
	public User save(User userRequest) {
		return this.userRepository.save(userRequest);
	}
	

}
