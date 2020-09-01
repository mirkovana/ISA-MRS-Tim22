package com.example.KCApp.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.ZahtevOdsustva;
import com.example.KCApp.beans.ZahtevZaOperaciju;
import com.example.KCApp.beans.ZahtevZaPregled;
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
	
	@Autowired
	private KlinikaService klinikaService;
	
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
		
		//SLANJE ADMINU KLINIKE DA JE DODAT NOVI ZAHTEV ZA PREGLED

		public void slanjeZahtevaZPAdminu(ZahtevZaPregled zahtev) {
			System.out.println("Slanje emaila...");
			
			try {
				SimpleMailMessage mail = new SimpleMailMessage();
				List<Klinika> klinike = klinikaService.listAll();
				for (Klinika k : klinike) {
					if (k.getIdKlinike() == zahtev.getKlinika().getIdKlinike()) {
						Set<AdministratorKlinike> admini = k.getAdministratoriKlinike();
						for (AdministratorKlinike admin : admini) {
							if (admin.getKlinika().getIdKlinike() == k.getIdKlinike()) {
								System.out.println("MEJL ADMINA" + admin.getEmail());
								mail.setTo(admin.getEmail());
							}
						}
					}
				}
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setSubject("Novi zahtev za pregled");
				mail.setText("Postovani/Postovana,\n\nPristigao je nov zahtev za pregled:\n\nDatum i vreme pocetka: "+ zahtev.getVreme()+
						"\nPacijent: " + zahtev.getPacijent().getIme() + " " + zahtev.getPacijent().getPrezime() +
						"\nLekar: " + zahtev.getLekar().getIme() + " " + zahtev.getLekar().getPrezime() +
						"\nTip pregleda: " + zahtev.getTipPregleda() +
						"\nCena: " + zahtev.getCena()
						);
				javaMailSender.send(mail);
				System.out.println("Email poslat!");
			}
			catch(Exception e) {
				System.out.println("Doslo je do greske...");
			}
		}
		
		//SLANJE ADMINU KLINIKE DA JE DODAT NOVI ZAHTEV ZA OPERACIJU

		public void slanjeZahtevaZOpAdminu(ZahtevZaOperaciju zahtev) {
			System.out.println("Slanje emaila...");
			
			try {
				SimpleMailMessage mail = new SimpleMailMessage();
				List<Klinika> klinike = klinikaService.listAll();
				for (Klinika k : klinike) {
					if (k.getIdKlinike() == zahtev.getKlinika().getIdKlinike()) {
						Set<AdministratorKlinike> admini = k.getAdministratoriKlinike();
						for (AdministratorKlinike admin : admini) {
							if (admin.getKlinika().getIdKlinike() == k.getIdKlinike()) {
								System.out.println("MEJL ADMINA" + admin.getEmail());
								mail.setTo(admin.getEmail());
							}
						}
					}
				}
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setSubject("Novi zahtev za operaciju");
				mail.setText("Postovani/Postovana,\n\nPristigao je nov zahtev za operaciju:\n\nDatum i vreme pocetka: "+ zahtev.getVreme()+
						"\nPacijent: " + zahtev.getPacijent().getIme() + " " + zahtev.getPacijent().getPrezime() +
						"\nLekar: " + zahtev.getLekar().getIme() + " " + zahtev.getLekar().getPrezime() +
						"\nDodatne informacije o operaciji: " + zahtev.getDodatneInfoOOperaciji()
						);
				javaMailSender.send(mail);
				System.out.println("Email poslat!");
			}
			catch(Exception e) {
				System.out.println("Doslo je do greske...");
			}
		}
		
		//SLANJE MEJLA PACIJENTU DA JE ODOBREN PREGLED
		public void slanjePacijentuOdobrenPregled(Pregled pregled) {
			System.out.println("Slanje emaila...");
		
			try {
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setTo(pregled.getPacijent().getEmail());
				System.out.println("MAIL PACIJENTA JE  " + pregled.getPacijent().getEmail());
				mail.setFrom(env.getProperty("spring.mail.username"));
				mail.setSubject("Odobren zahtev za pregled");
				mail.setText("Postovani,\n\nZahtev za pregled je odobren:\n\nDatum i vreme: "+ pregled.getVreme()+
						
						"\nLekar: " + (pregled.getLekar().getIme()) + " " + 
						(pregled.getLekar().getPrezime())+
						"\nPacijent: " + pregled.getPacijent().getIme() + " " + pregled.getPacijent().getPrezime() + 
						"\nSala: " + pregled.getSala().getNazivSale() + " " + pregled.getSala().getBrojSale() +
						"\nUkoliko Vam termin ne odgovara pregled mozete otkazati na svom profilu, u odeljku Zakazani pregledi."
						);
				javaMailSender.send(mail);
				System.out.println("Email poslat!");
			}
			catch(Exception e) {
				System.out.println("Doslo je do greske...");
			}
		}
}
