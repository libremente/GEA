/*
 *  * #%L
 *  * Alfresco Repository
 *  * %%
 *  * Copyright (C) 2005 - 2016 Alfresco Software Limited
 *  * %%
 *  * This file is part of the Alfresco software. 
 *  * If the software was purchased under a paid Alfresco license, the terms of 
 *  * the paid license agreement will prevail.  Otherwise, the software is 
 *  * provided under the following open source license terms:
 *  * 
 *  * Alfresco is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Lesser General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  * 
 *  * Alfresco is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU Lesser General Public License for more details.
 *  * 
 *  * You should have received a copy of the GNU Lesser General Public License
 *  * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 *  * #L%
 */
package com.sourcesense.crl.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gdata.util.common.base.StringUtil;
import com.sourcesense.crl.job.anagrafica.Constant;

public class OpenDataCommand {

	private static Log logger = LogFactory.getLog(OpenDataCommand.class);

	private NodeService nodeService;
	private String listSeparator;
	private String linkVotoFinale;

	private String linkAtto;
	private SearchService searchService;
	private NamespaceService namespaceService;
	private AttoUtil attoUtil;

	public void setLinkVotoFinale(String linkVotoFinale) {
		this.linkVotoFinale = linkVotoFinale;
	}

	public void setAttoUtil(AttoUtil attoUtil) {
		this.attoUtil = attoUtil;
	}

	public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public OpenDataCommand() {
		super(); 
	}

	/**
	 * Genera i ID - atto rappresentato da: tipoAtto + "-" + numeroAtto + "-" + legislatura 
	 * @param nodeRef atto dal quale calcolare l'ID
	 * @return String con l'ID atto.
	 */
	public String getIdAtto(NodeRef nodeRef) {
		String idAtto = "";
		try {
			String tipoAtto = getTipoAtto(nodeRef).toLowerCase();
			String legislatura = getLegislatura(nodeRef);
			String numeroAtto = getNumeroAtto(nodeRef);
			idAtto = tipoAtto + "-" + numeroAtto + "-" + legislatura;
		} catch (Exception e) {
			logger.error("Impossibile generare idAtto", e);
		}
		return idAtto;
	}

	/**
	 * Ritorna il nome del tipo levando il prefisso della definizione del content model.
	 * @param nodeRef nodo dal quale ottenere il tipo
	 * @return String  con il tipo di nodo che rappresenta nel content model di Alfresco o CRL extenzion model
	 */
	public String getTipoAtto(NodeRef nodeRef) {
		String tipoAtto = "";
		try {
			String nodeType = nodeService.getType(nodeRef).toString();
			tipoAtto = nodeType.substring(nodeType.length() - 3);
		} catch (Exception e) {
			logger.error("Impossibile generare idAtto", e);
		}
		return tipoAtto;
	}

	/**
	 * Ottiene il nome della legislatura leggendo la proprietà del nodo "legislatura"
	 * @param nodeRef Nodo dal quale sapere la legislatura.
	 * @return String legislatura dell'atto
	 */
	public String getLegislatura(NodeRef nodeRef) {
		return (String) nodeService.getProperty(nodeRef, AttoUtil.PROP_LEGISLATURA_QNAME);
	}

	public String getNumeroAtto(NodeRef nodeRef) {
		int numeroAtto = (int) nodeService.getProperty(nodeRef, AttoUtil.PROP_NUMERO_ATTO_QNAME);
		String estensioneAtto = getEstensioneAtto(nodeRef);
		if (estensioneAtto == null)
			estensioneAtto = StringUtil.EMPTY_STRING;
		return numeroAtto + estensioneAtto;
	}

	public String getEstensioneAtto(NodeRef nodeRef) {
		return (String) nodeService.getProperty(nodeRef, AttoUtil.PROP_ESTENSIONE_ATTO_QNAME);
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setListSeparator(String listSeparator) {
		this.listSeparator = listSeparator;
	}

	public void setLinkAtto(String linkAtto) {
		this.linkAtto = linkAtto;
	}

	public String getLinkAtto(NodeRef nodeRef) {
		MessageFormat mf = new MessageFormat(this.linkAtto);
		String parameterFormatting[] = new String[] {
				(String) nodeService.getProperty(nodeRef, ContentModel.PROP_NODE_UUID) };
		return mf.format(parameterFormatting);
	}

	public String getTipoIniziativa(NodeRef nodeRef) {
		String tipoIniziativa = (String) nodeService.getProperty(nodeRef, AttoUtil.PROP_TIPO_INIZIATIVA_QNAME);
		if (tipoIniziativa != null) {
			if ("01_ATTO DI INIZIATIVA CONSILIARE".equals(tipoIniziativa)) {

				return "Consiliare";

			} else if ("03_ATTO DI INIZIATIVA POPOLARE".equals(tipoIniziativa)) {

				return "Popolare";

			} else if ("05_ATTO DI INIZIATIVA UFFICIO PRESIDENZA".equals(tipoIniziativa)) {

				return "Ufficio di Presidenza";

			} else if ("07_ATTO DI INIZIATIVA AUTONOMIE LOCALI".equals(tipoIniziativa)) {

				return "Consiglio delle Autonomie locali";

			} else if ("06_ATTO DI INIZIATIVA PRESIDENTE GIUNTA".equals(tipoIniziativa)) {

				return "Presidente della Giunta";

			} else if ("02_ATTO DI INIZIATIVA DI GIUNTA".equals(tipoIniziativa)) {

				return "Giunta";

			} else if ("04_ATTO DI INIZIATIVA COMMISSIONI".equals(tipoIniziativa)) {

				return "Commissioni";

			} else if ("08_ATTO DI ALTRA INIZIATIVA".equals(tipoIniziativa)) {

				return "Altra Iniziativa";

			}
		}
		return null;

	}

	@SuppressWarnings("finally")
	public String getPrimoPromotore(NodeRef nodeRef) {
		List<NodeRef> firmatariFolder = searchService.selectNodes(nodeRef, "*[@cm:name='Firmatari']", null,
				this.namespaceService, false);
		if (!firmatariFolder.isEmpty()) {
			Set<QName> qnames = new HashSet<QName>(1, 1.0f);
			qnames.add(AttoUtil.TYPE_FIRMATARIO_QNAME);
			List<ChildAssociationRef> firmatariList = nodeService.getChildAssocs(firmatariFolder.get(0), qnames);
			for (ChildAssociationRef firmatario : firmatariList) {
				NodeRef firmatarioNodeRef = firmatario.getChildRef();
				boolean isFirmatarioPopolare = nodeService.getProperty(firmatarioNodeRef,
						AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME) != null
								? (boolean) nodeService.getProperty(firmatarioNodeRef,
										AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME)
								: false;
				boolean isPrimoFirmatario = nodeService.getProperty(firmatarioNodeRef,
						AttoUtil.PROP_IS_PRIMO_FIRMATARIO_QNAME) != null
								? (boolean) nodeService.getProperty(firmatarioNodeRef,
										AttoUtil.PROP_IS_PRIMO_FIRMATARIO_QNAME)
								: false;
				if (isFirmatarioPopolare && isPrimoFirmatario) {
					return (String) nodeService.getProperty(firmatarioNodeRef, AttoUtil.PROP_NOME_FIRMATARIO_QNAME);
				}
			}
		}
		return "";
	}

	@SuppressWarnings("finally")
	public String getTuttiPromotori(NodeRef nodeRef) {
		List<NodeRef> firmatariFolder = searchService.selectNodes(nodeRef, "*[@cm:name='Firmatari']", null,
				this.namespaceService, false);
		String promotoriString = "";
		if (!firmatariFolder.isEmpty()) {
			Set<QName> qnames = new HashSet<QName>(1, 1.0f);
			qnames.add(AttoUtil.TYPE_FIRMATARIO_QNAME);
			List<ChildAssociationRef> firmatariList = nodeService.getChildAssocs(firmatariFolder.get(0), qnames);
			for (ChildAssociationRef firmatarioChildRef : firmatariList) {
				NodeRef firmatarioNodeRef = firmatarioChildRef.getChildRef();
				boolean isFirmatarioPopolare = nodeService.getProperty(firmatarioNodeRef,
						AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME) != null
								? (boolean) nodeService.getProperty(firmatarioNodeRef,
										AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME)
								: false;
				String nomeFirmatario = (String) nodeService.getProperty(firmatarioNodeRef,
						AttoUtil.PROP_NOME_FIRMATARIO_QNAME);
				if (isFirmatarioPopolare) {
					if (StringUtils.isEmpty(promotoriString)) {
						promotoriString += nomeFirmatario;
					} else {
						promotoriString += listSeparator + nomeFirmatario;
					}
				}
			}
		}
		return promotoriString;
	}

	@SuppressWarnings("finally")
	public String getPrimoFirmatario(NodeRef nodeRef) {
		List<NodeRef> firmatariFolder = searchService.selectNodes(nodeRef, "*[@cm:name='Firmatari']", null,
				this.namespaceService, false);
		String firmatario = "";
		if (!firmatariFolder.isEmpty()) {
			Set<QName> qnames = new HashSet<QName>(1, 1.0f);
			qnames.add(AttoUtil.TYPE_FIRMATARIO_QNAME);
			List<ChildAssociationRef> firmatariList = nodeService.getChildAssocs(firmatariFolder.get(0), qnames);
			for (ChildAssociationRef firmatarioChildRef : firmatariList) {
				NodeRef firmatarioNodeRef = firmatarioChildRef.getChildRef();
				boolean isFirmatarioPopolare = nodeService.getProperty(firmatarioNodeRef,
						AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME) != null
								? (boolean) nodeService.getProperty(firmatarioNodeRef,
										AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME)
								: false;
				boolean isPrimoFirmatario = nodeService.getProperty(firmatarioNodeRef,
						AttoUtil.PROP_IS_PRIMO_FIRMATARIO_QNAME) != null
								? (boolean) nodeService.getProperty(firmatarioNodeRef,
										AttoUtil.PROP_IS_PRIMO_FIRMATARIO_QNAME)
								: false;
				if ((!isFirmatarioPopolare) && isPrimoFirmatario) {
					String primoFirmatario = (String) nodeService.getProperty(firmatarioNodeRef,
							AttoUtil.PROP_NOME_FIRMATARIO_QNAME);
					try {
						String idAnagrafica = getIdAnagrafica(primoFirmatario);
						if (StringUtils.isNotEmpty(idAnagrafica)) {
							firmatario = "-" + idAnagrafica + "-" + primoFirmatario;
						}
					} catch (Exception e) {
						logger.warn("Impossibile recuperare il primo firmatario " + primoFirmatario, e);
					} finally {
						return firmatario;
					}
				}
			}
		}
		return firmatario;
	}

	@SuppressWarnings("finally")
	public String getTuttiFirmatari(NodeRef nodeRef) {

		List<NodeRef> firmatariFolder = searchService.selectNodes(nodeRef, "*[@cm:name='Firmatari']", null,
				this.namespaceService, false);
		String firmatariString = "";
		if (!firmatariFolder.isEmpty()) {
			Set<QName> qnames = new HashSet<QName>(1, 1.0f);
			qnames.add(AttoUtil.TYPE_FIRMATARIO_QNAME);
			List<ChildAssociationRef> firmatariList = nodeService.getChildAssocs(firmatariFolder.get(0), qnames);
			for (ChildAssociationRef firmatarioChildRef : firmatariList) {
				NodeRef firmatarioNodeRef = firmatarioChildRef.getChildRef();
				boolean isFirmatarioPopolare = nodeService.getProperty(firmatarioNodeRef,
						AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME) != null
								? (boolean) nodeService.getProperty(firmatarioNodeRef,
										AttoUtil.PROP_IS_FIRMATARIO_POPOLARE_QNAME)
								: false;
				if (!isFirmatarioPopolare) {
					String nomeFirmatario = (String) nodeService.getProperty(firmatarioNodeRef,
							AttoUtil.PROP_NOME_FIRMATARIO_QNAME);
					try {
						String idAnagrafica = getIdAnagrafica(nomeFirmatario);
						if (StringUtils.isNotEmpty(idAnagrafica)) {
							if (StringUtils.isEmpty(firmatariString)) {
								firmatariString += "-" + idAnagrafica + "-" + nomeFirmatario;
							} else {
								firmatariString += listSeparator + "-" + idAnagrafica + "-" + nomeFirmatario;
							}
						}
					} catch (Exception e) {
						logger.warn("Impossibile recuperare il firmatario", e);
					}

				}
			}
		}
		return firmatariString;
	}

	public String getIdAnagrafica(String nomeAnagrafica) {
		String nomeAnagraficaCorretto = nomeAnagrafica.trim().replaceAll("[ \t]+", " ");
		String nome = nomeAnagraficaCorretto
				.replaceAll("([ \t][A-Z0-9_\\p{Punct}]{2,})+(([ \t][a-z]{2,})?([ \t][A-Z0-9_\\p{Punct}]{2,})+)*", "");
		String cognome = nomeAnagraficaCorretto.replaceAll("^[A-Z][a-z]{1,}([ \t][A-Z][a-z]{1,})*[ \t]", "");
		ResultSet consigliereNodes = null;
		try {
			consigliereNodes = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE,
					SearchService.LANGUAGE_LUCENE,
					"PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica//*\" AND TYPE:\"crlatti:consigliereAnagrafica\" AND @crlatti\\:nomeConsigliereAnagrafica:\""
							+ nome + "\" AND @crlatti\\:cognomeConsigliereAnagrafica:\"" + cognome + "\"");
			if (consigliereNodes.length() > 0) {
				return String.valueOf(
						(int) nodeService.getProperty(consigliereNodes.getNodeRef(0), Constant.PROP_ID_ANAGRAFICA));
			} else {
				logger.warn("Impossibile trovare in anagrafica " + nomeAnagrafica);
				return null;
			}
		} finally {
			if (consigliereNodes != null) {
				consigliereNodes.close();
			}
		}
	}

	public NodeRef getRelatoreNodeRef(NodeRef item) {
		NodeRef commissioneNodeRef = getCommissioneReferente(item);
		if (commissioneNodeRef != null) {
			List<NodeRef> relatori = searchService.selectNodes(commissioneNodeRef, "*[@cm:name='Relatori']", null,
					this.namespaceService, false);
			if ((relatori != null) && (!relatori.isEmpty())) {
				Set<QName> qnames = new HashSet<QName>(1, 1.0f);
				qnames.add(AttoUtil.TYPE_RELATORE_QNAME);
				List<ChildAssociationRef> relatoriList = nodeService.getChildAssocs(relatori.get(0), qnames);
				if ((relatoriList != null) && (!relatoriList.isEmpty())) {
					return relatoriList.get(0).getChildRef();
				}
			}
		}
		return null;
	}

	public NodeRef getPassaggio(NodeRef attoNoderef) {
		List<NodeRef> passaggi = searchService.selectNodes(attoNoderef, "*[@cm:name='Passaggi']", null,
				this.namespaceService, false);
		if (!passaggi.isEmpty()) {
			Set<QName> passaggiFolderQnames = new HashSet<QName>(1, 1.0f);
			passaggiFolderQnames.add(ContentModel.TYPE_FOLDER);
			List<ChildAssociationRef> passaggiList = nodeService.getChildAssocs(passaggi.get(0), passaggiFolderQnames);
			if ((passaggiList != null) && (!passaggiList.isEmpty())) {
				return passaggiList.get(0).getChildRef();
			}
		}
		return null;
	}

	public NodeRef getUltimoPassaggio(NodeRef attoNoderef) {
		List<NodeRef> passaggi = searchService.selectNodes(attoNoderef, "*[@cm:name='Passaggi']", null,
				this.namespaceService, false);
		if ((passaggi != null) && (!passaggi.isEmpty())) {
			Set<QName> passaggiFolderQnames = new HashSet<QName>(1, 1.0f);
			passaggiFolderQnames.add(ContentModel.TYPE_FOLDER);
			List<ChildAssociationRef> passaggiList = nodeService.getChildAssocs(passaggi.get(0), passaggiFolderQnames);
			if ((passaggiList != null) && (!passaggiList.isEmpty())) {
				return passaggiList.get(passaggiList.size() - 1).getChildRef();
			}
		}
		return null;
	}

	public List<ChildAssociationRef> getPassaggi(NodeRef attoNoderef) {
		List<NodeRef> passaggi = searchService.selectNodes(attoNoderef, "*[@cm:name='Passaggi']", null,
				this.namespaceService, false);
		if ((passaggi != null) && (!passaggi.isEmpty())) {
			Set<QName> passaggiFolderQnames = new HashSet<QName>(1, 1.0f);
			passaggiFolderQnames.add(ContentModel.TYPE_FOLDER);
			List<ChildAssociationRef> passaggiList = nodeService.getChildAssocs(passaggi.get(0), passaggiFolderQnames);
			if ((passaggiList != null) && (!passaggiList.isEmpty())) {
				return passaggiList;
			}
		}
		return new ArrayList<ChildAssociationRef>();
	}

	public NodeRef getCommissioneReferente(NodeRef attoNoderef) {
		NodeRef passaggio = getPassaggio(attoNoderef);
		if (passaggio != null) {
			List<NodeRef> commissioni = searchService.selectNodes(passaggio,
					"*[@cm:name='Commissioni']/*[@crlatti:ruoloCommissione='Referente' or @crlatti:ruoloCommissione='Co-Referente']",
					null, this.namespaceService, false);
			if ((commissioni != null) && (!commissioni.isEmpty())) {
				for (int i = commissioni.size() - 1; i > -1; i--) {
					if (nodeService.getProperty(commissioni.get(i), AttoUtil.PROP_DATA_VOTAZIONE) != null && nodeService
							.getProperty(commissioni.get(i), AttoUtil.PROP_ESITO_VOTAZIONE_COMMISSIONE_QNAME) != null)
						return commissioni.get(i);
				}
				return commissioni.get(commissioni.size() - 1);
			}
		}
		return null;
	}

	public String getRelatore(NodeRef item) {
		String relatore = "";
		NodeRef relatoreNodeRef = getRelatoreNodeRef(item);
		if (relatoreNodeRef != null) {

			String relatoreName = (String) nodeService.getProperty(relatoreNodeRef, ContentModel.PROP_NAME);
			relatoreName = relatoreName.trim().replaceAll(" +", " ");
			try {
				String idAnagrafica = getIdAnagrafica(relatoreName);
				if (StringUtils.isNotEmpty(idAnagrafica)) {
					relatore = "-" + idAnagrafica + "-" + relatoreName;
				}
			} catch (Exception e) {
				logger.warn("Impossibile recuperare il relatore " + relatoreName, e);
			}
		}
		return relatore;
	}

	public Date getDataNominaRelatore(NodeRef item) {
		NodeRef relatoreNodeRef = getRelatoreNodeRef(item);
		if (relatoreNodeRef != null) {
			return (Date) nodeService.getProperty(relatoreNodeRef, AttoUtil.PROP_DATA_NOMINA_RELATORE_QNAME);
		}
		return null;
	}

	public ArrayList<String> getAbbinamenti(NodeRef item) {
		List<NodeRef> attiAbbinati = attoUtil.getAttiAbbinati(item);
		int n = attiAbbinati.size();
		ArrayList<String> abbinamenti = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			abbinamenti.add(getIdAtto(attiAbbinati.get(i)));
		}
		return abbinamenti;
	}

	public String getEsitoVotazioneCommissioneReferente(NodeRef item) {
		NodeRef commissione = getCommissioneReferente(item);
		if (commissione != null) {
			return (String) nodeService.getProperty(commissione, AttoUtil.PROP_ESITO_VOTAZIONE_COMMISSIONE_QNAME);
		} else {
			return null;
		}
	}

	public Date getDataVotazioneCommissioneReferente(NodeRef item) {
		NodeRef commissione = getCommissioneReferente(item);
		if (commissione != null) {
			return (Date) nodeService.getProperty(commissione, AttoUtil.PROP_DATA_VOTAZIONE);
		} else {
			return null;
		}
	}

	public NodeRef getAula(NodeRef attoNoderef) {
		NodeRef passaggio = getUltimoPassaggio(attoNoderef);
		if (passaggio != null) {
			Set<QName> aulaFolderQnames = new HashSet<QName>(1, 1.0f);
			aulaFolderQnames.add(AttoUtil.TYPE_AULA);
			List<ChildAssociationRef> aulaList = nodeService.getChildAssocs(passaggio, aulaFolderQnames);
			if ((aulaList != null) && (!aulaList.isEmpty())) {
				return aulaList.get(0).getChildRef();
			}
		}
		return null;
	}

	public NodeRef getAulaByPassaggio(NodeRef passaggioNoderef) {
		if (passaggioNoderef != null) {
			Set<QName> aulaFolderQnames = new HashSet<QName>(1, 1.0f);
			aulaFolderQnames.add(AttoUtil.TYPE_AULA);
			List<ChildAssociationRef> aulaList = nodeService.getChildAssocs(passaggioNoderef, aulaFolderQnames);
			if ((aulaList != null) && (!aulaList.isEmpty())) {
				return aulaList.get(0).getChildRef();
			}
		}
		return null;
	}

	public String getEsitoVotazioneAula(NodeRef item) {
		NodeRef aula = getAula(item);
		if (aula != null) {
			return (String) nodeService.getProperty(aula, AttoUtil.ESITO_VOTO_PASSAGGIO_AULA_QNAME);
		} else {
			return null;
		}
	}

	public Date getDataVotazioneAula(NodeRef item) {
		NodeRef aula = getAula(item);
		if (aula != null) {
			return (Date) nodeService.getProperty(aula, AttoUtil.DATA_SEDUTA_PASSAGGIO_AULA_QNAME);
		} else {
			return null;
		}
	}

	public String getNumeroDcrPassaggioAula(NodeRef item) {
		NodeRef aula = getAula(item);
		if (aula != null) {
			return (String) nodeService.getProperty(aula, AttoUtil.PROP_NUMERO_DCR_PASSAGIO_AULA_QNAME);
		} else {
			return null;
		}
	}

	public String getLinkVotoFinaleAula(NodeRef attoNodeRef) {
		List<ChildAssociationRef> passaggi = getPassaggi(attoNodeRef);
		for (ChildAssociationRef passaggio : passaggi) {
			NodeRef aula = getAulaByPassaggio(passaggio.getChildRef());
			if (aula != null) {
				List<NodeRef> allegati = searchService.selectNodes(aula, "*[@cm:name='Allegati']", null,
						this.namespaceService, false);
				if ((allegati != null) && (!allegati.isEmpty())) {
					Set<QName> allegatoFolderQnames = new HashSet<QName>(1, 1.0f);
					allegatoFolderQnames.add(AttoUtil.TYPE_ALLEGATO_QNAME);
					List<ChildAssociationRef> allegatiList = nodeService.getChildAssocs(allegati.get(0),
							allegatoFolderQnames);
					for (ChildAssociationRef allegato : allegatiList) {
						if (((String) nodeService.getProperty(allegato.getChildRef(), AttoUtil.PROP_TIPOLOGIA_QNAME))
								.equalsIgnoreCase("allegato_aula")) {
							MessageFormat mf = new MessageFormat(this.linkVotoFinale);
							String parameterFormatting[] = new String[] {
									(String) nodeService.getProperty(allegato.getChildRef(),
											ContentModel.PROP_NODE_UUID),
									(String) nodeService.getProperty(allegato.getChildRef(), ContentModel.PROP_NAME) };
							return mf.format(parameterFormatting);
						}

					}
				}

			}
		}
		return null;
	}

	public String getLinkTestoAttoComReferente(NodeRef attoNodeRef) {
		List<NodeRef> pubbliciOpendataNoderefs = searchService.selectNodes(attoNodeRef,
				"*//*[@crlatti:pubblicoOpendata='true']", null, this.namespaceService, false);
		if (pubbliciOpendataNoderefs.size() > 0) {
			MessageFormat mf = new MessageFormat(this.linkVotoFinale);
			String parameterFormatting[] = new String[] {
					(String) nodeService.getProperty(pubbliciOpendataNoderefs.get(0), ContentModel.PROP_NODE_UUID),
					(String) nodeService.getProperty(pubbliciOpendataNoderefs.get(0), ContentModel.PROP_NAME) };
			return mf.format(parameterFormatting);
		}
		return null;
	}

	public String getIdsLr(NodeRef item) {
		String idsLr = "";
		Date dataLr = (Date) nodeService.getProperty(item, AttoUtil.PROP_DATA_LR_QNAME);
		if (dataLr != null) {
			String dateLrString = DateFormatUtils.format(dataLr, "yyyyMMdd");
			String numeroLr = (String) nodeService.getProperty(item, AttoUtil.PROP_NUMERO_LR_QNAME);
			idsLr = "lr00" + dateLrString + StringUtils.leftPad(numeroLr, 5, "0");
		}
		return idsLr;
	}
}
