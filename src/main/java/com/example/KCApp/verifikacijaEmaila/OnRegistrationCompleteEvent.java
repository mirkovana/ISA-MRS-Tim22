package com.example.KCApp.verifikacijaEmaila;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.example.KCApp.beans.Pacijent;


public class OnRegistrationCompleteEvent extends ApplicationEvent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appUrl;
    private Locale locale;
    private Pacijent user;
    
	public OnRegistrationCompleteEvent(Pacijent pacijent, Locale lokalni, String putanja) {
		super(pacijent);
		this.user = pacijent;
		this.locale = lokalni;
		this.appUrl = putanja;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Pacijent getUser() {
		return user;
	}

	public void setUser(Pacijent user) {
		this.user = user;
	}
	
}
