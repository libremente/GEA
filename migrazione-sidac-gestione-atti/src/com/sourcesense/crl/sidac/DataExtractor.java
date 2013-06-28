package com.sourcesense.crl.sidac;

import javax.sql.DataSource;

import com.sourcesense.crl.business.model.Abbinamento;
import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoTrattato;
import com.sourcesense.crl.business.model.Aula;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.CollegamentoLeggiRegionali;
import com.sourcesense.crl.business.model.ComitatoRistretto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.Componente;
import com.sourcesense.crl.business.model.Consultazione;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.OrganismoStatutario;
import com.sourcesense.crl.business.model.Organo;
import com.sourcesense.crl.business.model.Passaggio;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.model.Seduta;
import com.sourcesense.crl.business.model.TestoAtto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;

public class DataExtractor {

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public List<TestoAtto> getTestiAtto(String idAtto) {
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(Constant.QUERY_TESTI);
			ps.setString(1, idAtto);
			ResultSet rs = ps.executeQuery();
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			List<TestoAtto> testi = new ArrayList<TestoAtto>();
			System.out.println("QUERY TESTI per atto "+idAtto);
			
			while (rs.next()) {
                    
				TestoAtto testo= new TestoAtto();
				testo.setAppoPath(rs.getString(Constant.TEST_COLUMN_PATH).replace("D:", "F:"));
				testo.setAppoProvenienza(rs.getString(Constant.TEST_COLUMN_PROVENIENZA));
				testo.setAppoTitolo(rs.getString(Constant.TEST_COLUMN_TITOLO));
				testi.add(testo);
			}

			rs.close();
			ps.close();
			endTime = System.currentTimeMillis();
			System.out.println("QUERY TESTI :" + (endTime - startTime) / 1000 + " sec");

			return testi;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// Do nothing...
				}
			}
		}
		
	}
	
	
	
	public String getCommissioneSidac(String idCommissione){
		
		Connection conn = null;
        System.out.println("Get Commissione :"+idCommissione); 
		
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select descrizione from COMMISSIONI where id=?");
			ps.setString(1, idCommissione);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
			
				return rs.getString("descrizione");
			}
			rs.close();
			ps.close();
			return "";

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// Do nothing...
				}
			}
		}
		
	}
	
	
	
	public List<Seduta> getSeduteAula(List<Atto> atti) {

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(Constant.QUERY_SEDUTE_AULA);
			ResultSet rs = ps.executeQuery();
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			List<Seduta> sedute = new ArrayList<Seduta>();
			System.out.println("QUERY SEDUTE AULA");

			String appoIdSeduta = "";
			Seduta seduta = null;
			while (rs.next()) {

				// Stessa seduta
				if (appoIdSeduta.equals(rs.getString(Constant.SED_COLUMN_ID_SEDUTA))) {

					for (Atto attoRec : atti) {

						String numeroAtt = rs.getString(Constant.SED_COLUMN_NUM_ATTO);
						String tipoAtt = rs.getString(Constant.SED_COLUMN_TIPO_ATTO);

						if (numeroAtt.equals(attoRec.getNumeroAtto()) && tipoAtt.equals(attoRec.getTipoAtto())) {

							AttoTrattato attoTratt = new AttoTrattato();
							attoTratt.setAtto(attoRec);
							attoTratt.setDiscusso(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_DISCUSSO)));
							attoTratt.setPrevisto(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_PREVISTO)));
							sedute.get(sedute.size()-1).getAttiTrattati().add(attoTratt);
							break;
						}
					}

					// Nuova seduta
				} else {

				
					seduta = new Seduta();
					seduta.setDalleOre(rs.getDate(Constant.SED_COLUMN_ORA_INIZIO));
					seduta.setDataSeduta(rs.getDate(Constant.SED_COLUMN_DATA));
					seduta.setNumVerbale(rs.getString(Constant.SED_COLUMN_NUM_VERBALE));
					seduta.setLegislatura("IX");
					for (Atto attoRec : atti) {

						String numeroAtt = rs.getString(Constant.SED_COLUMN_NUM_ATTO);
						String tipoAtt = rs.getString(Constant.SED_COLUMN_TIPO_ATTO);

						if (numeroAtt.equals(attoRec.getNumeroAtto()) && tipoAtt.equals(attoRec.getTipoAtto())) {

							AttoTrattato attoTratt = new AttoTrattato();
							attoTratt.setAtto(attoRec);
							attoTratt.setDiscusso(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_DISCUSSO)));
							attoTratt.setPrevisto(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_PREVISTO)));
							seduta.getAttiTrattati().add(attoTratt);
							break;
						}
						
					}
					
					sedute.add(seduta);

				}

				appoIdSeduta = rs.getString(Constant.SED_COLUMN_ID_SEDUTA);

			}

			rs.close();
			ps.close();
			endTime = System.currentTimeMillis();
			System.out.println("QUERY SEDUTE AULA :" + (endTime - startTime) / 1000 + " sec");

			return sedute;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// Do nothing...
				}
			}
		}

	}
	

	public List<Seduta> getSeduteCommissione(String commissioneInitials, List<Atto> atti) {

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(Constant.QUERY_SEDUTE_COMM);
			ps.setString(1, commissioneInitials);
			ResultSet rs = ps.executeQuery();
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			List<Seduta> sedute = new ArrayList<Seduta>();
			System.out.println("QUERY SEDUTE COMM");

			String appoIdSeduta = "";
			Seduta seduta = new Seduta();
			while (rs.next()) {

				// Stessa seduta
				if (appoIdSeduta.equals(rs.getString(Constant.SED_COLUMN_ID_SEDUTA))) {

					for (Atto attoRec : atti) {

						String numeroAtt = rs.getString(Constant.SED_COLUMN_NUM_ATTO);
						String tipoAtt = rs.getString(Constant.SED_COLUMN_TIPO_ATTO);

						if (numeroAtt.equals(attoRec.getNumeroAtto()) && tipoAtt.equals(attoRec.getTipoAtto())) {

							AttoTrattato attoTratt = new AttoTrattato();
							attoTratt.setAtto(attoRec);
							attoTratt.setDiscusso(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_DISCUSSO)));
							attoTratt.setPrevisto(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_PREVISTO)));
							sedute.get(sedute.size()-1).getAttiTrattati().add(attoTratt);
							break;
						}
					}

					// Nuova seduta
				} else {

					

					seduta = new Seduta();
					seduta.setDalleOre(rs.getDate(Constant.SED_COLUMN_ORA_INIZIO));
					seduta.setDataSeduta(rs.getDate(Constant.SED_COLUMN_DATA));
					seduta.setNumVerbale(rs.getString(Constant.SED_COLUMN_NUM_VERBALE));
					seduta.setLegislatura("IX");
					for (Atto attoRec : atti) {

						String numeroAtt = rs.getString(Constant.SED_COLUMN_NUM_ATTO);
						String tipoAtt = rs.getString(Constant.SED_COLUMN_TIPO_ATTO);

						if (numeroAtt.equals(attoRec.getNumeroAtto()) && tipoAtt.equals(attoRec.getTipoAtto())) {

							AttoTrattato attoTratt = new AttoTrattato();
							attoTratt.setAtto(attoRec);
							attoTratt.setDiscusso(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_DISCUSSO)));
							attoTratt.setPrevisto(Util.getBooleanFromChar(rs.getString(Constant.SED_COLUMN_PREVISTO)));
							seduta.getAttiTrattati().add(attoTratt);
							sedute.add(seduta);
							break;
						}
						
					}

				}

				appoIdSeduta = rs.getString(Constant.SED_COLUMN_ID_SEDUTA);

			}

			rs.close();
			ps.close();
			endTime = System.currentTimeMillis();
			System.out.println("QUERY SEDUTE COMM :" + (endTime - startTime) / 1000 + " sec");

			return sedute;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// Do nothing...
				}
			}
		}

	}

	public boolean getAbbinamenti(Atto attoIn, List<Atto> atti) {
		Connection conn = null;
		boolean hasAbbinamenti = false;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(Constant.QUERY_ABBINAMENTI_ATTO);
			ps.setString(1, attoIn.getSidacId());
			ResultSet rs = ps.executeQuery();
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			List<Abbinamento> abbinamenti = new ArrayList<Abbinamento>();
			System.out.println("QUERY ABBINAMENTI");

			while (rs.next()) {

				// Alcuni atti sono abbinati a loro stessi
				if (!rs.getString(Constant.ABB_COLUMN_ID).equals(attoIn.getSidacId())) {

					hasAbbinamenti = true;

					String numeroAbb = rs.getString(Constant.ABB_COLUMN_NUM_ATTO);
					String tipoAbb = rs.getString(Constant.ABB_COLUMN_TIPO);

					Abbinamento abbinamento = new Abbinamento();
					abbinamento.setDataAbbinamento(rs.getDate(Constant.ABB_COLUMN_DATA));
					abbinamento.setDataDisabbinamento(rs.getDate(Constant.ABB_COLUMN_ANNULLO_DATA));
					abbinamento.setIdAtto(attoIn.getId());

					for (Atto attoRec : atti) {

						if (numeroAbb.equals(attoRec.getNumeroAtto()) && tipoAbb.equals(attoRec.getTipoAtto())) {

							abbinamento.setIdAttoAbbinato(attoRec.getId());
							break;

						}

					}

					abbinamento.setNote(rs.getString(Constant.ABB_COLUMN_NOTE));
					abbinamento.setNumeroAttoAbbinato(numeroAbb);
					abbinamento.setTipoAttoAbbinato(tipoAbb);
					abbinamento.setTipoTesto(convertTipoAbbinamento(rs.getString(Constant.ABB_COLUMN_TIPO_ABB)));
					abbinamenti.add(abbinamento);
				}

			}

			if (hasAbbinamenti) {

				for (Passaggio passag : attoIn.getPassaggi()) {

					passag.setAbbinamenti(abbinamenti);

				}

			}

			rs.close();
			ps.close();
			endTime = System.currentTimeMillis();
			System.out.println("QUERY ABBINAMENTI :" + (endTime - startTime) / 1000 + " sec");

			return hasAbbinamenti;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// Do nothing...
				}
			}
		}

	}

	public List<Atto> getAtti() {

		Connection conn = null;
		try {

			long startTime = System.currentTimeMillis();
			long endTime = 0;

			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(Constant.QUERY_ALL_ATTI);
			ResultSet rs = ps.executeQuery();
			Atto atto = new Atto();
			List<Atto> atti = new ArrayList<Atto>();

			System.out.println("QUERY ATTI e AULA");

			while (rs.next()) {

				// Controllo se più passaggi
				if (rs.getString(Constant.COLUMN_NUM_ATTO).equals(atto.getNumeroAtto()) && rs.getString(Constant.COLUMN_TIPO_ATTO).equals(atto.getTipoAtto())) {
					// Passaggio in più
					System.out.println("più passaggi per " + atto.getId());

					Passaggio passaggio = new Passaggio();
					passaggio.setNome("Passaggio" + rs.getInt("NUM_PASSAGGIO"));
					atto.getPassaggi().add(passaggio);

					// Numero e data LR e BURL vanno su Atto
					atto.setNumeroLr(rs.getString(Constant.DC_COLUMN_NUM_LR));
					atto.setDataLR(rs.getDate(Constant.DC_COLUMN_DATA_LR));
					atto.setDataPubblicazioneBURL(rs.getDate(Constant.DC_COLUMN_DATA_BURL));
					atto.setNumeroPubblicazioneBURL(rs.getString(Constant.DC_COLUMN_RIF_BURL));

					Aula aula = passaggio.getAula();
					aula.setDataPresaInCaricoEsameAula(rs.getDate(Constant.PC_COLUMN_DATA_ARRIVO_AULA));
					aula.setNoteGeneraliEsameAula(rs.getString(Constant.PC_COLUMN_ANNOTAZIONI));
					aula.setQuorumEsameAula(rs.getString(Constant.DC_COLUMN_QUORUM));
					aula.setNumeroDcr(rs.getString(Constant.DC_COLUMN_NUM_DCR));
					aula.setNumeroLcr(rs.getString(Constant.DC_COLUMN_NUM_LCR));
					aula.setEmendato(Util.getBooleanFromChar(rs.getString(Constant.DC_COLUMN_EMENDATO)));
					aula.setEsitoVotoAula(rs.getString(Constant.DC_COLUMN_VOTO_AULA));
					aula.setMotivazioneRinvio(rs.getString(Constant.DC_COLUMN_MOTIVAZIONE_RINVIO));
					// aula.set
					// private Date dataPresaInCaricoEsameAula;
					// private String relazioneScritta;

					// private String tipologiaVotazione;
					// private Date dataSedutaAula;
					//
					//
					// private String noteVotazione;
					//
					// private List<TestoAtto> testiAttoVotatoEsameAula = new
					// ArrayList<TestoAtto>();
					// private List<Allegato> emendamentiEsameAula = new
					// ArrayList<Allegato>();
					// private List<Allegato> allegatiEsameAula = new
					// ArrayList<Allegato>();
					//
					// private Integer numEmendPresentatiMaggiorEsameAula;
					// private Integer numEmendPresentatiMinorEsameAula;
					// private Integer numEmendPresentatiGiuntaEsameAula;
					// private Integer numEmendPresentatiMistoEsameAula;
					// private Integer numEmendApprovatiMaggiorEsameAula;
					// private Integer numEmendApprovatiMinorEsameAula;
					// private Integer numEmendApprovatiGiuntaEsameAula;
					// private Integer numEmendApprovatiMistoEsameAula;
					// private Integer nonAmmissibiliEsameAula;
					// private Integer decadutiEsameAula;
					// private Integer ritiratiEsameAula;
					// private Integer respintiEsameAula;
					// private Integer totaleNonApprovatiEsameAula;
					// private String noteEmendamentiEsameAula;
					//
					// private Date dataSedutaRinvio;
					// private Date dataTermineMassimo;
					// private String motivazioneRinvio;
					//
					// private Date dataSedutaStralcio;
					// private Date dataStralcio;
					// private Date dataIniziativaStralcio;
					//
					// private String articoli;
					// private String noteStralcio;

					//
					// private String numeroReg;
					//
					// private List<Link> linksEsameAula = new
					// ArrayList<Link>();
					//
					// private String noteGeneraliEsameAula;
					//
					// private boolean rinvioCommBilancio;

				} else {

					atto = new Atto();
					// SERVIZIO COMMISSIONI
					atto.setId(rs.getString(Constant.COLUMN_ID_ATTO));
					atto.setSidacId(rs.getString(Constant.COLUMN_ID_ATTO));
					atto.setNumeroRepertorio(rs.getString(Constant.COLUMN_REPERTORIO));
					atto.setDataRepertorio(rs.getDate(Constant.COLUMN_DATA_REPERTORIO));
					atto.setLegislatura(rs.getString(Constant.COLUMN_LEGISLATURA));
					atto.setTipoAtto(rs.getString(Constant.COLUMN_TIPO_ATTO));
					atto.setNumeroAtto(rs.getString(Constant.COLUMN_NUM_ATTO));
					atto.setEstensioneAtto(rs.getString(Constant.COLUMN_NUM_ATTO_ESTENSIONE));
					atto.setOggetto(rs.getString(Constant.COLUMN_TITOLO));
					atto.setDataIniziativa(rs.getDate(Constant.COLUMN_DATA_INIZITIVA));
					atto.setTipoIniziativa(rs.getString(Constant.COLUMN_TIPO_INIZIATIVA));
					atto.setDescrizioneIniziativa(rs.getString(Constant.COLUMN_DESCR_INIZIATIVA));
					atto.setDataPresaInCarico(rs.getDate(Constant.COLUMN_DATA_PRESA_CARICO));
					atto.setRichiestaUrgenza(Util.getBooleanFromChar(rs.getString(Constant.COLUMN_URGENZA_RICHIESTA)));
					atto.setDataVotazioneUrgenza(rs.getDate(Constant.COLUMN_URGENZA_VOT_DATA));
					atto.setVotazioneUrgenza(Util.getBooleanFromChar(rs.getString(Constant.COLUMN_URGENZA_VOTAZIONE)));
					atto.setStato(rs.getString(Constant.COLUMN_STATO));
					atto.setAssegnazione(rs.getString(Constant.COLUMN_ASSEGNAZIONE));
					atto.setTipoChiusura(rs.getString(Constant.COLUMN_TIPO_CHIUSURA));
					atto.setNumeroProtocollo(rs.getString(Constant.COLUMN_PROTOCOLLO));
					atto.setDataRepertorio(rs.getDate(Constant.COLUMN_DATA_PROTOCOLLO));
					atto.setClassificazione(rs.getString(Constant.COLUMN_CLASSIFICAZIONE));// da
																							// capire
					atto.setAiutiStato(Util.getBooleanFromChar(rs.getString(Constant.COLUMN_AIUTI_STATO)));
					atto.setNormaFinanziaria(Util.getBooleanFromChar(rs.getString(Constant.COLUMN_NORMA_FINANZIARIA)));
					atto.setValutazioneAmmissibilita(rs.getString(Constant.COLUMN_ACCETTABILITA));
					atto.setDataRicevimentoInformazioni(rs.getDate(Constant.COLUMN_DATA_RICEV_INFO));
					atto.setDataRichiestaInformazioni(rs.getDate(Constant.COLUMN_DATA_RICH_INFO));
					atto.setDataScadenza(rs.getDate(Constant.COLUMN_DECADUTO_DATA));
					atto.setCodice(rs.getString(Constant.COLUMN_CODICE_ATTO));

					atto.setAppoCollegamenti(rs.getString(Constant.COLUMN_COLLEGAMENTO_ALTRI_ATTI));
					atto.setNumeroDgr(rs.getString(Constant.COLUMN_NUMERO_DGR));
					atto.setDataDgr(rs.getDate(Constant.COLUMN_DATA_DGR));

					// PASSAGGIO capire se usare quello del ws
					/*
					 * Passaggio passaggio = new Passaggio();
					 * passaggio.setNome("Passaggio1");
					 * atto.getPassaggi().add(passaggio);
					 */

					// contaPassaggi = 1;

					// TODO CHIEDERE
					// atto.setPubblico();
					// atto.setAttoProseguente();

					// TODO probabili inesistenti
					// atto.setOggettoOriginale();

					/*
					 * atto.setDataPresentazione(); atto.setDataPubblicazione();
					 * atto.setDataSedutaSc(); atto.setDataIniziativa();
					 * atto.setTipologia(); atto.setDataAssegnazione();
					 * atto.setEsitoValidazione(); atto.setDataValidazione();
					 * atto.setDataAssegnazioneCommissioni();
					 * atto.setTipoIniziativaNome(); atto.setNumeroLr();
					 * atto.setDataLR(); atto.setNumeroPubblicazioneBURL();
					 * atto.setDataPubblicazioneBURL; atto.setDataChiusura();
					 * atto.setStatoChiusura();
					 * 
					 * atto.setNumeroDcr(); atto.setNumeroLcr();
					 * atto.setNoteCollegamenti();
					 * 
					 * 
					 * atto.setRichiestaUrgenza(); atto.setVotazioneUrgenza();
					 * atto.setDataVotazioneUrgenza();
					 * atto.setNoteAmmissibilita();
					 * atto.setNotePresentazioneAssegnazione();
					 * atto.setScadenza60gg(); atto.setIterAula();
					 * atto.setNoteChiusuraIter(); atto.setNumeroDgrSeguito();
					 * atto.setDataDgrSeguito(); atto.setNumRegolamento();
					 * atto.setDataRegolamento();
					 * 
					 * private List<Organo> organi = new ArrayList<Organo>();
					 * private List<Firmatario> firmatari = new
					 * ArrayList<Firmatario>(); private List<Relatore> relatori
					 * = new ArrayList<Relatore>(); private List<Consultazione>
					 * consultazioni = new ArrayList<Consultazione>(); private
					 * List<Allegato> allegati = new ArrayList<Allegato>();
					 * private List<Link> linksPresentazioneAssegnazione = new
					 * ArrayList<Link>(); private List<OrganismoStatutario>
					 * organismiStatutari = new
					 * ArrayList<OrganismoStatutario>(); private List<TestoAtto>
					 * testiAtto = new ArrayList<TestoAtto>(); private
					 * List<Collegamento> collegamenti = new
					 * ArrayList<Collegamento>(); private
					 * List<CollegamentoAttiSindacato> collegamentiAttiSindacato
					 * = new ArrayList<CollegamentoAttiSindacato>(); private
					 * List<CollegamentoLeggiRegionali>
					 * collegamentiLeggiRegionali = new
					 * ArrayList<CollegamentoLeggiRegionali>(); private
					 * List<Passaggio> passaggi = new ArrayList<Passaggio>();
					 */

					atti.add(atto);
				}

			}

			rs.close();
			ps.close();
			endTime = System.currentTimeMillis();
			System.out.println("QUERY ATTI e AULA END :" + (endTime - startTime) / 1000 + " sec");

			System.out.println("QUERY FIRMATARI");
			startTime = System.currentTimeMillis();
			for (Atto attoRec : atti) {

				ps = conn.prepareStatement(Constant.QUERY_FIRMATARI);
				ps.setString(1, attoRec.getId());
				rs = ps.executeQuery();
				while (rs.next()) {

					Firmatario firmat = new Firmatario();
					firmat.setDescrizione(rs.getString(Constant.FIRM_COLUMN_FIRMATARIO));
					firmat.setDataFirma(rs.getDate(Constant.FIRM_COLUMN_DATA_DEPOSITO));
					firmat.setDataRitiro(rs.getDate(Constant.FIRM_COLUMN_DATA_RITIRO));
					firmat.setGruppoConsiliare(rs.getString(Constant.FIRM_COLUMN_ABBREVIAZIONE));
					firmat.setNumeroOrdinamento(rs.getString(Constant.FIRM_COLUMN_ORDINAMENTO));
					attoRec.getFirmatari().add(firmat);
				}

				rs.close();
				ps.close();
			}

			endTime = System.currentTimeMillis();
			System.out.println("QUERY FIRMATARI END :" + (endTime - startTime) / 1000 + " sec");

			System.out.println("QUERY ORGANISMI");
			startTime = System.currentTimeMillis();
			for (Atto attoRec : atti) {

				ps = conn.prepareStatement(Constant.QUERY_ORGANISMI);
				ps.setString(1, attoRec.getId());
				rs = ps.executeQuery();
				while (rs.next()) {

					OrganismoStatutario org = new OrganismoStatutario();
					org.setDescrizione(rs.getString(Constant.ORG_COLUMN_NOME_ORGANO));
					org.setDataAssegnazione(rs.getDate(Constant.ORG_COLUMN_DATA_RICHIESTA));
					org.getParere().setDataAssegnazione(rs.getDate(Constant.ORG_COLUMN_DATA_RICHIESTA));
					org.getParere().setDataRicezioneOrgano(rs.getDate(Constant.ORG_COLUMN_DATA_RICEZIONE));
					attoRec.getOrganismiStatutari().add(org);
				}

				rs.close();
				ps.close();
			}

			endTime = System.currentTimeMillis();
			System.out.println("QUERY ORGANISMI END :" + (endTime - startTime) / 1000 + " sec");

			System.out.println("QUERY COMMISSIONI");
			startTime = System.currentTimeMillis();
			for (Atto attoRec : atti) {

				ps = conn.prepareStatement(Constant.QUERY_COMMISSIONI_PASSAGGIO1);
				ps.setString(1, attoRec.getId());
				rs = ps.executeQuery();

				while (rs.next()) {
					Passaggio passaggio = null;

					int numPassaggio = Integer.parseInt(rs.getString("NUM_PASSAGGIO"));

					// Secondo passaggio
					if (numPassaggio > 1 && numPassaggio <= attoRec.getPassaggi().size()) {

						System.out.println("Più passaggi com per " + attoRec.getId());
						passaggio = attoRec.getPassaggi().get(numPassaggio - 1);

					} else if (Integer.parseInt(rs.getString("NUM_PASSAGGIO")) == 1) {

						passaggio = attoRec.getPassaggi().get(0);

					} else {

						System.out.println("Passaggio inesistente per " + attoRec.getId());
						break;
					}

					passaggio.setAppoId(rs.getString(Constant.COMM_COLUMN_ID_PASS));
					Commissione comm = new Commissione();
					comm.setDescrizione(rs.getString(Constant.COMM_COLUMN_COMM_NOME));
					comm.setDataProposta(rs.getDate(Constant.COMM_COLUMN_DATA_PROPOSTA));
					comm.setDataAssegnazione(rs.getDate(Constant.COMM_COLUMN_DATA_FIRMA));

					// Ruolo
					if (Util.getBooleanFromChar(rs.getString(Constant.COMM_COLUMN_DELIBERANTE))) {

						comm.setRuolo(Commissione.RUOLO_DELIBERANTE);

					} else {

						if ("R".equals(rs.getString(Constant.COMM_COLUMN_TIPO_COMPETENZA))) {

							comm.setRuolo(Commissione.RUOLO_REFERENTE);

						} else if ("C".equals(rs.getString(Constant.COMM_COLUMN_TIPO_COMPETENZA))) {

							comm.setRuolo(Commissione.RUOLO_CONSULTIVA);
							// TODO Le altre???
						}

					}

					comm.setStato(rs.getString(Constant.COMM_COLUMN_STATO_COMM));
					comm.setDataPresaInCarico(rs.getDate(Constant.COMM_COLUMN_DATA_ARRIVO));
					comm.setDataVotazione(rs.getDate(Constant.COMM_COLUMN_DATA_VOTO));
					comm.setQuorumEsameCommissioni(rs.getString(Constant.COMM_COLUMN_QUORUM));
					comm.setDataTrasmissione(rs.getDate(Constant.COMM_COLUMN_DATA_TRASMISSIONE));
					comm.setNoteGeneraliEsameCommissione(rs.getString(Constant.COMM_COLUMN_OSSERVAZIONI));
					comm.setDataRichiestaIscrizioneAula(rs.getDate(Constant.COMM_COLUMN_DATA_RICH_ISCR_CONS));
					comm.setDataScadenza(rs.getDate(Constant.COMM_COLUMN_DATA_SCADENZA));
					comm.setMateria(rs.getString(Constant.COMM_COLUMN_MATERIA));
					comm.setEsitoVotazione(rs.getString(Constant.COMM_COLUMN_VOTO_COM));
					passaggio.getCommissioni().add(comm);

				}
				rs.close();
				ps.close();

			}
			endTime = System.currentTimeMillis();
			System.out.println("QUERY COMMISSIONI END :" + (endTime - startTime) / 1000 + " sec");

			System.out.println("QUERY RELATORI");
			startTime = System.currentTimeMillis();
			for (Atto attoRec : atti) {

				for (Passaggio passaggio : attoRec.getPassaggi()) {

					ps = conn.prepareStatement(Constant.QUERY_RELATORI_COMM);
					ps.setString(1, passaggio.getAppoId());
					rs = ps.executeQuery();
					while (rs.next()) {

						for (Commissione comm : passaggio.getCommissioni()) {

							if (comm.getDescrizione().equals(rs.getString(Constant.REL_COLUMN_COMM_NOME))) {

								Relatore rel = new Relatore();
								rel.setDescrizione(rs.getString(Constant.REL_COLUMN_RELATORE));
								rel.setDataNomina(rs.getDate(Constant.REL_COLUMN_NOMINA_REL));
								rel.setDataUscita(rs.getDate(Constant.REL_COLUMN_FINE_REL));
								comm.getRelatori().add(rel);
								break;
							}

						}

					}

				}

				rs.close();
				ps.close();
			}

			endTime = System.currentTimeMillis();
			System.out.println("QUERY RELATORI END :" + (endTime - startTime) / 1000 + " sec");

			System.out.println("QUERY COMITATO RISTRETTO");
			startTime = System.currentTimeMillis();
			for (Atto attoRec : atti) {

				for (Passaggio passaggio : attoRec.getPassaggi()) {

					ps = conn.prepareStatement(Constant.QUERY_COMITATI_RISTRETTI);
					ps.setString(1, passaggio.getAppoId());
					rs = ps.executeQuery();
					while (rs.next()) {

						for (Commissione comm : passaggio.getCommissioni()) {

							comm.getComitatoRistretto().setTipologia("Comitato ristretto");

							if (comm.getDescrizione().equals(rs.getString(Constant.COMRIS_COLUMN_COMM_NOME))) {
								Componente compo = new Componente();
								compo.setDataNomina(rs.getDate(Constant.COMRIS_COLUMN_NOMINA_COM));
								compo.setDataUscita(rs.getDate(Constant.COMRIS_COLUMN_FINE_COM));
								compo.setDescrizione(rs.getString(Constant.COMRIS_COLUMN_COMITATO_SOG));
								comm.getComitatoRistretto().getComponenti().add(compo);
								break;
							}

						}

					}

				}

				rs.close();
				ps.close();
			}

			endTime = System.currentTimeMillis();
			System.out.println("QUERY COMITATO RISTRETTO END :" + (endTime - startTime) / 1000 + " sec");

			return atti;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// Do nothing...
				}
			}
		}
	}

	private String convertTipoAbbinamento(String tipo) {

		if (tipo == null || "".equals(tipo)) {
			return "";
		} else if ("B".equals(tipo)) {
			return "Testo base";
		} else {
			return "Testo unificato";

		}

	}

}
