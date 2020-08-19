package com.example.KCApp.verifikacijaEmaila;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.VerificationToken;
import com.example.KCApp.service.EmailService;
import com.example.KCApp.service.VerificationTokenService;

@Component 
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private VerificationTokenService tokenService;
	@Autowired
	private EmailService emailService;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);

	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		try {

			Pacijent user = event.getUser();
			
			String token = UUID.randomUUID().toString();

			VerificationToken newUserToken = new VerificationToken(token, user);

			tokenService.save(newUserToken);

			String recipient = user.getEmail();
			String subject = "Potvrda registracije";
			String url = event.getAppUrl() + "/potvrdiRegistraciju/" + token;

			emailService.sendNotificaitionAsync(url, recipient, subject);

		} catch (Exception e) {
			System.out.println("Doslo je do greske!");
		}

	}

}