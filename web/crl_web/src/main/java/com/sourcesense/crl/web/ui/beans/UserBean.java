package com.sourcesense.crl.web.ui.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sourcesense.crl.business.model.ColonnaAtto;
import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.User;

;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

	/**
	 * /* Tipo atto N° atto Oggetto Tipo iniziativa Firmatari Stato Tipo
	 * chiusura Com. referente, co-referente, redigente o deliberante Data
	 * assegnazione Commissioni consultive Relatore Data nomina relatore
	 * Abbinamenti "Esito votazione commissione referente (o redigente o
	 * deliberante)"	"Data votazione commissione"	Data scadenza	"Data richiesta
	 * iscrizione all'aula" Esito votazione Aula "Data votazione aula" N° DCR N°
	 * LCR BURL Data BURL N° LR Data LR
	 */

	private static final long serialVersionUID = 7726122743894534255L;

	private User user = new User();

	private String username;

	private String password;

	private String sessionToken;

	private List<ColonnaAtto> colonneTotali = new ArrayList<ColonnaAtto>();

	private List<ColonnaAtto> colonneUser = new ArrayList<ColonnaAtto>();

	@PostConstruct
	public void init() {

		/*colonneTotali.add(new ColonnaAtto("Tipo atto","tipo", true ));
		colonneTotali.add(new ColonnaAtto("N° atto","numeroAtto", true));
		
		
		
		*/
		colonneTotali.add(new ColonnaAtto("Oggetto","oggetto", true));
		colonneTotali.add(new ColonnaAtto("Data presentazione","dataPresentazione", true));
		colonneTotali.add(new ColonnaAtto("Stato","stato", true));
		colonneTotali.add(new ColonnaAtto("Firmatari","elencoFirmatari", false));
		colonneTotali.add(new ColonnaAtto("Tipo iniziativa","tipoIniziativa", false));
		colonneTotali.add(new ColonnaAtto("Tipo chiusura","tipoChiusura", false));
		colonneTotali.add(new ColonnaAtto("Com. referente, co-referente, redigente o deliberante","commissioniNonConsultive", false));
		colonneTotali.add(new ColonnaAtto("Data assegnazione","dataAssegnazione", false));
		colonneTotali.add(new ColonnaAtto("Commissioni consultive","commissioniConsultive", false));
		colonneTotali.add(new ColonnaAtto("Relatore","relatore", false));
		colonneTotali.add(new ColonnaAtto("Data nomina relatore","dataNominaRelatore", false));
		colonneTotali.add(new ColonnaAtto("Abbinamenti","elencoAbbinamenti", false));
		colonneTotali.add(new ColonnaAtto("Esito votazione commissione referente (o redigente o deliberante)","esitoVotazioneCommissioneReferente",false));
		colonneTotali.add(new ColonnaAtto("Data votazione commissione","dataVotazioneCommissione", false));
		colonneTotali.add(new ColonnaAtto("Data scadenza", "dataScadenza",false));
		colonneTotali.add(new ColonnaAtto("Data richiesta iscrizione all'aula","dataRichiestaIscrizioneAula",false));
		colonneTotali.add(new ColonnaAtto("Esito votazione Aula","esitoVotazioneAula", false));
		colonneTotali.add(new ColonnaAtto("Data votazione aula","dataVotazioneAula", false));
		colonneTotali.add(new ColonnaAtto("N° DCR","numeroDcr", false));
		colonneTotali.add(new ColonnaAtto("N° LCR","numeroLcr", false));
		colonneTotali.add(new ColonnaAtto("BURL","numeroPubblicazioneBURL", false));
		colonneTotali.add(new ColonnaAtto("Data BURL","dataPubblicazioneBURL", false));
		colonneTotali.add(new ColonnaAtto("N° LR","numeroLr", false));
		colonneTotali.add(new ColonnaAtto("Data LR","dataLR", false));
		colonneTotali.add(new ColonnaAtto("Note generali","notePresentazioneAssegnazione", false));  
		refreshColonneUser();
		
	}

	public String getUserGroupName() {

		return user.getSessionGroup().getNome();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return user.getUsername();
	}

	public void setUsername(String username) {
		this.user.setUsername(username);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		this.user.setPassword(password);
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public List<ColonnaAtto> getColonneTotali() {
		return colonneTotali;
	}

	public void setColonneTotali(List<ColonnaAtto> colonneTotali) {
		this.colonneTotali = colonneTotali;
	}

	public List<ColonnaAtto> getColonneUser() {
		return colonneUser;
	}

	public void setColonneUser(List<ColonnaAtto> colonneUser) {
		this.colonneUser = colonneUser;
	}
	
	public void refreshColonneUser(){
		
		colonneUser.clear();
		
		for (ColonnaAtto colonnaAtto : colonneTotali) {
			if(colonnaAtto.isVisible()){
				colonneUser.add(colonnaAtto);
			}
		} 
	}
	

}
