package com.sourcesense.crl.util;

import java.text.MessageFormat;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OpenDataCommand {

	private static Log logger = LogFactory.getLog(OpenDataCommand.class);

	private NodeService nodeService;
	private String listSeparator;
	private String linkAtto;

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

}
