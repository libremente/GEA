package com.sourcesense.crl.sidac;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Aula;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Passaggio;
import com.sourcesense.crl.business.model.Target;
import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.AulaServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.business.service.UserServiceManager;

public class MainApp {
	
	
	public static List<Commissione> commissioni = new ArrayList<Commissione>();
	

	public static void main(String[] args) throws Exception {

		// Recupero dati da DB
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		DataExtractor dataExtractor = (DataExtractor) context.getBean("dataExtractor");
		List<Atto> listaAtti = dataExtractor.getAtti();
		//System.out.println("TOTALE ATTI : " + listaAtti.size());

		User user = new User();
		user.setUsername("admin");
		user.setPassword("admin");

		UserServiceManager userService = (UserServiceManager) context.getBean("userServiceManager");
		userService.authenticate(user);
        
		System.out.println("Carica Commissioni");
		CommissioneServiceManager comService = (CommissioneServiceManager) context.getBean("commissioneServiceManager");
		setCommissioni(comService.getAllCommissioni());
		
		
		System.out.println("* FASE UNO *");
		long startTime = System.currentTimeMillis();
		faseUno(context, listaAtti);
		long endTime = System.currentTimeMillis();
		System.out.println("*FINE FASE UNO*" + (endTime - startTime) / 60000 + " min ");

		System.out.println("* FASE DUE *");
		startTime = System.currentTimeMillis();
		faseUno(context, listaAtti);
		endTime = System.currentTimeMillis();
		System.out.println("*FINE FASE DUE*" + (endTime - startTime) / 60000 + " min ");

	}

	public static void faseUno(ApplicationContext context, List<Atto> listaAtti) throws Exception {

		System.out.println("-SERVIZIO COMMISSIONI-");
		AttoServiceManager attoService = (AttoServiceManager) context.getBean("attoServiceManager");
		CommissioneServiceManager commissioneService = (CommissioneServiceManager) context.getBean("commissioneServiceManager");
		AulaServiceManager aulaService = (AulaServiceManager) context.getBean("aulaServiceManager");
		ObjectMapper objMap = new ObjectMapper();

		int contaIns = 1;
		for (Atto atto : listaAtti) {

			if (contaIns == 20) {
				// TODO solo TEST
				break;
			}

			System.out.println("Inserimento atto: " + atto.getTipoAtto() + "-" + atto.getNumeroAtto()+ " Id Old:"+atto.getId());
			Atto attoAppo = attoService.persist(atto);
			atto.setId(attoAppo.getId());
			System.out.println(" Id New:"+atto.getId());
			System.out.println("Presa in carico");
			attoService.presaInCaricoSC(atto);
			Thread.sleep(1500);
			System.out.println("Assegnazione Presentazione");
			attoService.salvaInfoGeneraliPresentazione(atto);
			System.out.println("Ammissibilità");
			attoService.salvaAmmissibilitaPresentazione(atto);
			
			for (Passaggio passaggio : atto.getPassaggi()) {
				for (Commissione commissione : passaggio.getCommissioni()) {
					resetDescrizioneComm(commissione);
				}
			}
			
			System.out.println("Proposta assegnazione");
			attoService.salvaAssegnazionePresentazione(atto);

			for (Passaggio passaggio : atto.getPassaggi()) {

				System.out.println(" Passaggio : " + passaggio.getNome());
				System.out.println("-COMMISSIONI-");

				for (Commissione commissione : passaggio.getCommissioni()) {

					
					System.out.println("Commissione :" + commissione.getDescrizione() + " - " + commissione.getRuolo());
					
					Target target = new Target();
					target.setCommissione(commissione.getDescrizione());
					target.setPassaggio(passaggio.getNome());
					EsameCommissione esameCommissione = new EsameCommissione();
					esameCommissione.setAtto(atto);
					esameCommissione.setTarget(target);
					
					System.out.println("TARGET:::"+objMap.writeValueAsString(target));
					System.out.println("ATTO:::"+objMap.writeValueAsString(atto));
					System.out.println("Presa in Carico");
					commissioneService.salvaPresaInCaricoEsameCommissioni(esameCommissione);
					System.out.println("Votato");
					commissioneService.salvaVotazioneEsameCommissioni(esameCommissione);
					System.out.println("Trasmesso");
					commissioneService.salvaTrasmissione(esameCommissione);
					System.out.println("Note Allegati");
					commissioneService.salvaNoteAllegatiEsameCommissioni(esameCommissione);

					// REFERENTE
					if (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())) {

						System.out.println("Nomina relatore");
						commissioneService.salvaRelatoriEsameCommissioni(esameCommissione);
						System.out.println("Comitato Ristretto");
						commissioneService.salvaComitatoRistrettoEsameCommissioni(esameCommissione);
						System.out.println("Stralci");
						commissioneService.salvaStralci(esameCommissione);

					}

				}

				System.out.println("-AULA-");
				Aula aula = passaggio.getAula();
				Target target = new Target();
				target.setPassaggio(passaggio.getNome());
				EsameAula esameAula = new EsameAula();
				esameAula.setTarget(target);
				esameAula.setAtto(atto);
				System.out.println("Presa in Carico");
				aulaService.presaInCarico(esameAula);
				System.out.println("Votazione");
				aulaService.salvaVotazioneEsameAula(esameAula);
				System.out.println("Stralci");
				aulaService.salvaStralciEsameAula(esameAula);
				System.out.println("Rinvio");
				aulaService.salvaRinvioEsameEsameAula(esameAula);
				System.out.println("Note e allegati");
				aulaService.salvaNoteAllegatiEsameAula(esameAula);
				System.out.println("-CHIUSURA-");
				attoService.chiusuraAtto(atto);

			}

			contaIns++;

		}

	}

	public static void faseDue(ApplicationContext context, List<Atto> atti) {

		System.out.println("AGGIUNGI COLLEGAMENTI");
		AttoServiceManager attoService = (AttoServiceManager) context.getBean("attoServiceManager");
		long startTime = System.currentTimeMillis();
		for (Atto attoRec : atti) {

			if (attoRec.getAppoCollegamenti() != null && !"".equals(attoRec.getAppoCollegamenti())) {

				Hashtable<String, ArrayList<String>> hash = Util.extractingNumbers(attoRec.getAppoCollegamenti());
				if (hash != null) {

					String tipoAtto = "";

					for (String string : hash.keySet()) {

						tipoAtto = string;
					}

					String response = "Atto " + attoRec.getId() + " Collegato con " + tipoAtto + ": ";

					for (String integ : hash.get(tipoAtto)) {

						for (Atto atto : atti) {

							if (atto.getNumeroAtto().equals(integ) && atto.getTipoAtto().equals(tipoAtto)) {

								Collegamento collegamento = new Collegamento();
								collegamento.setIdAttoCollegato(atto.getId());
								collegamento.setNumeroAttoCollegato(atto.getNumeroAtto());
								collegamento.setTipoAttoCollegato(atto.getTipoAtto());
								response += " " + atto.getNumeroAtto();
								attoRec.getCollegamenti().add(collegamento);

							}

						}

					}
					System.out.println(response);
					attoService.salvaCollegamenti(attoRec);

				}
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("AGGIUNGI COLLEGAMENTI END :" + (endTime - startTime) / 1000 + " sec");

	}

	
	
	
	
	
	public static void resetDescrizioneComm(Commissione commIn) {
		
		for (Commissione commissione : commissioni) {
			
			String inNumeroRomano = commIn.getDescrizione().split(" ")[0];
			
			if(commissione.getDescrizione().split(" ")[0].equalsIgnoreCase(inNumeroRomano)){
				
				commIn.setDescrizione(commissione.getDescrizione());
				break;
				
			}
		}
		
		/*
		 * 
		 * 
	"I Commissione Programmazione e Bilancio",
	"II Commissione Affari istituzionali",
	"III Commissione Sanità e Assistenza",
	"IV Commissione Attività produttive e Occupazione",
	"V Commissione Territorio",
	"VI Commissione Ambiente e Protezione civile",
	"VII Commissione Cultura, Istruzione, Formazione professionale, Sport e Informazione",
	"VIII Commissione Agricoltura, Parchi e Risorse idriche",
	"Giunta Regolamento"

    I Commissione
    III Commissione
    VII Commissione
    I Commissione
    V Commissione
    IV COMMISSIONE
    VI Commissione
    VIII Commissione
    II Commissione
    
    
    */
	}

	public static List<Commissione> getCommissioni() {
		return commissioni;
	}

	public static void setCommissioni(List<Commissione> commissioni) {
		MainApp.commissioni = commissioni;
	}
	
	
	
	

	// ANAGRAFICHE COMMISSIONI
	// Consiglio Regionale
	// I Commissione Programmazione e Bilancio
	// Giunta regionale
	// Ufficio di Presidenza del Consiglio regionale
	// Giunta delle elezioni
	// II Commissione Affari istituzionali
	// III Commissione Sanità e Assistenza
	// IV Commissione Attività produttive e Occupazione
	// V Commissione Territorio
	// VI Commissione Ambiente e Protezione civile
	// VII Commissione Cultura, Istruzione, Formazione professionale, Sport e
	// Informazione
	// VIII Commissione Agricoltura, Parchi e Risorse idriche
	// VII Commissione Cultura,Istruzione,Formaz.prof.,Sp
	// Comitato paritetico di controllo e valutazione
	// Commissione d'inchiesta Fondazione S.Raffaele
	// Commissione d'inchiesta discarica Cappella Cantone
	// PPDLL84/142/148 Legge elettorale
	// Commissione speciale carceri
	// Commissione d'inchiesta Sesto San Giovanni
	// PPDLL167/145/151 Tutela animali fini scientifici
	// Giunta Regolamento

}
