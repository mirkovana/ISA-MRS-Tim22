package com.example.KCApp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.beans.ZahtevZaRegistraciju;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;


	@Autowired
	private Environment env;
	
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private LekarService lekarService;
	
	@Async
	public void sendNotificaitionDeniedAsync(ZahtevZaRegistraciju user, String opis) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");


			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(user.getEmail());
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + user.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			System.out.println("USPEO SAM DA PRODJEM FROM");
			mail.setSubject("Zahtev za registracijom je odbijen");
			System.out.println("PROSAO SUBJECT");
			mail.setText("Pozdrav " + user.getIme() + ",\n\nzahtev Vam je odbijen iz razloga \n"+ opis);
			System.out.println("PROSAO SET TEKST");
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		
		
		
		
	}
	
	@Async
	public void sendNotificaitionAsync(String url, String recipient, String subject) {
		System.out.println("Slanje emaila...");

		try {
			 
	        SimpleMailMessage email = new SimpleMailMessage();
	        email.setText("Potvrdite vas email za aktivaciju profila");
	        email.setTo(recipient);
	        email.setSubject(subject);
	        email.setText("http://localhost:8080/#" + url);
	        System.out.println(url);
	        javaMailSender.send(email);	
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}
	
	//SLANJE MEJLA PACIJENTU DA JE ODOBRENA OPERACIJA
	public void slanjePacijentuOdobrenaOperacija(Operacija operacija) {
		System.out.println("Slanje emaila...");
	
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(operacija.getPacijent().getEmail());
			System.out.println("MAIL PACIJENTA JE  " + operacija.getPacijent().getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Odobren zahtev za operaciju");
			mail.setText("Postovani,\n\nZahtev za operaciju je odobren:\n\nDatum i vreme: "+ operacija.getVremeOperacije()+
					
					"\nLekar: " + (operacija.getLekar().getIme()) + " " + 
					(operacija.getLekar().getPrezime())+
					"\nPacijent: " + operacija.getPacijent().getIme() + " " + operacija.getPacijent().getPrezime() + 
					"\nSala: " + operacija.getSala().getNazivSale() + " " + operacija.getSala().getBrojSale() 
					);
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
		
	}
	
	//SLANJE LEKARU DA JE ODOBRENA OPERACIJA

	public void slanjeLekaruOdobrenaOperacija(Operacija operacija) {
		System.out.println("Slanje emaila...");
		
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(operacija.getLekar().getEmail());
			System.out.println("MAIL PACIJENTA JE  " + operacija.getLekar().getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Odobren zahtev za operaciju");
			mail.setText("Postovani,\n\nZahtev za operaciju je odobren:\n\nDatum i vreme: "+ operacija.getVremeOperacije()+
					
					"\nLekar: " + (operacija.getLekar().getIme()) + " " + 
					(operacija.getLekar().getPrezime())+
					"\nPacijent: " + operacija.getPacijent().getIme() + " " + operacija.getPacijent().getPrezime() +
					"\nSala: " + operacija.getSala().getNazivSale() + " " + operacija.getSala().getBrojSale() 
					);
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
	}
	
	//SLANJE LEKARU/MED SESTRI DA JE ODOBRENO ODSUSTVO

	public void slanjeOdobrenoOdsustva(ZahtevOdsustva zahtev) {
		System.out.println("Slanje emaila...");
		
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(zahtev.getUser().getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Odobren zahtev za odsustvo");
			mail.setText("Postovani/Postovana " + zahtev.getUser().getIme() + ",\n\nVas zahtev za odsustvo je odobren:\n\nDatum i vreme pocetka: "+ zahtev.getDatumPocetka()+
					"\nDatum i vreme kraja: " + zahtev.getDatumKraja() +
					"\nRazlog odsustva: " + zahtev.getRazlog()
					);
			javaMailSender.send(mail);
			System.out.println("Email poslat!");
		}
		catch(Exception e) {
			System.out.println("Doslo je do greske...");
		}
	}
	
	//SLANJE LEKARU/MED SESTRI DA JE ODBIJENO ODSUSTVO

		public void slanjeOdbijenoOdsustva(ZahtevOdsustva zahtev, String razlog) {
			System.out.println("Slanje emaila...");
			
			try {
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setTo(zahtev.getUser().getEmail());
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setSubject("Odbijen zahtev za odsustvo");
				mail.setText("Postovani/Postovana " + zahtev.getUser().getIme() + ",\n\nVas zahtev za odsustvo je odbijen:\n\nDatum i vreme pocetka: "+ zahtev.getDatumPocetka()+
						"\nDatum i vreme kraja: " + zahtev.getDatumKraja() +
						"\nRazlog odsustva: " + zahtev.getRazlog() +
						"\nRazlog odbijanja Vaseg zahteva: " + razlog
						);
				javaMailSender.send(mail);
				System.out.println("Email poslat!");
			}
			catch(Exception e) {
				System.out.println("Doslo je do greske...");
			}
		}
}
