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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		// TODO Auto-generated constructor stub
	}

	public String getIdAtto(NodeRef nodeRef) {
		String idAtto = "";
		try {
			String tipoAtto = getTipoAtto(nodeRef).toLowerCase();
			String legislatura = getLegislatura(nodeRef);
			int numeroAtto = getNumeroAtto(nodeRef);
			idAtto = tipoAtto + "-" + numeroAtto + "-" + legislatura;
		} catch (Exception e) {
			logger.error("Impossibile generare idAtto", e);
		}
		return idAtto;
	}

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

	public String getLegislatura(NodeRef nodeRef) {
		return (String) nodeService.getProperty(nodeRef, AttoUtil.PROP_LEGISLATURA_QNAME);
	}

	public int getNumeroAtto(NodeRef nodeRef) {
		return (int) nodeService.getProperty(nodeRef, AttoUtil.PROP_NUMERO_ATTO_QNAME);
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
	public String getPrimoFirmatario(NodeRef nodeRef) {
		String firmatario = "";
		String primoFirmatario = (String) nodeService.getProperty(nodeRef, AttoUtil.PROP_PRIMO_FIRMATARIO_QNAME);
		if (primoFirmatario != null && !primoFirmatario.isEmpty()) {
			try {
				String idAnagrafica = getIdAnagrafica(primoFirmatario);
				if (idAnagrafica.length() > 0) {
					firmatario = "-" + idAnagrafica + "-" + primoFirmatario;
				}
			} catch (Exception e) {
				logger.error("Impossibile recuperare il primo firmatario", e);
			} finally {
				return firmatario;
			}
		} else {
			return firmatario;
		}
	}

	@SuppressWarnings("finally")
	public String getTuttiFirmatari(NodeRef nodeRef) {
		String firmatariString = "";
		ArrayList<String> firmatari = (ArrayList<String>) nodeService.getProperty(nodeRef,
				AttoUtil.PROP_FIRMATARI_QNAME);
		if (firmatari != null) {
			for (int i = 0; i < firmatari.size(); i++) {
				if (firmatari.get(i) != null && !firmatari.get(i).isEmpty()) {
					try {
						String idAnagrafica = getIdAnagrafica(firmatari.get(i));
						if (idAnagrafica.length() > 0) {
							if (firmatariString.isEmpty()) {
								firmatariString += "-" + idAnagrafica + "-" + firmatari.get(i);
							} else {
								firmatariString += listSeparator + "-" + idAnagrafica + "-" + firmatari.get(i);
							}
						}
					} catch (Exception e) {
						logger.error("Impossibile recuperare il firmatario", e);
					}
				}
			}
		}
		return firmatariString;
	}

	public String getIdAnagrafica(String nomeAnagrafica) {
		String[] nomeAnagraficaSplitted = nomeAnagrafica.split(" ");
		String nome = nomeAnagraficaSplitted[0];
		int i = 1;
		while (Character.isLowerCase((nomeAnagraficaSplitted[i].charAt(1)))) {
			nome += " " + nomeAnagraficaSplitted[i];
			i++;
		}
		String cognome = nomeAnagraficaSplitted[i];
		ResultSet legislatureNodes = null;
		try {
			legislatureNodes = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE,
					SearchService.LANGUAGE_LUCENE,
					"PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica//*\" AND TYPE:\"crlatti:consigliereAnagrafica\" AND @crlatti\\:nomeConsigliereAnagrafica:\""
							+ nome + "\" AND @crlatti\\:cognomeConsigliereAnagrafica:\"" + cognome + "\"");
			if (legislatureNodes.length() > 0) {
				return String.valueOf(
						(int) nodeService.getProperty(legislatureNodes.getNodeRef(0), Constant.PROP_ID_ANAGRAFICA));
			} else {
				logger.debug("Impossibile trovare in anagrafica " + nomeAnagrafica);
				return null;
			}
		} finally {
			if (legislatureNodes != null) {
				legislatureNodes.close();
			}
		}
	}

	public NodeRef getRelatoreNodeRef(NodeRef item) {
		NodeRef commissioneNodeRef = getCommissione(item);
		if (commissioneNodeRef != null) {
			List<NodeRef> relatori = searchService.selectNodes(commissioneNodeRef, "*[@cm:name='Relatori']", null,
					this.namespaceService, false);
			if (!relatori.isEmpty()) {
				Set<QName> qnames = new HashSet<QName>(1, 1.0f);
				qnames.add(AttoUtil.TYPE_RELATORE_QNAME);
				List<ChildAssociationRef> relatoriList = nodeService.getChildAssocs(relatori.get(0), qnames);
				if (!relatoriList.isEmpty()) {
					return relatoriList.get(0).getChildRef();
				}
			}
		}
		return null;
	}

	public NodeRef getPassaggio(NodeRef item) {
		List<NodeRef> passaggi = searchService.selectNodes(item, "*[@cm:name='Passaggi']", null, this.namespaceService,
				false);
		if (!passaggi.isEmpty()) {
			Set<QName> passaggiFolderQnames = new HashSet<QName>(1, 1.0f);
			passaggiFolderQnames.add(ContentModel.TYPE_FOLDER);
			List<ChildAssociationRef> passaggiList = nodeService.getChildAssocs(passaggi.get(0), passaggiFolderQnames);
			if (!passaggiList.isEmpty()) {
				return passaggiList.get(0).getChildRef();
			}
		}
		return null;
	}

	public NodeRef getCommissione(NodeRef item) {
		NodeRef passaggio = getPassaggio(item);
		if (passaggio != null) {
			List<NodeRef> commissioni = searchService.selectNodes(passaggio, "*[@cm:name='Commissioni']", null,
					this.namespaceService, false);
			if (!commissioni.isEmpty()) {
				Set<QName> commissioniFolderQnames = new HashSet<QName>(1, 1.0f);
				commissioniFolderQnames.add(AttoUtil.COMMISSIONE_TYPE_QNAME);
				List<ChildAssociationRef> commissioniList = nodeService.getChildAssocs(commissioni.get(0),
						commissioniFolderQnames);
				if (!commissioniList.isEmpty()) {
					return commissioniList.get(0).getChildRef();
				}
			}

		}
		return null;
	}

	public String getRelatore(NodeRef item) {
		String relatore = "";
		NodeRef relatoreNodeRef = getRelatoreNodeRef(item);
		if (relatoreNodeRef != null) {

			String relatoreName = (String) nodeService.getProperty(relatoreNodeRef, ContentModel.PROP_NAME);
			try {
				String idAnagrafica = getIdAnagrafica(relatoreName);
				if (idAnagrafica.length() > 0) {
					relatore = "-" + idAnagrafica + "-" + relatoreName;
				}
			} catch (Exception e) {
				logger.error("Impossibile recuperare il relatore", e);
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

	public String getAbbinamenti(NodeRef item) {
		List<NodeRef> attiAbbinati = attoUtil.getAttiAbbinati(item);
		int n = attiAbbinati.size();
		String abbinamenti = "";
		for (int i = 0; i < n; i++) {
			abbinamenti += getIdAtto(attiAbbinati.get(i));
			if (i < n - 1) {
				abbinamenti += listSeparator;
			}
		}
		return abbinamenti;
	}

	public String getEsitoVotazioneCommissione(NodeRef item) {
		NodeRef commissione = getCommissione(item);
		if (commissione != null) {
			return (String) nodeService.getProperty(commissione, AttoUtil.PROP_ESITO_VOTAZIONE_COMMISSIONE_QNAME);
		} else {
			return null;
		}
	}

	public Date getDataVotazioneCommissione(NodeRef item) {
		NodeRef commissione = getCommissione(item);
		if (commissione != null) {
			return (Date) nodeService.getProperty(commissione, AttoUtil.PROP_DATA_VOTAZIONE);
		} else {
			return null;
		}
	}

	public NodeRef getAula(NodeRef item) {
		NodeRef passaggio = getPassaggio(item);
		Set<QName> aulaFolderQnames = new HashSet<QName>(1, 1.0f);
		aulaFolderQnames.add(AttoUtil.TYPE_AULA);
		List<ChildAssociationRef> aulaList = nodeService.getChildAssocs(passaggio, aulaFolderQnames);
		if (!aulaList.isEmpty()) {
			return aulaList.get(0).getChildRef();
		}
		return null;
	}

	public String getEsitoVotazioneAula(NodeRef item) {
		NodeRef aula = getAula(item);
		if (aula != null) {
			return (String) nodeService.getProperty(item, AttoUtil.ESITO_VOTO_PASSAGGIO_AULA_QNAME);
		} else {
			return null;
		}
	}

	public Date getDataVotazioneAula(NodeRef item) {
		NodeRef aula = getAula(item);
		if (aula != null) {
			return (Date) nodeService.getProperty(item, AttoUtil.DATA_SEDUTA_PASSAGGIO_AULA_QNAME);
		} else {
			return null;
		}
	}

	public String getLinkVotoFinaleAula(NodeRef item) {
		NodeRef aula = getAula(item);
		if (aula != null) {
			List<NodeRef> allegati = searchService.selectNodes(aula, "*[@cm:name='Allegati']", null,
					this.namespaceService, false);
			if (!allegati.isEmpty()) {
				Set<QName> allegatoFolderQnames = new HashSet<QName>(1, 1.0f);
				allegatoFolderQnames.add(AttoUtil.TYPE_ALLEGATO_QNAME);
				List<ChildAssociationRef> allegatiList = nodeService.getChildAssocs(allegati.get(0),
						allegatoFolderQnames);
				for (ChildAssociationRef allegato : allegatiList) {
					if (((String) nodeService.getProperty(allegato.getChildRef(), AttoUtil.PROP_TIPOLOGIA_QNAME))
							.equalsIgnoreCase("allegato_aula")) {
						MessageFormat mf = new MessageFormat(this.linkVotoFinale);
						String parameterFormatting[] = new String[] {
								(String) nodeService.getProperty(allegato.getChildRef(), ContentModel.PROP_NODE_UUID) };
						return mf.format(parameterFormatting);
					}

				}
			}

		}
		return null;
	}
}
