package com.sourcesense.crl.sidac;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sourcesense.crl.business.model.Abbinamento;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoSearch;
import com.sourcesense.crl.business.model.Aula;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.GestioneAbbinamento;
import com.sourcesense.crl.business.model.GestioneSedute;
import com.sourcesense.crl.business.model.Passaggio;
import com.sourcesense.crl.business.model.Seduta;
import com.sourcesense.crl.business.model.Target;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.service.AbbinamentoServiceManager;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.AulaServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.business.service.SeduteServiceManager;
import com.sourcesense.crl.business.service.UserServiceManager;
import com.sourcesense.crl.util.ServiceNotAvailableException;

public class MainApp {

	public static List<Commissione> commissioni = new ArrayList<Commissione>();
	static HashMap<String, String> hashComm = new HashMap<String, String>();

	public static void main(String[] args) throws Exception {

		// Recupero dati da DB
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:src/context.xml");
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("context.xml");
		DataExtractor dataExtractor = (DataExtractor) context.getBean("dataExtractor");
		List<Atto> listaAtti = dataExtractor.getAtti();
		System.out.println("TOTALE ATTI : " + listaAtti.size());

		User user = new User();
		user.setUsername("admin");
		user.setPassword("crl_123!");

		UserServiceManager userService = (UserServiceManager) context.getBean("userServiceManager");
		userService.authenticate(user);

		System.out.println("Carica Commissioni");
		CommissioneServiceManager comService = (CommissioneServiceManager) context.getBean("commissioneServiceManager");
		setCommissioni(comService.getAllCommissioni());

		AttoServiceManager attoService = (AttoServiceManager) context.getBean("attoServiceManager");
		AttoSearch attoSearch = new AttoSearch();
		attoSearch.setLegislatura("IX");
		List<Atto> listaAttiAlfresco = attoService.searchAtti(attoSearch);
		for (Atto atto : listaAtti) {

			for (Atto attoAlf : listaAttiAlfresco) {

				if (atto.getNumeroAtto().equals(attoAlf.getNumeroAtto()) && atto.getTipoAtto().equalsIgnoreCase(attoAlf.getTipo())) {

					atto.setId(attoAlf.getId());
				}

			}

		}

		hashComm.put("I Commissione - Programmazione e bilancio", "I Commissione Programmazione e Bilancio");
		hashComm.put("II Commissione - Affari istituzionali", "II Commissione Affari istituzionali");
		hashComm.put("III Commissione - Sanità e politiche sociali", "III Commissione Sanità e Assistenza");
		hashComm.put("IV Commissione - Attività produttive e occupazione", "IV Commissione Attività produttive e Occupazione");
		hashComm.put("V Commissione - Territorio e infrastrutture", "V Commissione Territorio");
		hashComm.put("VI Commissione - Ambiente e protezione civile", "VI Commissione Ambiente e Protezione civile");
		hashComm.put("VII Commissione - Cultura, istruzione, formazione, comunicazione e sport",
				"VII Commissione Cultura, Istruzione, Formazione professionale, Sport e Informazione");
		hashComm.put("VIII Commissione - Agricoltura, montagna, foreste e parchi", "VIII Commissione Agricoltura, Parchi e Risorse idriche");

		// System.out.println("* FASE UNO *");
		long startTime = System.currentTimeMillis();
		// faseUno(context, listaAtti);
		long endTime = System.currentTimeMillis();
		// System.out.println("*FINE FASE UNO*" + (endTime - startTime) / 60000
		// + " min ");

		// System.out.println("* FASE DUE *");
		// startTime = System.currentTimeMillis();
		// faseDue(context, listaAtti);
		// endTime = System.currentTimeMillis();
		// System.out.println("*FINE FASE DUE*" + (endTime - startTime) / 60000
		// + " min ");

		// System.out.println("* FASE TRE *");
		// startTime = System.currentTimeMillis();
		// faseTre(dataExtractor, context, listaAtti);
		// endTime = System.currentTimeMillis();
		// System.out.println("*FINE FASE TRE*" + (endTime - startTime) / 60000
		// + " min ");

		// System.out.println("* FASE QUATTRO *");
		// startTime = System.currentTimeMillis();
		// faseQuattro(dataExtractor, context, listaAtti, commissioni);
		// endTime = System.currentTimeMillis();
		// System.out.println("*FINE FASE QUATTRO*" + (endTime - startTime) /
		// 60000 + " min ");

		System.out.println("* FASE CINQUE *");
		startTime = System.currentTimeMillis();
		faseCinque(dataExtractor, context, listaAtti, commissioni);
		endTime = System.currentTimeMillis();
		System.out.println("*FINE FASE CINQUE*" + (endTime - startTime) / 60000 + " min ");

	}

	public static void faseCinque(DataExtractor dataExtractor, ApplicationContext context, List<Atto> atti, List<Commissione> commissioni) {

		System.out.println("TESTI ATTI");

		AttoServiceManager attoService = (AttoServiceManager) context.getBean("attoServiceManager");
		CommissioneServiceManager comService = (CommissioneServiceManager) context.getBean("commissioneServiceManager");
		AulaServiceManager aulaService = (AulaServiceManager) context.getBean("aulaServiceManager");

		long startTime = System.currentTimeMillis();

		for (Atto attoRec : atti) {

			try {

				List<TestoAtto> testi = dataExtractor.getTestiAtto(attoRec.getSidacId());

				String appoNome = "";
				String appoProvenienza = "";
				int conta = 2;

				for (TestoAtto testoAtto : testi) {

					if (appoNome.equals(attoRec.getTipoAtto() + "-" + attoRec.getNumeroAtto()) && appoProvenienza.equals(testoAtto.getAppoProvenienza())) {

						testoAtto.setNome(attoRec.getTipoAtto() + "-" + attoRec.getNumeroAtto()+" ("+conta+")");
						conta++;

					}else{
						
						testoAtto.setNome(attoRec.getTipoAtto() + "-" + attoRec.getNumeroAtto());
						conta = 2;		
					}

					appoProvenienza = testoAtto.getAppoProvenienza();
					appoNome = attoRec.getTipoAtto() + "-" + attoRec.getNumeroAtto();
					System.out.println("INSERIMENTO FILE : "+testoAtto.getNome());
					testoAtto.setPubblico(false);

					InputStream stream = new FileInputStream(testoAtto.getAppoPath());

					if (testoAtto.getAppoProvenienza().startsWith("USSC")) {

						attoService.uploadTestoAttoPresentazioneAssegnazione(attoRec, stream, testoAtto);
						System.out.println("Inserito file USSC per " + attoRec.getTipoAtto() + " - " + attoRec.getNumeroAtto());

					} else if (testoAtto.getAppoProvenienza().startsWith("COMM_")) {

						String idCommissione = testoAtto.getAppoProvenienza().substring(5);
						String commSidac = dataExtractor.getCommissioneSidac(idCommissione);

						for (Passaggio passaggio : attoRec.getPassaggi()) {

							testoAtto.setPassaggio(passaggio.getNome());

							for (Commissione commAppo : passaggio.getCommissioni()) {

								if (commAppo.getDescrizione().toLowerCase().startsWith(commSidac.toLowerCase())) {

									for (Commissione commOrig : commissioni) {

										if (commOrig.getDescrizione().toLowerCase().startsWith(commSidac.toLowerCase())) {
											testoAtto.setCommissione(hashComm.get(commOrig.getDescrizione()));
										}
									}

									comService.uploadTestoAttoVotatoEsameCommissioni(attoRec, stream, testoAtto);
									System.out.println("Inserito file " + commAppo.getDescrizione() + " per " + attoRec.getTipoAtto() + " - " + attoRec.getNumeroAtto());

								}

							}

						}

					} else if (testoAtto.getAppoProvenienza().startsWith("CONS")) {

						for (Passaggio passaggio : attoRec.getPassaggi()) {

							testoAtto.setPassaggio(passaggio.getNome());
							aulaService.uploadTestoAttoVotatoEsameAula(attoRec, stream, testoAtto);
							System.out.println("Inserito file AULA per " + attoRec.getTipoAtto() + " - " + attoRec.getNumeroAtto());
						}

					}

				}

			} catch (ServiceNotAvailableException snae) {

				System.out.println("ERRORE : Atto " + attoRec.getTipoAtto() + " - " + attoRec.getNumeroAtto() + "  id SIDAC :" + attoRec.getSidacId());
				System.out.println("ERRORE : URL = " + snae.getServiceName());
				System.out.println("ERRORE : EXC = ");
				snae.printStackTrace();
				continue;
			} catch (Exception e) {
				System.out.println("ERRORE : Atto " + attoRec.getNumeroAtto() + " - " + attoRec.getTipoAtto() + "  id SIDAC :" + attoRec.getSidacId());
				System.out.println("ERRORE : EXC = ");
				e.printStackTrace();
				continue;
			}

		}

		long endTime = System.currentTimeMillis();
		System.out.println("AGGIUNGI ABBINAMENTI END :" + (endTime - startTime) / 1000 + " sec");

	}

	public static void faseUno(ApplicationContext context, List<Atto> listaAtti) throws Exception {

		System.out.println("-SERVIZIO COMMISSIONI-");
		AttoServiceManager attoService = (AttoServiceManager) context.getBean("attoServiceManager");
		CommissioneServiceManager commissioneService = (CommissioneServiceManager) context.getBean("commissioneServiceManager");
		AulaServiceManager aulaService = (AulaServiceManager) context.getBean("aulaServiceManager");
		ObjectMapper objMap = new ObjectMapper();

		for (Atto atto : listaAtti) {

			try {

				System.out.println("Inserimento atto: " + atto.getTipoAtto() + "-" + atto.getNumeroAtto() + " Id Old:" + atto.getId());
				Atto attoAppo = attoService.persist(atto);
				atto.setId(attoAppo.getId());
				System.out.println(" Id New:" + atto.getId());

				if (atto.getDataPresaInCarico() != null) {

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

							System.out.println("TARGET:::" + objMap.writeValueAsString(target));
							System.out.println("ATTO:::" + objMap.writeValueAsString(atto));
							if (commissione.getDataPresaInCarico() != null) {

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

						}

						System.out.println("-AULA-");
						Aula aula = passaggio.getAula();
						Target target = new Target();
						target.setPassaggio(passaggio.getNome());
						EsameAula esameAula = new EsameAula();
						esameAula.setTarget(target);
						esameAula.setAtto(atto);
						if (aula.getDataPresaInCaricoEsameAula() != null) {
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
						}
						System.out.println("-CHIUSURA-");
						attoService.chiusuraAtto(atto);

					}
				}

			} catch (ServiceNotAvailableException snae) {

				System.out.println("ERRORE : Atto " + atto.getTipoAtto() + " - " + atto.getNumeroAtto() + "  id SIDAC :" + atto.getSidacId());
				System.out.println("ERRORE : URL = " + snae.getServiceName());
				System.out.println("ERRORE : EXC = ");
				snae.printStackTrace();
				continue;
			} catch (Exception e) {
				System.out.println("ERRORE : Atto " + atto.getNumeroAtto() + " - " + atto.getTipoAtto() + "  id SIDAC :" + atto.getSidacId());
				System.out.println("ERRORE : EXC = ");
				e.printStackTrace();
				continue;
			}
		}

	}

	public static void faseDue(ApplicationContext context, List<Atto> atti) {

		System.out.println("AGGIUNGI COLLEGAMENTI");
		AttoServiceManager attoService = (AttoServiceManager) context.getBean("attoServiceManager");
		long startTime = System.currentTimeMillis();
		for (Atto attoRec : atti) {

			try {

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

								if (integ.equals(atto.getNumeroAtto()) && tipoAtto.equals(atto.getTipoAtto())) {

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

			} catch (ServiceNotAvailableException snae) {

				System.out.println("ERRORE : Atto " + attoRec.getTipoAtto() + " - " + attoRec.getNumeroAtto() + "  id SIDAC :" + attoRec.getSidacId());
				System.out.println("ERRORE : URL = " + snae.getServiceName());
				System.out.println("ERRORE : EXC = ");
				snae.printStackTrace();
				continue;
			} catch (Exception e) {
				System.out.println("ERRORE : Atto " + attoRec.getNumeroAtto() + " - " + attoRec.getTipoAtto() + "  id SIDAC :" + attoRec.getSidacId());
				System.out.println("ERRORE : EXC = ");
				e.printStackTrace();
				continue;
			}

		}

		long endTime = System.currentTimeMillis();
		System.out.println("AGGIUNGI COLLEGAMENTI END :" + (endTime - startTime) / 1000 + " sec");

	}

	public static void faseTre(DataExtractor dataExtractor, ApplicationContext context, List<Atto> atti) {

		System.out.println("AGGIUNGI ABBINAMENTI");
		AbbinamentoServiceManager abbinamentoService = (AbbinamentoServiceManager) context.getBean("abbinamentoServiceManager");
		long startTime = System.currentTimeMillis();
		for (Atto attoRec : atti) {

			try {

				if (dataExtractor.getAbbinamenti(attoRec, atti)) {

					for (Passaggio passaggio : attoRec.getPassaggi()) {

						for (Abbinamento abbinamento : passaggio.getAbbinamenti()) {

							GestioneAbbinamento gestAbb = new GestioneAbbinamento();

							Target target = new Target();
							target.setPassaggio(passaggio.getNome());
							gestAbb.setAbbinamento(abbinamento);
							gestAbb.setTarget(target);
							abbinamentoService.salvaAbbinamento(gestAbb);

						}

					}

				}

			} catch (ServiceNotAvailableException snae) {

				System.out.println("ERRORE : Atto " + attoRec.getTipoAtto() + " - " + attoRec.getNumeroAtto() + "  id SIDAC :" + attoRec.getSidacId());
				System.out.println("ERRORE : URL = " + snae.getServiceName());
				System.out.println("ERRORE : EXC = ");
				snae.printStackTrace();
				continue;
			} catch (Exception e) {
				System.out.println("ERRORE : Atto " + attoRec.getNumeroAtto() + " - " + attoRec.getTipoAtto() + "  id SIDAC :" + attoRec.getSidacId());
				System.out.println("ERRORE : EXC = ");
				e.printStackTrace();
				continue;
			}

		}

		long endTime = System.currentTimeMillis();
		System.out.println("AGGIUNGI ABBINAMENTI END :" + (endTime - startTime) / 1000 + " sec");

	}

	public static void faseQuattro(DataExtractor dataExtractor, ApplicationContext context, List<Atto> atti, List<Commissione> commissioni) {

		System.out.println("AGGIUNGI SEDUTE COMMISSIONI");
		long startTime = System.currentTimeMillis();
		SeduteServiceManager seduteService = (SeduteServiceManager) context.getBean("seduteServiceManager");

		for (Commissione commissione : commissioni) {

			List<Seduta> sedute = null;

			try {

				sedute = dataExtractor.getSeduteCommissione(commissione.getDescrizione().substring(0, 3), atti);

			} catch (ServiceNotAvailableException snae) {

				System.out.println("ERRORE : Commissione " + commissione.getDescrizione());
				System.out.println("ERRORE : URL = " + snae.getServiceName());
				System.out.println("ERRORE : EXC = ");
				snae.printStackTrace();
				continue;

			} catch (Exception e) {
				System.out.println("ERRORE : Commissione " + commissione.getDescrizione());
				System.out.println("ERRORE : EXC = ");
				e.printStackTrace();
				continue;
			}

			for (Seduta seduta : sedute) {

				try {

					GestioneSedute gestioneSeduta = new GestioneSedute();
					Target target = new Target();
					target.setProvenienza(commissione.getDescrizione());
					gestioneSeduta.setTarget(target);
					gestioneSeduta.setSeduta(seduta);
					Seduta sedutaAppo = seduteService.salvaSeduta(gestioneSeduta);
					seduta.setIdSeduta(sedutaAppo.getIdSeduta());
					seduteService.salvaOdg(seduta);

				} catch (ServiceNotAvailableException snae) {

					System.out.println("ERRORE : Seduta " + seduta.getDataSeduta());
					System.out.println("ERRORE : URL = " + snae.getServiceName());
					System.out.println("ERRORE : EXC = ");
					snae.printStackTrace();
					continue;
				} catch (Exception e) {
					System.out.println("ERRORE : Seduta " + seduta.getDataSeduta());
					System.out.println("ERRORE : EXC = ");
					e.printStackTrace();
					continue;
				}

			}

		}

		long endTime = System.currentTimeMillis();
		System.out.println("AGGIUNGI SEDUTE COMMISSIONI END :" + (endTime - startTime) / 1000 + " sec");

		System.out.println("AGGIUNGI SEDUTE AULA");
		startTime = System.currentTimeMillis();

		List<Seduta> sedute;
		sedute = dataExtractor.getSeduteAula(atti);

		for (Seduta seduta : sedute) {

			try {

				GestioneSedute gestioneSeduta = new GestioneSedute();
				Target target = new Target();
				target.setProvenienza("Aula");
				gestioneSeduta.setTarget(target);
				gestioneSeduta.setSeduta(seduta);
				Seduta sedutaAppo = seduteService.salvaSeduta(gestioneSeduta);
				seduta.setIdSeduta(sedutaAppo.getIdSeduta());
				seduteService.salvaOdg(seduta);

			} catch (ServiceNotAvailableException snae) {

				System.out.println("ERRORE : Seduta " + seduta.getDataSeduta());
				System.out.println("ERRORE : URL = " + snae.getServiceName());
				System.out.println("ERRORE : EXC = ");
				snae.printStackTrace();
				continue;
			} catch (Exception e) {
				System.out.println("ERRORE : Seduta " + seduta.getDataSeduta());
				System.out.println("ERRORE : EXC = ");
				e.printStackTrace();
				continue;
			}
		}

		endTime = System.currentTimeMillis();
		System.out.println("AGGIUNGI SEDUTE AULA :" + (endTime - startTime) / 1000 + " sec");

	}

	public static void resetDescrizioneComm(Commissione commIn) {

		for (Commissione commissione : commissioni) {

			String inNumeroRomano = commIn.getDescrizione().split(" ")[0];

			if (commissione.getDescrizione().split(" ")[0].equalsIgnoreCase(inNumeroRomano)) {

				commIn.setDescrizione(commissione.getDescrizione());
				break;

			}
		}

		/*
		 * 
		 * 
		 * "I Commissione Programmazione e Bilancio",
		 * "II Commissione Affari istituzionali",
		 * "III Commissione Sanità e Assistenza",
		 * "IV Commissione Attività produttive e Occupazione",
		 * "V Commissione Territorio",
		 * "VI Commissione Ambiente e Protezione civile",
		 * "VII Commissione Cultura, Istruzione, Formazione professionale, Sport e Informazione"
		 * , "VIII Commissione Agricoltura, Parchi e Risorse idriche",
		 * "Giunta Regolamento"
		 * 
		 * I Commissione III Commissione VII Commissione I Commissione V
		 * Commissione IV COMMISSIONE VI Commissione VIII Commissione II
		 * Commissione
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
