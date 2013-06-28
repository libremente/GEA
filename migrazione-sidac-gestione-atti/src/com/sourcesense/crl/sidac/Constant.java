package com.sourcesense.crl.sidac;

public class Constant {

	// public static String QUERY_INSERT_COMM="";

	public static String QUERY_ALL_ATTI = " SELECT  ag.ID as ID_ATTO_APPO , delc.*, ag.*, pc.*,alg.*, ti.DESCRIZIONE as TIPO_INIZIATIVA, le.NUM_ROMANO as LEGISLATURA,"
			+ " ta.DESCRIZIONE as TIPO_ATTO,"
			+ " st.DESCRIZIONE as STATO , tipa.DESCRIZIONE as AMMISSIBILITA , qo.DESCRIZIONE as QUORUM_AULA , st_voto.DESCRIZIONE as VOTO_AULA ,riic.* "
			+ " from ATTI_GENERALE ag, PASSAGGI_CONSIGLIO pc , TIPI_ATTI ta , STATI st ,STATI st_voto ,LEGISLATURA le , TIPI_INIZIATIVA ti ,  "
			+ " TIPI_AMMISSIBILITA tipa , DELIBERE_CONSIGLIO delc , QUORUM qo , ATTI_LNK_GIUNTA alg , RINVII_IN_COMMISSIONE riic " + " where le.NUM_ROMANO = 'IX'  "
			+ " and ag.ID_LEGISLATURA = le.ID  " + " and ag.ID_TIPO_ATTO  in (1,7,8,9,10,11,12,13,14)   " + " and ag.ID = pc.ID_ATTO (+) " + " and ag.ID_TIPO_ATTO  = ta.ID  "
			+ " and ag.ID_STATO = st.ID  " + " and ag.ID_TIPO_INIZIATIVA  = ti.ID (+) " + " and ag.ACCETTABILITA  = tipa.ID (+) " + " and ag.ID = alg.ID_ATTO (+) "
			+ " and pc.ID = delc.ID_PASSAGGIO (+) " + " and delc.VOTO = st_voto.ID (+) " + " and delc.QUORUM = qo.ID (+) " + " and pc.ID =   riic.id_passaggio_cons (+) "
			+ " order by  ag.NUM_ATTO asc,TIPO_ATTO asc , pc.NUM_PASSAGGIO asc ";

	// ATTO_GENERALE
	public static String COLUMN_ID_ATTO = "ID_ATTO_APPO";
	public static String COLUMN_REPERTORIO = "REPERTORIO";
	public static String COLUMN_DATA_REPERTORIO = "DATA_REPERTORIO";
	public static String COLUMN_LEGISLATURA = "LEGISLATURA";
	public static String COLUMN_TIPO_ATTO = "TIPO_ATTO";
	public static String COLUMN_NUM_ATTO = "NUM_ATTO";
	public static String COLUMN_NUM_ATTO_ESTENSIONE = "NUM_ATTO_ESTENSIONE";
	public static String COLUMN_TITOLO = "TITOLO";
	public static String COLUMN_DATA_INIZITIVA = "DATA_INIZIATIVA";
	public static String COLUMN_TIPO_INIZIATIVA = "TIPO_INIZIATIVA";
	public static String COLUMN_DATA_PRESA_CARICO = "DATA_PRESA_CARICO";
	public static String COLUMN_URGENZA_RICHIESTA = "URGENZA_RICHIESTA";
	public static String COLUMN_URGENZA_VOT_DATA = "URGENZA_VOT_DATA";
	public static String COLUMN_URGENZA_VOTAZIONE = "URGENZA_VOTAZIONE";
	public static String COLUMN_STATO = "STATO";
	public static String COLUMN_ASSEGNAZIONE = "ASSEGNAZIONE";
	public static String COLUMN_TIPO_CHIUSURA = "TIPO_CHIUSURA";
	public static String COLUMN_PROTOCOLLO = "PROTOCOLLO";
	public static String COLUMN_DATA_PROTOCOLLO = "DATA_PROTOCOLLO";
	public static String COLUMN_DESCR_INIZIATIVA = "DESCR_INIZIATIVA";
	public static String COLUMN_CODICE_ATTO = "CODICE_ATTO";
	public static String COLUMN_CLASSIFICAZIONE = "CLASSIFICAZIONE";
	public static String COLUMN_AIUTI_STATO = "AIUTI_STATO";
	public static String COLUMN_NORMA_FINANZIARIA = "NORMA_FINANZIARIA";
	public static String COLUMN_ACCETTABILITA = "AMMISSIBILITA";
	public static String COLUMN_DATA_RICH_INFO = "DATA_RICH_INFO";
	public static String COLUMN_DATA_RICEV_INFO = "DATA_RICEV_INFO";
	public static String COLUMN_DECADUTO_DATA = "DECADUTO_DATA";
	public static String COLUMN_DATA_DGR = "DATA_DGR";
	public static String COLUMN_NUMERO_DGR = "NUM_DGR";

	public static String COLUMN_RIASSUNZIONE = "RIASSUNZIONE"; // chiedere
	public static String COLUMN_DATA_DEP_ATTI = "DATA_DEP_ATTI";// chiedere
	public static String COLUMN_DECADUTO = "DECADUTO";// chiedere
	public static String COLUMN_DIR_GENERALE = "DIR_GENERALE";// chiedere

	public static String COLUMN_RITIRO = "RITIRO";// chiuso per ritiro
	public static String COLUMN_RITIRO_NOTE = "RITIRO_NOTE";// chiuso per ritiro
	public static String COLUMN_RITIRO_DATA = "RITIRO_DATA";// chiuso per ritiro
	public static String COLUMN_RISPOSTE = "RISPOSTE";// atti itr etc
	public static String COLUMN_RISP_NUM = "RISP_NUM";// atti itr etc
	public static String COLUMN_RISP_CHIUSE = "RISP_CHIUSE";// atti itr etc
	public static String COLUMN_DATA_ANNUNCIO = "DATA_ANNUNCIO";// atti itr
	public static String COLUMN_ANNUNCIATO = "ANNUNCIATO";// atti itr
	public static String COLUMN_DECRETO_NOMINE = "DECRETO_NOMINE";// sempre null
	public static String COLUMN_DATA_TRASM_CONS = "DATA_TRASM_CONS"; // sempre
																		// null
	public static String COLUMN_COLLEGAMENTO_ALTRI_ATTI = "COLLEGAMENTO_ALTRI_ATTI";
	
	public static String PC_COLUMN_DATA_RICHIESTA_ISCRIZIONE = "DATA_RICHIESTA_ISCRIZIONE";
	public static String PC_COLUMN_PRESENZA_RELAZIONE = "PRESENZA_RELAZIONE";// non
																				// corrisponde
																				// è
																				// un
																				// boolean
	public static String PC_COLUMN_CONTROLLO = "CONTROLLO";// non corrisponde è
															// un boolean
	public static String PC_COLUMN_ANNOTAZIONI = "ANNOTAZIONI";// sempre null
	public static String PC_COLUMN_DATA_ARRIVO_AULA = "DATA_ARRIVO_AULA";
	public static String PC_COLUMN_RINVIATO = "RINVIATO";// non corrisponde è un
															// boolean

	// DELIBERE_CONSIGLIO (dati aula)
	public static String DC_COLUMN_QUORUM = "QUORUM_AULA"; // CHAR(36BYTE)
	public static String DC_COLUMN_TITOLO = "TITOLO"; // VARCHAR2(50BYTE) Tutti
														// null
	public static String DC_COLUMN_NUM_DCR = "NUM_DCR"; // VARCHAR2(30BYTE)
	public static String DC_COLUMN_DATA_DCR = "DATA_DCR"; // DATE // ?non la
															// consideriamo?
	public static String DC_COLUMN_NUM_LCR = "NUM_LCR"; // VARCHAR2(50BYTE)
	public static String DC_COLUMN_DATA_LCR = "DATA_LCR"; // DATE ?non la
															// consideriamo?
	public static String DC_COLUMN_NUM_LR = "NUM_LR"; // VARCHAR2
	public static String DC_COLUMN_DATA_LR = "DATA_LR"; // DATE
	public static String DC_COLUMN_RIF_BURL = "RIF_BURL";// VARCHAR2
	public static String DC_COLUMN_DATA_BURL = "DATA_BURL"; // DATE
	public static String DC_COLUMN_CONTROLLO = "CONTROLLO"; // CHAR Chiedere
	public static String DC_COLUMN_EMENDATO = "EMENDATO";// CHAR
	public static String DC_COLUMN_PUBBLICARE = "PUBBLICARE";// CHAR Sarà questo
																// il
																// pubblico?????
	public static String DC_COLUMN_TESTO = "TESTO";// VARCHAR2 sempre null
	public static String DC_COLUMN_VOTO_AULA = "VOTO_AULA"; // CHAR Chiedere a
															// quale totale si
															// riferisce
	public static String DC_COLUMN_MOTIVAZIONE_RINVIO = "MOTIVAZIONE"; //

	
	public static String QUERY_FIRMATARI = " SELECT  CONCAT( CONCAT(sog.NOME,' ') ,sog.COGNOME) as FIRMATARIO , gr.ABBREVIAZIONE , aliprom.DATA_DEPOSITO , aliprom.DATA_RITIRO , aliprom.ORDINAMENTO "
			+ " from  ATTI_LNK_PROMOTORI aliprom ,SOGGETTI sog , GRUPPI gr "
			+ " where aliprom.ID_ATTO = ? "
			+ " and aliprom.ID_FIRMATARIO_CONS = sog.id "
			+ " and aliprom.ID_FIRMATARIO_GRP = gr.ID (+) " + " ORDER BY aliprom.ORDINAMENTO ";

	public static String FIRM_COLUMN_FIRMATARIO = "FIRMATARIO";
	public static String FIRM_COLUMN_ABBREVIAZIONE = "ABBREVIAZIONE";
	public static String FIRM_COLUMN_DATA_DEPOSITO = "DATA_DEPOSITO";
	public static String FIRM_COLUMN_DATA_RITIRO = "DATA_RITIRO";
	public static String FIRM_COLUMN_ORDINAMENTO = "ORDINAMENTO";

	public static String QUERY_ORGANISMI = " SELECT org.NOME_ORGANO , ricpar.DATA_RICHIESTA , ricpar.DATA_RICEZIONE " + " FROM RICHIESTA_PARERI ricpar , ORGANI_ESTERNI org "
			+ " WHERE ricpar.ID_ATTO =  ? " + " and ricpar.ID_ORGANO = org.ID";

	public static String ORG_COLUMN_NOME_ORGANO = "NOME_ORGANO";
	public static String ORG_COLUMN_DATA_RICHIESTA = "DATA_RICHIESTA";
	public static String ORG_COLUMN_DATA_RICEZIONE = "DATA_RICEZIONE";

	public static String QUERY_COLLEGAMENTO = "SELECT  1 " + " from ATTI_GENERALE ag, TIPI_ATTI ta , LEGISLATURA le " + " where le.NUM_ROMANO = 'IX'  "
			+ " and ag.ID_LEGISLATURA = le.ID " + " and ag.NUM_ATTO = ? " + " and ta.descrizione = ? " + " and ta.id = ag.ID_TIPO_ATTO";

	// Assegnazione commissioni
	public static String QUERY_COMMISSIONI_PASSAGGIO1 = " SELECT   ass.ID as ID_ATTO, co.descrizione  as COMM_NOME, stat_ass.descrizione as STATO_ASSEGNAZIONE, "
			+ " stat_com.descrizione as STATO_COMM ,st_voto.DESCRIZIONE as VOTO_COM, " + " alc.*,  ass.* , atseg.* , pascom.* , pascom.id as ID_PASS"
			+ " FROM ATTI_GENERALE ag ,ASSEGNAZIONI ass ,ASSEGNAZIONI_LNK_COMMISSIONI alc ,COMMISSIONI co , STATI stat_ass , STATI stat_com , "
			+ " STATI st_voto,ATTI_SEGUITO atseg ,PASSAGGI_COMMISSIONI pascom " + " WHERE ag.ID = ? " + " and ag.ID = ass.ID_ATTO " + " and ass.ID = alc.ID_ASSEGNAZIONE "
			+ " and ass.NUM_PROPOSTA = (select max(ass_sub.NUM_PROPOSTA) from ASSEGNAZIONI ass_sub where ass_sub.id_atto = ag.id)" + " and alc.ID_COMMISSIONE = co.ID "
			+ " and ass.id_stato = stat_ass.id" + " and atseg.ID_ATTO_GENERALE = ag.ID" + " and atseg.ID_COMMISSIONE  = co.ID " + " and atseg.ID = pascom.ID_SEGUITO"
			+ " and pascom.VOTO = st_voto.ID (+)" + " and atseg.id_stato = stat_com.id " + " order by pascom.NUM_PASSAGGIO asc ";

	public static String COMM_COLUMN_DATA_PROPOSTA = "DATA_PROPOSTA"; // CHAR(36BYTE)
	public static String COMM_COLUMN_DATA_FIRMA = "DATA_FIRMA"; // ???data
																// assegnazione
	public static String COMM_COLUMN_PROT_PROPOSTA = "PROT_PROPOSTA"; // Chiedere
	public static String COMM_COLUMN_STATO_ASSEGNAZIONE = "STATO_ASSEGNAZIONE"; // Non
																				// ci
																				// interessa
	public static String COMM_COLUMN_TIPO_COMPETENZA = "TIPO_COMPETENZA"; // R U
																			// C
																			// G
																			// ???
																			// chiedere
																			// U
																			// G
	public static String COMM_COLUMN_DELIBERANTE = "DELIBERANTE"; //
	public static String COMM_COLUMN_PARERE_OBBLIGATORIO = "PARERE_OBBLIGATORIO"; // non
																					// lo
																					// usiamo
	public static String COMM_COLUMN_STATO_COMM = "STATO_COMM"; //
	public static String COMM_COLUMN_TIPO = "TIPO"; // R U C X G ???? Chiedere
	public static String COMM_COLUMN_DATA_ARRIVO = "DATA_ARRIVO"; // Data presa
																	// in carico
	public static String COMM_COLUMN_PARERE = "PARERE"; // ??
	public static String COMM_COLUMN_DATA_VOTO = "DATA_VOTO"; //
	public static String COMM_COLUMN_QUORUM = "QUORUM"; //
	public static String COMM_COLUMN_DATA_TRASMISSIONE = "DATA_TRASMISSIONE"; //
	public static String COMM_COLUMN_OSSERVAZIONI = "OSSERVAZIONI"; // Note
	public static String COMM_COLUMN_DATA_RICH_ISCR_CONS = "DATA_RICH_ISCR_CONS"; // iscrizione
																					// aula
	public static String COMM_COLUMN_DATA_SCADENZA = "DATA_SCADENZA"; //
	public static String COMM_COLUMN_MATERIA = "MATERIA"; //
	public static String COMM_COLUMN_VOTO_COM = "VOTO_COM"; //
	public static String COMM_COLUMN_TRATTAZIONE = "TRATTAZIONE"; // ?????????
	public static String COMM_COLUMN_DA_DISTRIBUIRE = "DA_DISTRIBUIRE"; // ???
	public static String COMM_COLUMN_DIR_GENERALE = "DIR_GENERALE"; // ??
	public static String COMM_COLUMN_COMM_NOME = "COMM_NOME"; //
	public static String COMM_COLUMN_ID_PASS = "ID_PASS"; //

	public static String QUERY_RELATORI_COMM = "SELECT  comlrel.DATA_NOMINA as NOMINA_REL, comlrel.DATA_CESSAZIONE as FINE_REL,  CONCAT( CONCAT(sog.NOME,' ') ,sog.COGNOME) as RELATORE , co.DESCRIZIONE "
			+ " FROM COMMISSIONI co ,PASSAGGI_COMMISSIONI pascom ,COMMISSIONI_LNK_RELATORI comlrel, SOGGETTI sog , ATTI_SEGUITO atseg "
			+ " WHERE pascom.id = ? "
			+ " and comlrel.ID_PASSAGGIO = pascom.id "
			+ " and comlrel.ID_SOGGETTO = sog.id "
			+ " and pascom.ID_SEGUITO = atseg.ID "
			+ " and atseg.ID_COMMISSIONE = co.id "
			+ " order by pascom.NUM_PASSAGGIO asc , pascom.id";

	public static String REL_COLUMN_NOMINA_REL = "NOMINA_REL"; // ??
	public static String REL_COLUMN_FINE_REL = "FINE_REL"; //
	public static String REL_COLUMN_RELATORE = "RELATORE"; //
	public static String REL_COLUMN_COMM_NOME = "DESCRIZIONE"; //

	public static String QUERY_COMITATI_RISTRETTI = "SELECT   CONCAT( CONCAT(sog.NOME,' ') ,sog.COGNOME) as COMITATO_SOG, "
			+ " comrist.DATA as DATA_COMITATO , comlinsog.DATA_IN ,comlinsog.DATA_OUT , co.descrizione "
			+ " FROM COMMISSIONI co ,PASSAGGI_COMMISSIONI pascom , SOGGETTI sog , ATTI_SEGUITO atseg , COMITATI_RISTRETTI comrist , COMITATO_LNK_SOGGETTI comlinsog "
			+ " WHERE pascom.id = ? " + " and pascom.ID_SEGUITO = atseg.ID " + " and atseg.ID_COMMISSIONE = co.id " + " and pascom.ID = comrist.ID_PASSAGGIO "
			+ " and comrist.ID = comlinsog.ID_COMITATO " + " and comlinsog.ID_SOGGETTO  = sog.ID " + " order by pascom.NUM_PASSAGGIO asc , pascom.id";

	public static String COMRIS_COLUMN_NOMINA_COM = "DATA_IN"; // ??
	public static String COMRIS_COLUMN_FINE_COM = "DATA_OUT"; //
	public static String COMRIS_COLUMN_COMITATO_SOG = "COMITATO_SOG"; //
	public static String COMRIS_COLUMN_COMM_NOME = "DESCRIZIONE"; //
	public static String COMRIS_COLUMN_COMITATO_DATA = "DATA_COMITATO"; //

	public static String QUERY_ABBINAMENTI_ATTO = " select  abb_atto.id , abb_atto.num_atto , abb_tipo.descrizione as TIPO,  "
			+ "  abb_stato.descrizione as STATO , ala.* , abb.* , ala.ANNULLO_DATA as DATA_ANNULLO , abb.TIPO as TIPO_ABB" + " from ABBINAMENTI_LNK_ATTI ala , ABBINAMENTI abb , "
			+ "      ATTI_GENERALE abb_atto , TIPI_ATTI abb_tipo , STATI abb_stato " + " where  ala.id_atto = ? " + " and ala.id_abbinamento = abb.id "
			+ " and abb.id_atto_proseguente = abb_atto.id " + " and abb_atto.ID_TIPO_ATTO  = abb_tipo.ID " + " and abb_atto.ID_STATO = abb_stato.ID ";

	public static String ABB_COLUMN_ID = "ID";
	public static String ABB_COLUMN_NUM_ATTO = "NUM_ATTO";
	public static String ABB_COLUMN_TIPO = "TIPO";
	public static String ABB_COLUMN_STATO = "STATO";
	public static String ABB_COLUMN_ANNULLO = "ANNULLO";
	public static String ABB_COLUMN_ANNULLO_DATA = "DATA_ANNULLO";
	public static String ABB_COLUMN_DATA = "DATA";
	public static String ABB_COLUMN_NOTE = "NOTE";
	public static String ABB_COLUMN_TIPO_ABB = "TIPO_ABB";

	public static String QUERY_SEDUTE_COMM = "select ag.NUM_ATTO , ta.DESCRIZIONE as TIPO_ATTO, sc.*,aloc.* "
			+ " from SEDUTE_COMMISSIONI sc, COMMISSIONI com , LEGISLATURA le , ATTI_LNK_ODG_COM aloc , ATTI_GENERALE ag , "
			+ "   PASSAGGI_COMMISSIONI pascom , ATTI_SEGUITO atseg , TIPI_ATTI ta " + " where le.NUM_ROMANO = 'IX' " + " and com.ID_LEGISLATURA = le.ID "
			+ " and SUBSTR(com.DESCRIZIONE, 0, 3) = ? " + " and sc.ID_COMMISSIONI = com.ID " + " and aloc.ID_SEDUTA = sc.ID " + " and aloc.ID_PASSAGGIO = pascom.ID "
			+ " and pascom.ID_SEGUITO = atseg.ID " + " and atseg.ID_ATTO_GENERALE = ag.ID " + " and ag.ID_TIPO_ATTO = ta.ID "
			+ " order by aloc.ID_SEDUTA,aloc.NUM_ORDINE,com.DESCRIZIONE , sc.NUM_VERBALE , sc.DATA desc";

	public static String QUERY_SEDUTE_AULA = "select ag.NUM_ATTO , ta.DESCRIZIONE as TIPO_ATTO ,sc.*  ,aloc.* "
			+ " from SEDUTE_CONSIGLIO sc, ATTI_GENERALE ag , LEGISLATURA le , ATTI_LNK_ODG_CONS aloc , PASSAGGI_CONSIGLIO pc , TIPI_ATTI ta   " + " where le.NUM_ROMANO = 'IX' "
			+ " and ag.ID_LEGISLATURA = le.ID " + " and ag.ID_TIPO_ATTO = ta.ID " + " and ag.ID_TIPO_ATTO  in (1,7,8,9,10,11,12,13,14) " + " and ag.ID = pc.ID_ATTO "
			+ " and pc.ID = aloc.ID_PASSAGGIO " + " and aloc.ID_SEDUTA = sc.ID " + " order by aloc.ID_SEDUTA,aloc.NUM_ORDINE, sc.NUM_VERBALE , sc.DATA desc";

	public static String SED_COLUMN_NUM_ATTO = "NUM_ATTO";
	public static String SED_COLUMN_TIPO_ATTO = "TIPO_ATTO";
	public static String SED_COLUMN_DATA = "DATA";
	public static String SED_COLUMN_NUM_VERBALE = "NUM_VERBALE";
	public static String SED_COLUMN_ANNO_VERBALE = "ANNO_VERBALE";
	public static String SED_COLUMN_ORA_INIZIO = "ORA_INIZIO";
	public static String SED_COLUMN_NUM_ORDINE = "NUM_ORDINE";
	public static String SED_COLUMN_PREVISTO = "PREVISTO";
	public static String SED_COLUMN_DISCUSSO = "DISCUSSO";
	public static String SED_COLUMN_ID_SEDUTA = "ID_SEDUTA";

	public static String QUERY_TESTI = "Select * "
			+ " From (select t.id_atto, t.titolo, t.provenienza, tt.descrizione, ltt.id_doc from Testi T, Testi_lnk_tipi lTT, Tipi_testi TT " + " where t.id = lTT.id_testo "
			+ "   And lTT.id_tipotesto = TT.id) Sidac, "
			+ "  (Select id_document, (SELECT PATH FROM W2KODISSEA.fbc_volume where id = 'AF63782A-08AD-4550-933C-B40F9E3E3C5E') || PATH as FILE_PATH"
			+ "     from W2KODISSEA.fbc_phy_file PF, W2KODISSEA.fbc_lnk_log_phy_file llp, W2KODISSEA.gsl_system_coord_map cm " + "     where cm.id_log_file = llp.id_log_file "
			+ "         and llp.id_phy_file = pf.id " + "          and cm.map_type = 1) Odissea " + "  where Sidac.id_doc = Odissea.id_document " + "  and Sidac.id_atto = ? ";

	
	public static String TEST_COLUMN_TITOLO = "TITOLO";
	public static String TEST_COLUMN_PROVENIENZA = "PROVENIENZA";
	public static String TEST_COLUMN_PATH = "FILE_PATH";
	
	
	/*
	 * 
	 * 
	 * 
	 * ASSEGNAZIONI
	 * 
	 * ID CHAR(36BYTE) No ID_ATTO CHAR(36BYTE) No NUM_PROPOSTA NUMBER(10,0) Vale
	 * l'ultimo , non hanno data annullo ID_DOC_PROPOSTA CHAR(36BYTE)
	 * Documento??? TIPO_PROPOSTA CHAR(1BYTE) Yes Non abbiamo questo concetto
	 * chiedere ATTESA_PARERE CHAR(1BYTE) Yes Non abbiamo questo concetto
	 * chiedere ID_STATO CHAR(36BYTE) No
	 * 
	 * ASSEGNAZIONI_LNK_COMMISSIONI
	 * 
	 * ID CHAR(36BYTE) No ID_ASSEGNAZIONE CHAR(36BYTE) No ID_COMMISSIONE
	 * CHAR(36BYTE) No
	 * 
	 * ATTI_SEGUITO
	 * 
	 * ID CHAR(36BYTE) No ID_ATTO_GENERALE CHAR(36BYTE) Yes ID_COMMISSIONE
	 * CHAR(36BYTE) Yes ID_STATO CHAR(36BYTE) No TIPO VARCHAR2(50BYTE) Yes
	 * ID_STATO_OLD CHAR(36BYTE) Yes
	 * 
	 * 
	 * 
	 * PASSAGGI_COMMISSIONI
	 * 
	 * ID CHAR(36BYTE) No ID_SEGUITO CHAR(36BYTE) No ID_TESTO_PROPOSTO
	 * CHAR(36BYTE) Yes ID_SEDUTA_ARRIVO CHAR(36BYTE) Yes ID_SEDUTA_VOTO
	 * CHAR(36BYTE) Yes ID_TESTO_LICENZIATO CHAR(36BYTE) Yes ID_ABBINAMENTO
	 * CHAR(36BYTE) Yes ID_COMITATO_RISTR CHAR(36BYTE) Yes NUM_PASSAGGIO
	 * NUMBER(10,0) No 1
	 */

	/*
	 * Si noti che le proposte di assegnazione possono essere pi� di una, e
	 * vengono numerate mediante il campo NUM_PROPOSTA. L'attuale assegnazione �
	 * quindi quella con massimo valore di NUM_PROPOSTA, con valore del campo
	 * TIPO_PROPOSTA pari a Y.
	 */

	// ASSEGNAZIONI_LNK_COMMISSIONI.tipo_proposta = 'R' -> Referente
	// ASSEGNAZIONI_LNK_COMMISSIONI.tipo_proposta = 'C' -> COnsultive

	// Esame in commissione

	/*
	 * ATTI_GENERALE<- ATTI_SEGUITO.id_atto_generale -
	 * ATTI_SEGUITO.id_commissione-> COMMISSIONI
	 * 
	 * Le informazioni per ogni passaggio in commissione vengono archiviate
	 * nella tabella PASSAGGI_COMMISSIONI, e vengono collegate tramite il campo
	 * ID_SEGUITO alla tabella ATTI_SEGUITO. Si noti che per un atto vi possono
	 * essere pi� passaggi in commissione, determinati dall�eventuale rinvio in
	 * commissione da parte del consiglio, ad ognuno di questi corrisponde un
	 * record nella tabella PASSAGGI_COMMISSIONI ed � identificato dal campo
	 * NUM_PASSAGGIO.
	 */

}
