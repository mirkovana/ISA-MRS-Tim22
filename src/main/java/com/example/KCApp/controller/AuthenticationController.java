package com.example.KCApp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.KCApp.DTO.AdministratorKlinickogCentraDTO;
import com.example.KCApp.DTO.AdministratorKlinikeDTO;
import com.example.KCApp.DTO.LekarDTO;
import com.example.KCApp.DTO.MedicinskaSestraDTO;
import com.example.KCApp.DTO.PacijentDTO;
import com.example.KCApp.DTO.UserDTO;
import com.example.KCApp.beans.AdministratorKlinickogCentra;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.User;
import com.example.KCApp.beans.UserTokenState;
import com.example.KCApp.exception.ResourceConflictException;
import com.example.KCApp.security.TokenUtils;
import com.example.KCApp.security.auth.JwtAuthenticationRequest;
import com.example.KCApp.service.AdministratorKlinickogCentraService;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.AuthorityService;
import com.example.KCApp.service.LekarService;
import com.example.KCApp.service.MedicinskaSestraService;
import com.example.KCApp.service.PacijentService;
import com.example.KCApp.service.UserService;
import com.example.KCApp.service.impl.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdministratorKlinickogCentraService AKCService;
	
	@Autowired
	private AdministratorKlinikeService AKService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private MedicinskaSestraService MSService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private AuthorityService authorityService;

	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) {

		// 
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Ubaci korisnika u trenutni security kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		Integer userId = user.getId();
		for(Object a : user.getAuthorities()) {
			Authority auth = (Authority) a;
			if(auth.getName().equals("ROLE_ADMINKC")) {
				user = AKCService.get(userId);
				return ResponseEntity.ok(new AdministratorKlinickogCentraDTO((AdministratorKlinickogCentra)user, new UserTokenState(jwt, expiresIn)));
			}else if(auth.getName().equals("ROLE_ADMINK")) {
				user = AKService.get(userId);
				return ResponseEntity.ok(new AdministratorKlinikeDTO((AdministratorKlinike)user, new UserTokenState(jwt, expiresIn)));
			}else if(auth.getName().equals("ROLE_LEKAR")) {
				user = lekarService.get(userId);
				return ResponseEntity.ok(new LekarDTO((Lekar)user, new UserTokenState(jwt, expiresIn)));
			}else if(auth.getName().equals("ROLE_MS")) {
				user = MSService.get(userId);
				return ResponseEntity.ok(new MedicinskaSestraDTO((MedicinskaSestra)user, new UserTokenState(jwt, expiresIn)));
			}else if(auth.getName().equals("ROLE_PACIJENT")) {
				user = pacijentService.get(userId);
				return ResponseEntity.ok(new PacijentDTO((Pacijent)user, new UserTokenState(jwt, expiresIn)));
			}else {
				return ResponseEntity.ok(new UserDTO(user, new UserTokenState(jwt, expiresIn)));
			}
		}
		return ResponseEntity.badRequest().build();
		// TO JE BILO Vrati token kao odgovor na uspesnu autentifikaciju
		//return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/logout")
	public ResponseEntity logOut(HttpServletRequest request) {
		SecurityContextHolder.getContext().setAuthentication(null);
		return new ResponseEntity(HttpStatus.OK);
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@RequestBody UserDTO userData) {
		 	Pacijent user = new Pacijent();
		 	user.setPassword(passwordEncoder.encode(userData.getPassword()));
		 	user.setUsername(userData.getUsername());
		 	user.setGrad(userData.getGrad());
		 	user.setDrzava(userData.getDrzava());
		 	user.setBrojTelefona(userData.getBrojTelefona());
		 	user.setIme(userData.getIme());
		 	user.setPrezime(userData.getPrezime());
		 	user.setEmail(userData.getUsername());
		 	user.setAdresa(userData.getAdresa());
		 	user.setLastPasswordResetDate(userData.getLastPasswordResetDate());
		 	user.setAuthorities(Arrays.asList(authorityService.findOne(5)));
		 	userService.save(user);
		 	return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}

	// U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
	@PostMapping(value = "/refresh")
	public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}