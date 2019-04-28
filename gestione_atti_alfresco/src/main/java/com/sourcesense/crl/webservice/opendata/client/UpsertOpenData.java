
package com.sourcesense.crl.webservice.opendata.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.9-b14002 Generated
 * source version: 2.1
 * 
 */
@WebServiceClient(name = "UpsertOpenData", targetNamespace = "https://intranet.consiglio.regione.lombardia.it/OpenData", wsdlLocation = "file:/opt/alfresco/tomcat/webapps/alfresco/WEB-INF/classes/wsdl/UpsertOpenData.wsdl")
public class UpsertOpenData extends Service {

	private final static URL UPSERTOPENDATA_WSDL_LOCATION;
	private final static WebServiceException UPSERTOPENDATA_EXCEPTION;
	private final static QName UPSERTOPENDATA_QNAME = new QName(
			"https://intranet.consiglio.regione.lombardia.it/OpenData", "UpsertOpenData");

	static {
		URL url = null;
		WebServiceException e = null;
		try {
			url = new URL("file:/opt/alfresco/tomcat/webapps/alfresco/WEB-INF/classes/wsdl/UpsertOpenData.wsdl");
		} catch (MalformedURLException ex) {
			e = new WebServiceException(ex);
		}
		UPSERTOPENDATA_WSDL_LOCATION = url;
		UPSERTOPENDATA_EXCEPTION = e;
	}

	public UpsertOpenData() {
		super(__getWsdlLocation(), UPSERTOPENDATA_QNAME);
	}

	public UpsertOpenData(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	/**
	 * 
	 * @return returns UpsertOpenDataSoap
	 */
	@WebEndpoint(name = "UpsertOpenDataSoap")
	public UpsertOpenDataSoap getUpsertOpenDataSoap() {
		return super.getPort(
				new QName("https://intranet.consiglio.regione.lombardia.it/OpenData", "UpsertOpenDataSoap"),
				UpsertOpenDataSoap.class);
	}

	/**
	 * 
	 * @param features
	 *            A list of {@link javax.xml.ws.WebServiceFeature} to configure
	 *            on the proxy. Supported features not in the
	 *            <code>features</code> parameter will have their default
	 *            values.
	 * @return returns UpsertOpenDataSoap
	 */
	@WebEndpoint(name = "UpsertOpenDataSoap")
	public UpsertOpenDataSoap getUpsertOpenDataSoap(WebServiceFeature... features) {
		return super.getPort(
				new QName("https://intranet.consiglio.regione.lombardia.it/OpenData", "UpsertOpenDataSoap"),
				UpsertOpenDataSoap.class, features);
	}

	private static URL __getWsdlLocation() {
		if (UPSERTOPENDATA_EXCEPTION != null) {
			throw UPSERTOPENDATA_EXCEPTION;
		}
		return UPSERTOPENDATA_WSDL_LOCATION;
	}

}
